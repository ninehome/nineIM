package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

import java.nio.ByteBuffer;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc :
 */
interface IMProxy<P extends Packet> extends IMProxyPacket<P> {
    /**
     * 获取配置信息
     *
     * @return 配置信息
     */
    IMConfig getConfig();

    /**
     * 连接中（运行在主线程）
     */
    void onConnecting();

    /**
     * 连接成功回调（运行在主线程）
     */
    void onConnected();

    /**
     * 断开连接回调（运行在主线程）
     */
    void onDisconnected();

    /**
     * 连接出错回调（运行在主线程）
     *
     * @param e 错误信息
     */
    void onError(Exception e);

    /**
     * 开始发送消息回调（运行在主线程）
     *
     * @param packet 消息
     */
    void onSendPacketStart(P packet);

    /**
     * 取消发送消息回调（运行在主线程）
     *
     * @param packet 消息
     */
    void onSendPacketCancel(P packet);

    /**
     * 发送消息结束回调（运行在主线程）
     *
     * @param packet 消息
     */
    void onSendPacketEnd(P packet);

    /**
     * 开始接收消息回调（运行在主线程）
     *
     * @param byteBuffer 字节缓冲区
     */
    void onReceivePacketBegin(ByteBuffer byteBuffer);

    /**
     * 取消接收消息回调（运行在主线程）
     */
    void onReceivePacketCancel();

    /**
     * 接收消息结束回调（运行在主线程）
     *
     * @param packet 消息
     */
    void onReceivePacketEnd(P packet);

    /**
     * 资源释放的回调
     */
    void onRelease();
}
