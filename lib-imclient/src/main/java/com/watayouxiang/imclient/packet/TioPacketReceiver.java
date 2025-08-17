package com.watayouxiang.imclient.packet;

import com.watayouxiang.imclient.exception.ServerSocketCloseException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TioPacketReceiver implements PacketReceiver {
    @Override
    public ByteBuffer getPacketBuffer(InputStream inputStream) throws Exception {
        if (inputStream == null) return null;

        // 读取消息头字节数组
        byte[] headerBytes = readBytes(inputStream, TioPacket.HEADER_LENGTH);
        if (headerBytes == null) return null;

        // 获取消息体长度
        int bodyLength = getBodyLength(headerBytes);

        // 读取消息体字节数组
        byte[] bodyBytes = readBytes(inputStream, bodyLength);

        // 获取消息包长度
        int packetLength = TioPacket.HEADER_LENGTH;
        if (bodyLength > 0) {
            packetLength += bodyLength;
        }

        // 组装
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);
        buffer.put(headerBytes);
        if (bodyBytes != null) {
            buffer.put(bodyBytes);
        }
        buffer.flip();// 将指针重置到第0位置

        return buffer;
    }

    /**
     * 读取指定长度的字节数组（阻塞式方法）
     *
     * @param inputStream InputStream
     * @param length      byte[]的长度
     * @return byte[]
     * @throws IOException "当Socket断开连接"或者"inputStream.read(...)==-1"
     */
    private byte[] readBytes(InputStream inputStream, int length) throws IOException {
        if (inputStream == null || length <= 0) {
            return null;
        }
        int read;
        byte[] bytes = new byte[length];
        int offset = 0;
        while ((read = inputStream.read(bytes, offset, length)) != -1) {// 阻塞式方法
            // bytes刚好读到length字节数
            if (read - length == 0) {
                break;
            }
            // bytes读到的字节数<length, 继续读
            offset += read;
            length -= read;
        }
        if (read == -1) {
            throw new ServerSocketCloseException("no more data because the end of the stream has been reached");
        }
        return bytes;
    }

    /**
     * 获取消息体长度
     *
     * @param headerBytes 消息头字节数组
     * @return 消息体长度
     */
    private int getBodyLength(byte[] headerBytes) {
        if (headerBytes == null || headerBytes.length != TioPacket.HEADER_LENGTH) {
            return 0;
        }
        ByteBuffer buffer = ByteBuffer.allocate(TioPacket.HEADER_LENGTH);
        buffer.put(headerBytes);
        buffer.flip();// 将指针重置到第0位置
        return buffer.getShort();// 取前两个字节数据，就是消息体的长度
    }
}
