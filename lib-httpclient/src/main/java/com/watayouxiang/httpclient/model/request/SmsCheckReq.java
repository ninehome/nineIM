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
 *     time   : 12/24/20
 *     desc   : 判断验证码是否正确
 * </pre>
 */
public class SmsCheckReq extends BaseReq<String> {
    /**
     * 1：绑定手机号；2：注册；3：登录；4:修改密码;5:修改手机-老手机号验证;6:找回密码；7：绑定新手机;8:三方绑定手机
     */
    private final String biztype;
    /**
     * 手机号
     */
    private final String mobile;
    /**
     * 验证码
     */
    private final String code;

    public SmsCheckReq(String biztype, String mobile, String code) {
        this.biztype = biztype;
        this.mobile = mobile;
        this.code = code;
    }

    @Override
    public String path() {
        return "/mytio/sms/check.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("biztype", biztype)
                .append("code", code)
                .append("mobile", mobile);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
