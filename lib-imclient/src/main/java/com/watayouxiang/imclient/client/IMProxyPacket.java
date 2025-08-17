package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;
import com.watayouxiang.imclient.packet.PacketDecoder;
import com.watayouxiang.imclient.packet.PacketEncoder;
import com.watayouxiang.imclient.packet.PacketReceiver;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc :
 */
interface IMProxyPacket<P extends Packet> {
    /**
     * 获取跳包
     *
     * @return 心跳包
     */
    P getHeartBeatPacket();

    /**
     * 获取消息编码器
     *
     * @return 消息编码器
     */
    PacketEncoder<P> getPacketEncoder();

    /**
     * 获取消息解码器
     *
     * @return 消息解码器
     */
    PacketDecoder<P> getPacketDecoder();

    /**
     * 获取消息接收器
     *
     * @return 消息接收器
     */
    PacketReceiver getPacketReceiver();
}
