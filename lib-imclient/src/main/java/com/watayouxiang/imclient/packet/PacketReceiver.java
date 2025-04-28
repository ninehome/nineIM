package com.watayouxiang.imclient.packet;

import java.io.InputStream;
import java.nio.ByteBuffer;

public interface PacketReceiver {
    /**
     * 读取一个完整消息包的字节码
     * - 处理半包和粘包
     * - 阻塞式方法
     *
     * @param inputStream 输入流
     * @return 消息包的字节缓冲区
     * @throws Exception 读数据失败
     */
    ByteBuffer getPacketBuffer(InputStream inputStream) throws Exception;
}
