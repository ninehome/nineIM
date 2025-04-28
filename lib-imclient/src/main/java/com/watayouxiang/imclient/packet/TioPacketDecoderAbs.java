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
abstract class TioPacketDecoderAbs implements PacketDecoder<TioPacket> {
    @Override
    public TioPacket decode(ByteBuffer buffer) throws Exception {
        if (buffer == null) return null;

        int readableLen = buffer.limit() - buffer.position();
        if (readableLen < TioPacket.HEADER_LENGTH) {
            return null;
        }

        // 消息体长度
        short bodyLen = buffer.getShort();

        int packetLen = TioPacket.HEADER_LENGTH;
        if (bodyLen > 0) {
            packetLen += bodyLen;
        }
        if (readableLen < packetLen) {
            return null;
        }

        // 命令码
        short command = buffer.getShort();
        // 消息体是否压缩
        byte gzip = buffer.get();
        // 消息体
        byte[] body = null;
        if (bodyLen > 0) {
            body = new byte[bodyLen];
            buffer.get(body);
        }

        if (gzip == 1) {
            body = GzipUtils.unCompress(body);
        }

        String bodyString = ByteUtils.bytes2String(body, TioPacket.CHARSET);
        Object bodyObj = TioIMClient.getInstance().getJsonEngine().string2Object(bodyString, getClazz(command));

        TioPacket packet = new TioPacket();
        packet.setCommand(command);
        packet.setGzip(gzip);
        if (bodyObj != null) {
            packet.setBody(bodyObj);
        }

        packet.setBodyJson(bodyString);
        packet.setBodyLength(bodyLen);

        return packet;
    }

    public abstract Class getClazz(short command);
}
