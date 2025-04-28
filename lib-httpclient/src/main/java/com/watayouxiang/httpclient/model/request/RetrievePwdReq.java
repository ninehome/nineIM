package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 找回密码请求
 *
 * @see Void
 */
public class RetrievePwdReq extends BaseReq<Void> {
    private String loginname;

    public RetrievePwdReq(String loginname) {
        this.loginname = loginname;
    }

    @Override
    public String path() {
        return "/mytio/register/retrievePwd.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("loginname", loginname);
    }

    public String getLoginname() {
        return loginname;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
