package com.watayouxiang.imclient.packet;

import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.utils.ByteUtils;
import com.watayouxiang.imclient.utils.GzipUtils;

import java.nio.ByteBuffer;

/**
 * author : TaoWang
 * date : 2020/3/25
 * desc :
 */
public class TioPacketEncoder implements PacketEncoder<TioPacket> {
    @Override
    public ByteBuffer encode(TioPacket packet) throws Exception {
        if (packet == null) return null;

        short command = packet.getCommand();
        Object body = packet.getBody();

        String bodyString = TioIMClient.getInstance().getJsonEngine().object2String(body);
        byte[] bodyBytes = ByteUtils.string2Bytes(bodyString, TioPacket.CHARSET);

        short bodyLen = 0;
        if (bodyBytes != null) {
            bodyLen = (short) bodyBytes.length;
        }

        byte gzip = 0;
        if (bodyLen >= TioPacket.GZIP_LENGTH) {
            gzip = 1;
        }

        if (gzip == 1) {
            bodyBytes = GzipUtils.compress(bodyBytes);
        }

        if (bodyBytes != null) {
            bodyLen = (short) bodyBytes.length;
        }

        packet.setBodyJson(bodyString);
        packet.setBodyLength(bodyLen);
        packet.setGzip(gzip);

        int packetLen = TioPacket.HEADER_LENGTH + bodyLen;
        ByteBuffer buffer = ByteBuffer.allocate(packetLen);

        // 消息体长度
        buffer.putShort(bodyLen);
        // 命令码
        buffer.putShort(command);
        // 消息体是否压缩
        buffer.put(gzip);
        // 消息体
        if (bodyBytes != null) {
            buffer.put(bodyBytes);
        }

        return buffer;
    }
}
