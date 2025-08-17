package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.OnkeyLoginResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 * <p>
 * "data" : "操作成功"
 *
 * @see String
 */
public class OnkeyLoginReq extends BaseReq<OnkeyLoginResp> {
    private String token;
    private String appKey;

    public OnkeyLoginReq(String token, String appKey) {
        this.token = token;
        this.appKey = appKey;
    }

    @Override
    public String path() {
        return "/mytio/oauth/login.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("token", token)
                .append("appKey", appKey)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<OnkeyLoginResp>>() {
        }.getType();
    }
}
