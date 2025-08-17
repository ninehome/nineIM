package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketDecoder;
import com.watayouxiang.imclient.packet.PacketEncoder;
import com.watayouxiang.imclient.packet.PacketReceiver;
import com.watayouxiang.imclient.thread.ConnectThread;
import com.watayouxiang.imclient.thread.ConnectThreadStop;
import com.watayouxiang.imclient.thread.ReceiveThread;
import com.watayouxiang.imclient.thread.ReceiveThreadStop;
import com.watayouxiang.imclient.thread.SendThread;
import com.watayouxiang.imclient.thread.SendThreadStop;
import com.watayouxiang.imclient.tool.UIHandler;
import com.watayouxiang.imclient.utils.ExceptionUtils;
import com.watayouxiang.imclient.utils.LoggerUtils;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc :
 */
class Delegate<P extends Packet> implements DelegateOperation<P> {

    private Runnable mHeartBeatRunnable;
    private Runnable mReconnectRunnable;

    private ConnectThread mConnectThread;
    private SendThread<P> mSendThread;
    private ReceiveThread<P> mReceiveThread;
    private IMState mState;
    private boolean mSendThreadStart;
    private boolean mReceiveThreadStart;

    private IMProxy<P> mProxy;
    private UIHandler mUIHandler;
    private Exception mException;
    private AllThreadStopListener mAllThreadStopListener;

    Delegate(IMProxy<P> proxy) {
        mProxy = proxy;
        mState = IMState.IDLE;
        mUIHandler = new UIHandler();
    }

    // ====================================================================================
    // DelegateOperation
    // ====================================================================================

    private void setState(IMState state) {
        mState = state;
        LoggerUtils.d(String.format(Locale.getDefault(), ">>> %s", state));
    }

    @Override
    public void connect() throws NullPointerException {
        if (mState == IMState.CONNECTED) {
            return;
        }
        startConnectThread();
    }

    @Override
    public void disconnect() {
        if (mState == IMState.IDLE
                || mState == IMState.DISCONNECTED) {
            return;
        }
        stopHeartBeat();
        stopReconnect();
        if (isAllThreadNull()) {
            notifyIMDisconnected();
        } else {
            stopAllThread(true);
        }
    }

    @Override
    public IMState getState() {
        return mState;
    }

    @Override
    public Exception getException() {
        // TODO: 2020/9/11
        return null;
    }

    @Override
    public boolean sendPacket(P packet) {
        if (packet == null || mSendThread == null) {
            return false;
        }
        mSendThread.putPacket(packet);
        return true;
    }

    @Override
    public void release() {
        if (!isAllThreadNull()) {
            mAllThreadStopListener = new AllThreadStopListener() {
                @Override
                public void onAllThreadStop() {
                    startRelease();
                }
            };
            disconnect();
        } else {
            startRelease();
        }
    }

    private void startRelease() {
        mReconnectRunnable = null;
        mHeartBeatRunnable = null;

        mConnectThread = null;
        mReceiveThread = null;
        mSendThread = null;
        mState = null;

        if (mUIHandler != null) {
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
        }

        mException = null;
        mAllThreadStopListener = null;
        mProxy.onRelease();
        mProxy = null;

        LoggerUtils.d(">>> release");
    }

    // ====================================================================================
    // ThreadConnectCallback
    // ====================================================================================

