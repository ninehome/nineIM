package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/14
 * desc :
 */
public class UpdateSignReq extends BaseReq<Void> {
    private String sign;

    public UpdateSignReq(String sign) {
        this.sign = sign;
    }

    @Override
    public String path() {
        return "/mytio/user/updatSign.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("sign", sign);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
