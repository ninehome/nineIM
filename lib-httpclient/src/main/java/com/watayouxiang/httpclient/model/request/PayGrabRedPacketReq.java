package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class PayGrabRedPacketReq extends BaseReq<PayGrabRedPacketResp> {
    /**
     * 红包的订单号
     */
    private final String serialnumber;
    /**
     * 会话id
     */
    private final String chatlinkid;

    public PayGrabRedPacketReq(String serialnumber, String chatlinkid) {
        this.serialnumber = serialnumber;
        this.chatlinkid = chatlinkid;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("serialnumber", serialnumber)
                .append("chatlinkid", chatlinkid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayGrabRedPacketResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/redPacket/grabRedpacket.tio_x";
    }
}
