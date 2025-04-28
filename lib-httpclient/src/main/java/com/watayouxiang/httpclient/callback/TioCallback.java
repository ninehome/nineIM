package com.watayouxiang.httpclient.callback;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

public abstract class TioCallback<Data> extends AbsCallback<BaseResp<Data>> {
    private Type type;

    public TioCallback() {
    }

    /**
     * 避免泛型擦除，使用示例：
     * Type type = new TypeToken<TioResp<ImServerResp>>() {}.getType();
     */
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public BaseResp<Data> convertResponse(okhttp3.Response response) throws Throwable {
        TioConvert<Data> convert = new TioConvert<>(type);
        return convert.convertResponse(response);
    }

    // ====================================================================================
    // 原生
    // ====================================================================================

    @Override
    public void onStart(Request<BaseResp<Data>, ? extends Request> request) {
        super.onStart(request);
    }

    @Override
    public void onCacheSuccess(Response<BaseResp<Data>> response) {
        super.onCacheSuccess(response);
        onResponse(response);
    }

    @Override
    public void onSuccess(Response<BaseResp<Data>> response) {
        onResponse(response);
    }

    @Override
    public void onError(Response<BaseResp<Data>> response) {
        super.onError(response);
        onResponse(response);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    // ====================================================================================
    // t-io
    // ====================================================================================

    private Response<BaseResp<Data>> response;

    private void onResponse(Response<BaseResp<Data>> response) {
        this.response = response;
        if (response.isSuccessful()) {
            // http success
            BaseResp<Data> body = response.body();
            if (body.isOk()) {
                // tio success
                onTioSuccess(body.getData());
            } else {
                // tio error
                onTioError(body.getMsg());
            }
        } else {
            // http error
            onTioError(ExceptionHandler.handleException(response));
        }
    }

    public boolean isFromCache() {
        if (response != null) {
            return response.isFromCache();
        }
        return false;
    }

    public String getTioMsg() {
        if (response != null && response.isSuccessful() && response.body() != null) {
            return response.body().getMsg();
        }
        return null;
    }

    public boolean isTioError() {
        if (response != null && response.isSuccessful() && response.body() != null) {
            return !response.body().isOk();
        }
        return false;
    }

    public abstract void onTioSuccess(Data data);

    public abstract void onTioError(String msg);
}
