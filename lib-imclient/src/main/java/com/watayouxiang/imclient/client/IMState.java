package com.watayouxiang.imclient.client;

public enum IMState {
    /**
     * 闲置
     */
    IDLE,
    /**
     * 正在连接
     */
    CONNECTING,
    /**
     * 已经连接
     */
    CONNECTED,
    /**
     * 断开连接
     */
    DISCONNECTED,
    /**
     * 异常
     */
    ERROR
}
