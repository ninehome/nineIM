package com.watayouxiang.imclient.client;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.thread.ReceiveRunnable;
import com.watayouxiang.imclient.thread.ReceiveRunnableListener;
import com.watayouxiang.imclient.thread.SendRunnable;
import com.watayouxiang.imclient.thread.SendRunnableListener;
import com.watayouxiang.imclient.thread.SocketRunnable;
import com.watayouxiang.imclient.tool.ThreadHandler;
import com.watayouxiang.imclient.utils.ExceptionUtils;
import com.watayouxiang.imclient.utils.LoggerUtils;
import com.watayouxiang.imclient.utils.StringUtils;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/9/9
 *     desc   :
 * </pre>
 */
abstract class Delegate3<P extends Packet> implements DelegateOperation<P> {
    private IMProxy<P> mProxy = null;
    private IMState mImState = null;
    private ThreadHandler mWtHandler = null;

    private ExecutorService mFixedThreadPool = null;
    private SendRunnable<P> mSendRunnable = null;
    private ReceiveRunnable<P> mReceiveRunnable = null;
    private Runnable mHeartBeatRunnable = null;
    private Runnable mReconnectRunnable = null;
    private Exception mException = null;
    private boolean mSendThreadStart = false;
    private boolean mReceiveThreadStart = false;

