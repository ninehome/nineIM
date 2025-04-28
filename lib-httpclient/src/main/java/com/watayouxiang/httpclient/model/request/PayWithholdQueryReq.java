package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayWithholdQueryResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class PayWithholdQueryReq extends BaseReq<PayWithholdQueryResp> {
    private final String serialnumber;

    public PayWithholdQueryReq(String serialnumber) {
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
        return new TypeToken<BaseResp<PayWithholdQueryResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/withholdQuery.tio_x";
    }
}
