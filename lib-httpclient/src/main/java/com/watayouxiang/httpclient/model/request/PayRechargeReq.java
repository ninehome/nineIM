package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayRechargeResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/17
 *     desc   :
 * </pre>
 */
public class PayRechargeReq extends BaseReq<PayRechargeResp> {
    /**
     * 充值金额，字符串，分
     */
    private final String amount;
    /**
     * 备注，选填
     */
    private String remark;

    public PayRechargeReq(String amount, String remark) {
        this.amount = amount;
        this.remark = remark;
    }

    public PayRechargeReq(String amount) {
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
        return new TypeToken<BaseResp<PayRechargeResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/recharge.tio_x";
    }
}
