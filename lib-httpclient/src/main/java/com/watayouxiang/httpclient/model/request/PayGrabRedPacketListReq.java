package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketListResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class PayGrabRedPacketListReq extends BaseReq<PayGrabRedPacketListResp> {

    private final String pageNumber;
    private final String period;

    public PayGrabRedPacketListReq(String pageNumber, String period) {
        this.pageNumber = pageNumber;
        this.period = period;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("pageNumber", pageNumber)
                .append("period", period)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGrabRedPacketListResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/redPacket/grabRedpacketlist.tio_x";
    }
}
