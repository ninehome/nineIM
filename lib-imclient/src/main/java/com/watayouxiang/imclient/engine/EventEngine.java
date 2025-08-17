package com.watayouxiang.imclient.engine;

/**
 * author : TaoWang
 * date : 2020/4/27
 * desc : 事件总线引擎
 */
public interface EventEngine {
    /**
     * 分发事件
     *
     * @param event 事件
     */
    void post(Object event);

    /**
     * 注册订阅
     *
     * @param subscriber 订阅者
     */
    void register(Object subscriber);

    /**
     * 取消订阅
     *
     * @param subscriber 订阅者
     */
    void unregister(Object subscriber);
}
