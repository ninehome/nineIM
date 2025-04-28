package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.BuildConfig;
import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.utils.LoggerUtils;
import com.watayouxiang.imclient.utils.StringUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc :
 */
public abstract class TaoIMClient<P extends Packet> implements IMProxy<P>, IMOperation<P> {
    private IMConfig mConfig;
    private Delegate3Wrapper<P> mDelegate;
    private List<IMCallback<P>> mCallbacks;

    public TaoIMClient() {
        mDelegate = new Delegate3Wrapper<>(this);
        mCallbacks = new ArrayList<>();

        TaoIMClient.setDebug(BuildConfig.DEBUG);
    }

    // ====================================================================================
    // operation
    // ====================================================================================

    @Override
    public void setConfig(IMConfig config) {
        mConfig = config;
        LoggerUtils.d(StringUtils.valueOf(config, "IMConfig null"));
    }

    @Override
    public IMConfig getConfig() {
        return mConfig;
    }

    @Override
    public void registerCallback(IMCallback<P> callback) {
        if (callback == null || mCallbacks.contains(callback)) return;
        if (mCallbacks.add(callback)) {
            LoggerUtils.d(String.format(Locale.getDefault(), "IMCallback: (%d) %s", mCallbacks.size(), mCallbacks.toString()));
        }
    }

    @Override
    public void unregisterCallback(IMCallback<P> callback) {
        if (callback == null) return;
        if (mCallbacks.remove(callback)) {
            LoggerUtils.d(String.format(Locale.getDefault(), "IMCallback: (%d) %s", mCallbacks.size(), mCallbacks.toString()));
        }
    }

    @Override
    public IMState getState() {
        return mDelegate.getState();
    }

    @Override
    public Exception getException() {
        return mDelegate.getException();
    }

    @Override
    public boolean isConnected() {
        return getState() == IMState.CONNECTED;
    }

    @Override
    public void connect() throws NullPointerException {
        mDelegate.connect();
    }

    @Override
    public void disconnect() {
        mDelegate.disconnect();
    }

    @Override
    public void release() {
        mDelegate.release();
    }

    @Override
    public boolean sendPacket(P packet) {
        return mDelegate.sendPacket(packet);
    }

    public static void setDebug(boolean debug) {
        LoggerUtils.setDebug(debug);
    }

    private void iteratorCallbacks(IMCallbackIterator<P> iterator) {
        int size = mCallbacks.size();
        for (int i = 0; i < size; i++) {
            iterator.onIterator(mCallbacks.get(i));
        }
    }

    // ====================================================================================
    // notify
    // ====================================================================================

    @Override
    public void onConnecting() {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onConnecting();
            }
        });
    }

    @Override
    public void onConnected() {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onConnected();
            }
        });
    }

    @Override
    public void onDisconnected() {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onDisconnected();
            }
        });
    }

    @Override
    public void onError(final Exception e) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void onSendPacketStart(final P packet) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onSendBegin(packet);
            }
        });
    }

    @Override
    public void onSendPacketCancel(final P packet) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onSendCancel(packet);
            }
        });
    }

    @Override
    public void onSendPacketEnd(final P packet) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onSendEnd(packet);
            }
        });
    }

    @Override
    public void onReceivePacketBegin(final ByteBuffer byteBuffer) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onReceiveBegin(byteBuffer);
            }
        });
    }

    @Override
    public void onReceivePacketCancel() {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onReceiveCancel();
            }
        });
    }

    @Override
    public void onReceivePacketEnd(final P packet) {
        iteratorCallbacks(new IMCallbackIterator<P>() {
            @Override
            public void onIterator(IMCallback<P> callback) {
                callback.onReceiveEnd(packet);
            }
        });
    }

    @Override
    public void onRelease() {
        if (mCallbacks != null) {
            mCallbacks.clear();
            mCallbacks = null;
        }
        mConfig = null;
        mDelegate = null;
    }
}
