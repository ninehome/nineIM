package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AnjiCaptchaCheckResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/18
 *     desc   :
 * </pre>
 */
public class AnjiCaptchaCheckReq extends BaseReq<AnjiCaptchaCheckResp> {
    private String captchaType;
    private String token;
    private String pointJson;

    public AnjiCaptchaCheckReq(String captchaType, String token, String pointJson) {
        this.captchaType = captchaType;
        this.token = token;
        this.pointJson = pointJson;
    }

    public AnjiCaptchaCheckReq(String token, String pointJson) {
        this.captchaType = "blockPuzzle";
        this.token = token;
        this.pointJson = pointJson;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("captchaType", captchaType)
                .append("token", token)
                .append("pointJson", pointJson)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<AnjiCaptchaCheckResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/anjiCaptcha/check.tio_x";
    }
}
