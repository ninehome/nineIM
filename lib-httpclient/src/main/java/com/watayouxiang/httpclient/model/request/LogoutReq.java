package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : 登出请求
 *
 * @see Void
 */
public class LogoutReq extends BaseReq<Void> {

    @Override
    public String path() {
        return "/mytio/logout.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
