package com.watayouxiang.httpclient.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc :
 */
public abstract class BaseReq<Data> {

    // ====================================================================================
    // req
    // ====================================================================================

    public void get(TioCallback<Data> callback) {
        TioHttpClient.get(this, callback);
    }

    public Response<BaseResp<Data>> get() {
        return TioHttpClient.get(this);
    }

    public void post(TioCallback<Data> callback) {
        TioHttpClient.post(this, callback);
    }

    public Response<BaseResp<Data>> post() {
        return TioHttpClient.post(this);
    }

    public Response<BaseResp<Data>> upload() {
        return TioHttpClient.upload(this);
    }

    public void upload(TioCallback<Data> callback) {
        TioHttpClient.upload(this, callback);
    }

    public void cancel() {
        TioHttpClient.cancel(getCancelTag());
    }

    // ====================================================================================
    // body type
    // ====================================================================================

    /**
     * 避免泛型擦除
     *
     * @return new TypeToken<TioResp<ConfigResp>>() {
     * }.getType();
     */
    public abstract Type bodyType();

    // ====================================================================================
    // url
    // ====================================================================================

    /**
     * @return 基本 url
     */
    public String baseUrl() {
        return TioHttpClient.getBaseUrl();
    }

    /**
     * @return 请求路径
     */
    public abstract String path();

    /**
     * @return 完整 url
     */
    public String url() {
        return baseUrl() + path();
    }

    // ====================================================================================
    // param
    // ====================================================================================

    /**
     * @return 参数 map
     */
    public TioMap<String, String> params() {
        return TioMap.getParamMap();
    }

    /**
     * @return 文件 map
     */
    public TioMap<String, File> files() {
        return TioMap.getFileMap();
    }

    // ====================================================================================
    // cache
    // ====================================================================================

    private CacheMode mCacheMode = OkGo.getInstance().getCacheMode();

    public CacheMode getCacheMode() {
        return mCacheMode;
    }

    /**
     * @param cacheMode 缓存模式
     */
    public BaseReq<Data> setCacheMode(CacheMode cacheMode) {
        mCacheMode = cacheMode;
        return this;
    }

    // ====================================================================================
    // cancel tag
    // ====================================================================================

    private Object mCancelTag;

    public Object getCancelTag() {
        return mCancelTag;
    }

    /**
     * @param tag 取消标记
     */
    public BaseReq<Data> setCancelTag(Object tag) {
        mCancelTag = tag;
        return this;
    }

}
