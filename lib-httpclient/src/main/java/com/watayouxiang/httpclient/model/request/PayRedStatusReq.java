package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayRedStatusResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class PayRedStatusReq extends BaseReq<PayRedStatusResp> {
    /**
     * 红包的订单号
     */
    private final String serialnumber;

    public PayRedStatusReq(String serialnumber) {
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
        return new TypeToken<BaseResp<PayRedStatusResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/redPacket/redStatus.tio_x";
    }
}
