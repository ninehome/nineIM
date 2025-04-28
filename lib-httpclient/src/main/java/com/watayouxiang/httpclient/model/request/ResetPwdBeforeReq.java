package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ResetPwdBeforeResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public class ResetPwdBeforeReq extends BaseReq<ResetPwdBeforeResp> {
    private final String code;
    private final String phone;

    public ResetPwdBeforeReq(String code, String phone) {
        this.code = code;
        this.phone = phone;
    }

    @Override
    public String path() {
        return "/mytio/user/resetPwdBefore.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("code", code)
                .append("phone", phone)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ResetPwdBeforeResp>>() {
        }.getType();
    }
}
