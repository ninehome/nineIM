package com.watayouxiang.imclient.client;

import com.watayouxiang.imclient.packet.Packet;

interface IMOperation<P extends Packet> {
    /**
     * 初始化配置
     *
     * @param config 配置
     */
    void setConfig(IMConfig config);

    /**
     * 注册回调
     *
     * @param callback 回调
     */
    void registerCallback(IMCallback<P> callback);

    /**
     * 注销回调
     *
     * @param callback 回调
     */
    void unregisterCallback(IMCallback<P> callback);

    /**
     * 是否成功连接
     *
     * @return 成功连接
     */
    boolean isConnected();

    /**
     * 获取状态
     *
     * @return 状态
     */
    IMState getState();

    /**
     * 获取错误原因
     */
    Exception getException();

    /**
     * 连接
     */
    void connect();

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 释放资源
     */
    void release();

    /**
     * 发送消息包
     *
     * @param packet 消息包
     * @return 是否成功调用
     */
    boolean sendPacket(P packet);
}
