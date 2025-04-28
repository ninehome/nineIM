package com.watayouxiang.imclient.engine;

import org.greenrobot.eventbus.EventBus;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc : 事件总线
 */
public class TioEventEngine implements EventEngine {
    /**
     * 分发事件
     *
     * @param event 事件
     */
    @Override
    public void post(Object event) {
        if (event == null) return;
        EventBus.getDefault().post(event);
    }

    /**
     * 注册订阅
     *
     * @param subscriber 订阅者
     */
    @Override
    public void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 取消订阅
     *
     * @param subscriber 订阅者
     */
    @Override
    public void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
}
