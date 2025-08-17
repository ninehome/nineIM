package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

import java.nio.ByteBuffer;

/**
 * 注意：回调匀运行在主线程
 *
 * @param <P> 消息类型
 */
public interface IMCallback<P extends Packet> {
    /**
     * 连接中
     */
    void onConnecting();

    /**
     * 连接成功
     */
    void onConnected();

    /**
     * 断连成功
     */
    void onDisconnected();

    /**
     * 连接出错
     *
     * @param e 异常
     */
    void onError(Exception e);

    /**
     * 发送一个消息包开始
     *
     * @param packet 消息包
     */
    void onSendBegin(P packet);

    /**
     * 发送一个消息包取消
     *
     * @param packet 消息包
     */
    void onSendCancel(P packet);

    /**
     * 发送一个消息包完成
     *
     * @param packet 消息包
     */
    void onSendEnd(P packet);

    /**
     * 接收一个消息包开始
     *
     * @param buffer 字节数据
     */
    void onReceiveBegin(ByteBuffer buffer);

    /**
     * 接收一个消息包取消
     */
    void onReceiveCancel();

    /**
     * 接收一个消息包完成
     *
     * @param packet 消息包
     */
    void onReceiveEnd(P packet);
}
