package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc : 用户信息请求
 *
 * @see UserInfoResp
 */
public class UserInfoReq extends BaseReq<UserInfoResp> {

    private String uid;

    public UserInfoReq(String uid) {
        this.uid = uid;
    }

    @Override
    public String path() {
        return "/mytio/user/info.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("uid", uid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<UserInfoResp>>() {
        }.getType();
    }
}
