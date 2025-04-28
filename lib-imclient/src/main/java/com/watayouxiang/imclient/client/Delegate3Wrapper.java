package com.watayouxiang.imclient.client;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.tool.UIHandler;

import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/10
 *     desc   :
 * </pre>
 */
class Delegate3Wrapper<P extends Packet> extends Delegate3<P> {
    private final IMProxy<P> mProxy;
    private final UIHandler mUIHandler;

    Delegate3Wrapper(IMProxy<P> proxy) {
        super(proxy);
        mProxy = proxy;
        mUIHandler = new UIHandler();
    }

    @Override
    protected void onStateChanged(@NonNull final IMState state) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case CONNECTING:
                        mProxy.onConnecting();
                        break;
                    case CONNECTED:
                        mProxy.onConnected();
                        break;
                    case DISCONNECTED:
                        mProxy.onDisconnected();
                        break;
                    case ERROR:
                        mProxy.onError(getException());
                        break;
                }
            }
        });
    }

    @Override
    protected void onSendPacketStart(final P packet) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onSendPacketStart(packet);
            }
        });
    }

    @Override
    protected void onSendPacketCancel(final P packet) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onSendPacketCancel(packet);
            }
        });
    }

    @Override
    protected void onSendPacketEnd(final P packet) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onSendPacketEnd(packet);
            }
        });
    }

    @Override
    protected void onReceivePacketBegin(final ByteBuffer byteBuffer) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onReceivePacketBegin(byteBuffer);
            }
        });
    }

    @Override
    protected void onReceivePacketCancel() {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onReceivePacketCancel();
            }
        });
    }

    @Override
    protected void onReceivePacketEnd(final P packet) {
        mUIHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProxy.onReceivePacketEnd(packet);
            }
        });
    }
}
