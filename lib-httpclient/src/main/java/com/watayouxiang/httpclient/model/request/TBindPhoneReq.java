package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.TBindPhoneResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/28/20
 *     desc   : 三方绑定手机号
 * </pre>
 */
public class TBindPhoneReq extends BaseReq<TBindPhoneResp> {

    private final String code;
    private final String phone;

    public TBindPhoneReq(String code, String phone) {
        this.code = code;
        this.phone = phone;
    }

    @Override
    public String path() {
        return "/mytio/user/thirdbindphone.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("code", code)
                .append("phone", phone)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<TBindPhoneResp>>() {
        }.getType();
    }
}
