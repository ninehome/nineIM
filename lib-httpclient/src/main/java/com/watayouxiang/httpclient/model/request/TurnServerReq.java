package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.response.TurnServerResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/5/20
 * desc : 获取打洞服务器
 */
public class TurnServerReq extends BaseReq<TurnServerResp> {
    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<TurnServerResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/im/turnserver.tio_x";
    }
}
