package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayRechargeQueryResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class PayRechargeQueryReq extends BaseReq<PayRechargeQueryResp> {
    private final String serialnumber;

    public PayRechargeQueryReq(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("serialnumber", serialnumber)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayRechargeQueryResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/rechargeQuery.tio_x";
    }
}
