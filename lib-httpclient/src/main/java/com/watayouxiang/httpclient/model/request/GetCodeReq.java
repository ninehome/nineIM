package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 找回密码请求
 *
 * @see Void
 */
public class GetCodeReq extends BaseReq<String> {
    private String captchaVerification;
    private String sendType;
    private String loginname;

    public GetCodeReq(String captchaVerification,String sendType,String loginname) {
        this.captchaVerification = captchaVerification;
        this.sendType = sendType;
        this.loginname = loginname;
    }

    @Override
    public String path() {
        return "/mytio/sms/resetPwdSend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("captchaVerification", captchaVerification)
                .append("sendType",sendType)
                .append("loginname",loginname);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
