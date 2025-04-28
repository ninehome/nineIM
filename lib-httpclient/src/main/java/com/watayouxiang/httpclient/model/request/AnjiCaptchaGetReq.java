package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AnjiCaptchaGetResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/18
 *     desc   :
 * </pre>
 */
public class AnjiCaptchaGetReq extends BaseReq<AnjiCaptchaGetResp> {
    private String captchaType;

    public AnjiCaptchaGetReq(String captchaType) {
        this.captchaType = captchaType;
    }

    public AnjiCaptchaGetReq() {
        captchaType = "blockPuzzle";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<AnjiCaptchaGetResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/anjiCaptcha/get.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("captchaType", captchaType)
                ;
    }
}
