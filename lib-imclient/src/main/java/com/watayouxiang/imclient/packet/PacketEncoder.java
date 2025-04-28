package com.watayouxiang.imclient.packet;

import com.watayouxiang.imclient.packet.Packet;

import java.nio.ByteBuffer;

public interface PacketEncoder<P extends Packet> {
    /**
     * 消息包编码
     *
     * @param packet 消息包
     * @return 字节缓冲区
     * @throws Exception 编码异常
     */
    ByteBuffer encode(P packet) throws Exception;
}
