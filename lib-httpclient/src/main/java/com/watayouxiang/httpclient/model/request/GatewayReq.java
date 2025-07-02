package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.httpclient.model.response.GatewayResp;

import java.lang.reflect.Type;

public class GatewayReq extends BaseReq<GatewayResp> {
    private String userid;


    public GatewayReq(String userid) {
        this.userid = userid;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<GatewayResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "userid="+this.userid;
    }

    public String baseUrl() {
        return "http://121.40.250.68/index/index?";
    }
}
