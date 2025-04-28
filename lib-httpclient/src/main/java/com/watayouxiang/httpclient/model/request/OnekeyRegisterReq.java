package com.watayouxiang.httpclient.model.request;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PhoneRegisterResp;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/23/20
 *     desc   : 手机注册
 * </pre>
 */
public class OnekeyRegisterReq extends BaseReq<String> {
    private final String tokenId;

    private final String token;

    private final String name;
    /**
     * 是否同意协议
     */
    private final String agreement;

    public OnekeyRegisterReq(String tokenId, String token, String name) {
        this.tokenId = tokenId;
        this.token = token;
        this.name = name;
        this.agreement = "on";
    }

    @Override
    public String path() {
        return "/mytio/oauth/register.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("tokenId", tokenId)
                .append("token", token)
                .append("name", name)
                .append("agreement", agreement);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
