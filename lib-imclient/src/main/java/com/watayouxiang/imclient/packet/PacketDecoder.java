package com.watayouxiang.imclient.packet;

import java.nio.ByteBuffer;

public interface PacketDecoder<P extends Packet> {
    /**
     * 消息包解码
     *
     * @param buffer 字节缓冲区
     * @return 消息包
     * @throws Exception 解码异常
     */
    P decode(ByteBuffer buffer) throws Exception;
}