    private void startConnectThread() throws NullPointerException {
        if (mConnectThread != null
                || mSendThread != null
                || mReceiveThread != null) {
            return;
        }

        IMConfig config = mProxy.getConfig();
        if (config == null) throw new NullPointerException("config null");

        mConnectThread = new ConnectThread(config) {
            @Override
            public void onConnectThreadStart() {
                LoggerUtils.d("onConnectThreadStart");
            }

            @Override
            public void onConnectThreadStop(final ConnectThreadStop state, final Socket socket, final Exception e) {
                LoggerUtils.d("onConnectThreadStop");
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnectThread = null;
                        if (state == ConnectThreadStop.SUCCESS) {
                            mSendThreadStart = false;
                            mReceiveThreadStart = false;
                            startSendThread(socket);
                            startReceiveThread(socket);
                        } else if (state == ConnectThreadStop.CLOSE) {
                            notifyIMDisconnected();
                        } else if (state == ConnectThreadStop.ERROR) {
                            notifyIMError(e);
                        }
                        notifyAllThreadStop();
                    }
                });
            }
        };
        mConnectThread.start();
    }

    // ====================================================================================
    // ThreadSendCallback
    // ====================================================================================

    private void startSendThread(Socket sslSocket) {
        if (mSendThread != null) return;

        PacketEncoder<P> packetEncoder = mProxy.getPacketEncoder();
        if (packetEncoder == null) throw new NullPointerException("packetEncoder null");

        mSendThread = new SendThread<P>(sslSocket, mProxy.getPacketEncoder()) {
            @Override
            public void onSendThreadStart() {
                LoggerUtils.d("onSendThreadStart");
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSendThreadStart = true;
                        notifyIMConnected();
                    }
                });
            }

            @Override
            public void onSendPacketStart(final P packet) {
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onSendPacketStart(packet);
                        startHeartBeat();
                    }
                });
            }

            @Override
            public void onSendPacketCancel(final P packet) {
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onSendPacketCancel(packet);
                    }
                });
            }

            @Override
            public void onSendPacketEnd(final P packet) {
                LoggerUtils.d("--> " + packet.getJson());
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onSendPacketEnd(packet);
                    }
                });
            }

            @Override
            public void onSendThreadStop(final SendThreadStop state, final Exception e) {
                LoggerUtils.d("onSendThreadStop");
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSendThread = null;
                        stopHeartBeat();
                        if (state == SendThreadStop.CLOSE) {
                            notifyIMDisconnected();
                        } else if (state == SendThreadStop.ERROR) {
                            notifyIMError(e);
                        }
                        notifyAllThreadStop();
                    }
                });
            }
        };
        mSendThread.start();
    }

    // ====================================================================================
    // ThreadReceiveCallback
    // ====================================================================================

    private void startReceiveThread(Socket sslSocket) {
        if (mReceiveThread != null) return;

        PacketDecoder<P> packetDecoder = mProxy.getPacketDecoder();
        if (packetDecoder == null) throw new NullPointerException("packetDecoder null");
        PacketReceiver packetReceiver = mProxy.getPacketReceiver();
        if (packetReceiver == null) throw new NullPointerException("packetReceiver null");

        mReceiveThread = new ReceiveThread<P>(sslSocket, packetDecoder, packetReceiver) {
            @Override
            public void onReceiveThreadStart() {
                LoggerUtils.d("onReceiveThreadStart");
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mReceiveThreadStart = true;
                        notifyIMConnected();
                    }
                });
            }

            @Override
            public void onReceivePacketBegin(final ByteBuffer byteBuffer) {
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onReceivePacketBegin(byteBuffer);
                    }
                });
            }

            @Override
            public void onReceivePacketCancel() {
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onReceivePacketCancel();
                    }
                });
            }

            @Override
            public void onReceivePacketEnd(final P packet) {
                LoggerUtils.d("<-- " + packet.getJson());
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxy.onReceivePacketEnd(packet);
                    }
                });
            }

            @Override
            public void onReceiveThreadStop(final ReceiveThreadStop state, final Exception e) {
                LoggerUtils.d("onReceiveThreadStop");
                mUIHandler.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mReceiveThread = null;
                        if (state == ReceiveThreadStop.CLOSE) {
                            notifyIMDisconnected();
                        } else if (state == ReceiveThreadStop.ERROR) {
                            notifyIMError(e);
                        }
                        notifyAllThreadStop();
                    }
                });
            }
        };
        mReceiveThread.start();
    }

    // ====================================================================================
    // notify
    // ====================================================================================

    private void notifyIMConnected() {
        if (!mSendThreadStart || !mReceiveThreadStart) return;
        setState(IMState.CONNECTED);
        mProxy.onConnected();
    }

    private void notifyIMDisconnected() {
        if (!isAllThreadNull()) return;
        setState(IMState.DISCONNECTED);
        mProxy.onDisconnected();
    }

    private void notifyIMError(Exception e) {
        if (mException == null) {
            mException = e;
        }
        if (!isAllThreadNull()) {
            stopAllThread(false);
            return;
        }
        LoggerUtils.d("Exception = " + ExceptionUtils.getStackTrace(mException));
        setState(IMState.ERROR);
        mProxy.onError(mException);
        startReconnect(mException);
        mException = null;
    }

    // ====================================================================================
    // stop thread
    // ====================================================================================

    private void notifyAllThreadStop() {
        if (!isAllThreadNull()) return;
        if (mAllThreadStopListener != null) {
            mAllThreadStopListener.onAllThreadStop();
        }
    }

    private boolean isAllThreadNull() {
        return mConnectThread == null
                && mSendThread == null
                && mReceiveThread == null;
    }

    private void stopAllThread(boolean isClose) {
        if (mConnectThread != null) {
            mConnectThread.setCloseFlag(isClose);
        }
        if (mSendThread != null) {
            mSendThread.setCloseFlag(isClose);
        }
        if (mReceiveThread != null) {
            mReceiveThread.setCloseFlag(isClose);
        }

        if (mConnectThread != null) {
            mConnectThread.stopThread();
        }
        if (mSendThread != null) {
            mSendThread.stopThread();
        }
        if (mReceiveThread != null) {
            mReceiveThread.stopThread();
        }
    }

    // ====================================================================================
    // HeartBeat
    // ====================================================================================

    private void startHeartBeat() {
        long heartBeatInterval = mProxy.getConfig().getHeartBeatInterval();
        if (heartBeatInterval < IMConfig.HEARTBEAT_INTERVAL_MIN) return;

        stopHeartBeat();
        mUIHandler.postDelayed(mHeartBeatRunnable = new Runnable() {
            @Override
            public void run() {
                sendPacket(mProxy.getHeartBeatPacket());
            }
        }, heartBeatInterval);
    }

    private void stopHeartBeat() {
        if (mHeartBeatRunnable != null) {
            mUIHandler.removeCallbacks(mHeartBeatRunnable);
            mHeartBeatRunnable = null;
        }
    }

    // ====================================================================================
    // Reconnect
    // ====================================================================================

    private void startReconnect(Exception e) {
        if (e instanceof RuntimeException) return;
        long reconnectInterval = mProxy.getConfig().getReconnectInterval();
        if (reconnectInterval < IMConfig.RECONNECT_INTERVAL_MIN) return;

        stopReconnect();
        mUIHandler.postDelayed(mReconnectRunnable = new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, reconnectInterval);
    }

    private void stopReconnect() {
        if (mReconnectRunnable != null) {
            mUIHandler.removeCallbacks(mReconnectRunnable);
            mReconnectRunnable = null;
        }
    }
}
