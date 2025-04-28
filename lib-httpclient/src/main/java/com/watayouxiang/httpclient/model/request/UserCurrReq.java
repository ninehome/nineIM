package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-07
 * desc : 获取当前用户信息
 *
 * @see UserCurrResp
 */
public class UserCurrReq extends BaseReq<UserCurrResp> {

    @Override
    public String path() {
        return "/mytio/user/curr.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<UserCurrResp>>() {
        }.getType();
    }
}
