package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayWithholdResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/18
 *     desc   :
 * </pre>
 */
public class PayWithholdReq extends BaseReq<PayWithholdResp> {
    /**
     * 充值金额，字符串，分
     */
    private final String amount;
    /**
     * 备注，选填
     */
    private String remark;

    public PayWithholdReq(String amount, String remark) {
        this.amount = amount;
        this.remark = remark;
    }

    public PayWithholdReq(String amount) {
        this.amount = amount;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("amount", amount)
                .append("remark", remark)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayWithholdResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/withhold.tio_x";
    }
}
