package com.watayouxiang.imclient.app;

import androidx.annotation.NonNull;

import com.watayouxiang.imclient.engine.EventEngine;
import com.watayouxiang.imclient.engine.JsonEngine;
import com.watayouxiang.imclient.model.ImServer;
import com.watayouxiang.imclient.packet.TioHandshake;

interface AppIMClientOperation {
    /**
     * Http请求获取IM服务器地址
     *
     * @return IM服务器地址
     */
    ImServer.Address requestAddress();

    /**
     * 设置握手：
     * 设置握手后，内部会自动进行握手请求
     *
     * @param handshake 握手
     */
    void setHandshake(TioHandshake handshake);

    /**
     * 获取握手
     *
     * @return 握手对象，可能为空
     */
    TioHandshake getHandshake();

    /**
     * 是否握手成功
     *
     * @return 是否握手成功
     */
    boolean isHandshake();

    /**
     * 设置踢出登录监听
     *
     * @param listener 踢出登录监听
     */
    void setKickOutListener(AppIMKickOutListener listener);

    /**
     * 获取事件总线引擎
     *
     * @return 事件总线引擎
     */
    EventEngine getEventEngine();

    /**
     * 设置事件总线引擎
     *
     * @param eventEngine 事件总线引擎
     */
    void setEventEngine(EventEngine eventEngine);

    /**
     * 获取json解析引擎
     *
     * @return json解析引擎
     */
    JsonEngine getJsonEngine();

    /**
     * 设置json解析引擎
     *
     * @param jsonEngine json解析引擎
     */
    void setJsonEngine(JsonEngine jsonEngine);

    /**
     * 更新token
     *
     * @param newToken 新token
     */
    void updateToken(@NonNull String newToken);

    /**
     * 开启/关闭 "App进入后台，自动断开连接"（默认开启）
     *
     * @param onOff true 开启，false 关闭
     */
    void setAutoDisconnectOnAppBackground(boolean onOff);
}
