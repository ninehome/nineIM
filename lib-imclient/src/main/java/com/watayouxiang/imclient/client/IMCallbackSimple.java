package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

import java.nio.ByteBuffer;

public class IMCallbackSimple<P extends Packet> implements IMCallback<P> {
    @Override
    public void onConnecting() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onSendBegin(P packet) {

    }

    @Override
    public void onSendCancel(P packet) {

    }

    @Override
    public void onSendEnd(P packet) {

    }

    @Override
    public void onReceiveBegin(ByteBuffer buffer) {

    }

    @Override
    public void onReceiveCancel() {

    }

    @Override
    public void onReceiveEnd(P packet) {

    }
}