    Delegate3(IMProxy<P> proxy) {
        mProxy = proxy;
        mImState = IMState.IDLE;
        mWtHandler = new ThreadHandler();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    @Override
    public void connect() throws NullPointerException {
        mWtHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mImState == IMState.CONNECTING) return;
                if (mImState == IMState.CONNECTED) return;

                notifyStateChanged(IMState.CONNECTING);
                new SocketRunnable(mProxy.getConfig()) {
                    @Override
                    public void onConnectSocketStart() {

                    }

                    @Override
                    public void onConnectSocketError(@NonNull Exception e) {
                        notifyError(e);
                    }

                    @Override
                    public void onConnectSocketSuccess(@NonNull Socket socket) {
                        mSendThreadStart = false;
                        mReceiveThreadStart = false;
                        mFixedThreadPool = Executors.newFixedThreadPool(2);
                        startSendThread(mFixedThreadPool, socket);
                        startReceiveThread(mFixedThreadPool, socket);
                    }
                }.run();
            }
        });
    }

    @Override
    public void disconnect() {
        mWtHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mImState == IMState.IDLE) return;
                if (mImState == IMState.DISCONNECTED) return;

                stopHeartBeatInternal();
                stopReconnectInternal();
                stopSocketInternal();
                notifyStateChanged(IMState.DISCONNECTED);
            }
        });
    }

    @Override
    public synchronized boolean sendPacket(final P packet) {
        if (getState() != IMState.CONNECTED) return false;
        if (packet == null) return false;
        if (mSendRunnable == null) return false;
        mSendRunnable.putPacket(packet);
        return true;
    }

    @Override
    public synchronized IMState getState() {
        return mImState;
    }

    @Override
    public synchronized Exception getException() {
        return mException;
    }

    @Override
    public synchronized void release() {
        stopHeartBeatInternal();
        stopReconnectInternal();
        stopSocketInternal();
        mImState = null;
        mException = null;
        if (mProxy != null) {
            mProxy.onRelease();
            mProxy = null;
        }
        if (mWtHandler != null) {
            mWtHandler.release();
            mWtHandler = null;
        }
        LoggerUtils.d(">>> release");
    }

    // ====================================================================================
    // socket
    // ====================================================================================

    private void startSendThread(@NonNull ExecutorService executorService, @NonNull Socket socket) {
        mSendRunnable = new SendRunnable<P>(socket, mProxy.getPacketEncoder());
        mSendRunnable.setListener(new SendRunnableListener<P>() {
            @Override
            public void onSendThreadStart() {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSendThreadStart = true;
                        notifyConnected();
                    }
                });
            }

            @Override
            public void onSendThreadStop() {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopHeartBeatInternal();
                    }
                });
            }

            @Override
            public void onSendThreadError(@NonNull final Exception e) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopHeartBeatInternal();
                        notifyError(e);
                    }
                });
            }

            @Override
            public void onSendPacketStart(final P packet) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Delegate3.this.onSendPacketStart(packet);
                    }
                });
            }

            @Override
            public void onSendPacketCancel(final P packet) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Delegate3.this.onSendPacketCancel(packet);
                    }
                });
            }

            @Override
            public void onSendPacketEnd(final P packet) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoggerUtils.d("--> " + packet.getJson());
                        startHeartBeatInternal();
                        Delegate3.this.onSendPacketEnd(packet);
                    }
                });
            }
        });
        executorService.execute(mSendRunnable);
    }

    private void startReceiveThread(@NonNull ExecutorService executorService, @NonNull Socket socket) {
        mReceiveRunnable = new ReceiveRunnable<P>(socket, mProxy.getPacketDecoder(), mProxy.getPacketReceiver());
        mReceiveRunnable.setListener(new ReceiveRunnableListener<P>() {
            @Override
            public void onReceiveThreadStart() {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mReceiveThreadStart = true;
                        notifyConnected();
                    }
                });
            }

            @Override
            public void onReceiveThreadStop() {

            }

            @Override
            public void onReceiveThreadError(@NonNull final Exception e) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyError(e);
                    }
                });
            }

            @Override
            public void onReceivePacketBegin(final ByteBuffer byteBuffer) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Delegate3.this.onReceivePacketBegin(byteBuffer);
                    }
                });
            }

            @Override
            public void onReceivePacketCancel() {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Delegate3.this.onReceivePacketCancel();
                    }
                });
            }

            @Override
            public void onReceivePacketEnd(@NonNull final P packet) {
                mWtHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoggerUtils.d("<-- " + packet.getJson());
                        Delegate3.this.onReceivePacketEnd(packet);
                    }
                });
            }
        });
        executorService.execute(mReceiveRunnable);
    }

    private void stopSocketInternal() {
        if (mSendRunnable != null) {
            mSendRunnable.setCloseFlag(true);
            mSendRunnable.setListener(null);
            mSendRunnable = null;
        }
        if (mReceiveRunnable != null) {
            mReceiveRunnable.setCloseFlag(true);
            mReceiveRunnable.setListener(null);
            mReceiveRunnable = null;
        }
        if (mFixedThreadPool != null) {
            mFixedThreadPool.shutdownNow();
            mFixedThreadPool = null;
        }
    }

    // ====================================================================================
    // heartBeat
    // ====================================================================================

    private void startHeartBeatInternal() {
        IMConfig config = mProxy.getConfig();
        if (config == null) {
            notifyError(new NullPointerException("config null"));
            return;
        }
        long heartBeatInterval = config.getHeartBeatInterval();
        if (heartBeatInterval < IMConfig.HEARTBEAT_INTERVAL_MIN) {
            notifyError(new IllegalArgumentException("heartBeatInterval too small"));
            return;
        }
        final P heartBeatPacket = mProxy.getHeartBeatPacket();
        if (heartBeatPacket == null) {
            notifyError(new NullPointerException("heartBeatPacket null"));
            return;
        }

        stopHeartBeatInternal();
        mWtHandler.postDelayed(mHeartBeatRunnable = new Runnable() {
            @Override
            public void run() {
                sendPacket(heartBeatPacket);
            }
        }, heartBeatInterval);
    }

    private void stopHeartBeatInternal() {
        if (mHeartBeatRunnable != null) {
            mWtHandler.removeCallbacks(mHeartBeatRunnable);
            mHeartBeatRunnable = null;
        }
    }

    // ====================================================================================
    // reconnect
    // ====================================================================================

    private void startReconnectInternal() {
        IMConfig config = mProxy.getConfig();
        if (config == null) {
            notifyError(new NullPointerException("config null"));
            return;
        }
        long reconnectInterval = config.getReconnectInterval();
        if (reconnectInterval < IMConfig.RECONNECT_INTERVAL_MIN) {
            notifyError(new IllegalArgumentException("reconnectInterval too small"));
            return;
        }

        stopReconnectInternal();
        mWtHandler.postDelayed(mReconnectRunnable = new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, reconnectInterval);

        LoggerUtils.d(String.format(Locale.getDefault(), "reconnect after %d millisecond", reconnectInterval));
    }

    private void stopReconnectInternal() {
        if (mReconnectRunnable != null) {
            mWtHandler.removeCallbacks(mReconnectRunnable);
            mReconnectRunnable = null;
        }
    }

    // ====================================================================================
    // notify
    // ====================================================================================

    private void notifyConnected() {
        if (!mSendThreadStart || !mReceiveThreadStart) return;
        if (mImState == IMState.CONNECTED) return;
        notifyStateChanged(IMState.CONNECTED);
    }

    private void notifyError(@NonNull final Exception e) {
        if (mImState == IMState.ERROR) return;
        LoggerUtils.e(StringUtils.valueOf(ExceptionUtils.getStackTrace(e), "Exception null"));
        mException = e;
        notifyStateChanged(IMState.ERROR);

        // 非运行时异常，需要重连
        if (!(e instanceof RuntimeException)) {
            mWtHandler.post(new Runnable() {
                @Override
                public void run() {
                    startReconnectInternal();
                }
            });
        }
    }

    private void notifyStateChanged(@NonNull IMState imState) {
        LoggerUtils.d(String.format(Locale.getDefault(), ">>> %s", imState));
        mImState = imState;
        onStateChanged(imState);
    }

    // ====================================================================================
    // abstract
    // ====================================================================================

    protected abstract void onStateChanged(@NonNull IMState state);

    protected abstract void onSendPacketStart(P packet);

    protected abstract void onSendPacketCancel(P packet);

    protected abstract void onSendPacketEnd(P packet);

    protected abstract void onReceivePacketBegin(ByteBuffer byteBuffer);

    protected abstract void onReceivePacketCancel();

    protected abstract void onReceivePacketEnd(P packet);
}
