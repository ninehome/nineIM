package com.watayouxiang.androidutils.mvp;

import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.imclient.TioIMClient;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public abstract class BaseModel {
    private final boolean registerEvent;

    public BaseModel(boolean registerEvent) {
        this.registerEvent = registerEvent;
        if (registerEvent) {
            TioIMClient.getInstance().getEventEngine().register(this);
        }
    }

    public BaseModel() {
        this(false);
    }

    public void detachModel() {
        TioHttpClient.cancel(this);
        if (registerEvent) {
            TioIMClient.getInstance().getEventEngine().unregister(this);
        }
    }

    // ====================================================================================
    // 数据回调
    // ====================================================================================

    public abstract static class DataProxy<Data> {
        public void onSuccess(Data data) {

        }

        public void onFailure(String msg) {

        }

        public void onFinish() {

        }
    }
}