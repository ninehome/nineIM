package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.LogoutAccountResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class LogoutAccountReq extends BaseReq<LogoutAccountResp> {

    private long uid;

    public LogoutAccountReq(long uid) {
        this.uid = uid;
    }

    @Override
    public String path() {
        return "/mytio/user/delete.tio_x";
    }


    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("uid", String.valueOf(uid));
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<LogoutAccountResp>>() {
        }.getType();
    }
}
