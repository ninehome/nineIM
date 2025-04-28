package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

/**
 * author : TaoWang
 * date : 2020/3/24
 * desc :
 */
interface DelegateOperation<P extends Packet> {
    /**
     * 连接（异步）
     *
     * @throws NullPointerException 连接参数缺失
     */
    void connect() throws NullPointerException;

    /**
     * 断开连接（异步）
     */
    void disconnect();

    /**
     * 获取连接状态
     *
     * @return 连接状态
     */
    IMState getState();

    /**
     * 获取错误原因
     */
    Exception getException();

    /**
     * 发送消息（异步）
     *
     * @param packet 消息
     * @return 是否开始发送消息
     */
    boolean sendPacket(P packet);

    /**
     * 释放资源
     */
    void release();
}
