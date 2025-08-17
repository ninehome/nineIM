package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayOpenFlagResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/17
 *     desc   :
 * </pre>
 */
public class PayOpenFlagReq extends BaseReq<PayOpenFlagResp> {

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap();
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayOpenFlagResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/openflag.tio_x";
    }
}
