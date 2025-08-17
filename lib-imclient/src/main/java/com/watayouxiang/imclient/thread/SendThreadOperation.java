package com.watayouxiang.imclient.thread;

import com.watayouxiang.imclient.packet.Packet;

interface SendThreadOperation<P extends Packet> {
    /**
     * 消息包入队
     *
     * @param packet 消息包
     */
    void putPacket(P packet);
}
