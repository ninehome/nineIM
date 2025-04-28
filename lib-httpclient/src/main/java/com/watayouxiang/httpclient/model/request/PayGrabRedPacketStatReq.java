package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketStatResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class PayGrabRedPacketStatReq extends BaseReq<PayGrabRedPacketStatResp> {

    private final String period;

    public PayGrabRedPacketStatReq(String period) {
        this.period = period;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("period", period)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGrabRedPacketStatResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/redPacket/grabredpacketstat.tio_x";
    }
}
