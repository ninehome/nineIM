package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/23/20
 *     desc   : 手机注册
 * </pre>
 */
public class CodeRegisterReq extends BaseReq<String> {
    private final String phone;

    private final String code;

    private final String nick;
    /**
     * 是否同意协议
     */
    private final String agreement;

    public CodeRegisterReq(String phone, String code, String nick) {
        this.phone = phone;
        this.code = code;
        this.nick = nick;
        this.agreement = "on";
    }

    @Override
    public String path() {
        return "/mytio/oauth/codeRegister.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("phone", phone)
                .append("code", code)
                .append("nick", nick)
                .append("agreement", agreement);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
