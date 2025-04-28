package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PaySendRedPacketResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/19
 *     desc   :
 * </pre>
 */
public class PaySendRedPacketReq extends BaseReq<PaySendRedPacketResp> {
    /**
     * 1 普通红包，2 手气红包
     */
    private final String packetType;
    /**
     * 总金额
     */
    private final String amount;
    /**
     * 会话id
     */
    private final String chatlinkid;
    /**
     * 单笔金额:以分为单位普通红包
     */
    private final String singleAmount;
    /**
     * 红包数量:一对一红包数量为 1，普通群红包和拼手气红包 数量最大 100 个
     */
    private final String packetCount;
    /**
     * 备注，选填
     */
    private String remark;

    private String passwd;

    private long timestamp;

    public void setPasswd(String passwd, long timestamp) {
        this.passwd = passwd;
        this.timestamp = timestamp;
    }

    public PaySendRedPacketReq(String packetType, String amount, String chatlinkid, String singleAmount, String packetCount, String remark) {
        this.packetType = packetType;
        this.amount = amount;
        this.chatlinkid = chatlinkid;
        this.singleAmount = singleAmount;
        this.packetCount = packetCount;
        this.remark = remark;
    }

    public PaySendRedPacketReq(String packetType, String amount, String chatlinkid, String singleAmount, String packetCount) {
        this.packetType = packetType;
        this.amount = amount;
        this.chatlinkid = chatlinkid;
        this.singleAmount = singleAmount;
        this.packetCount = packetCount;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("packetType", packetType)
                .append("amount", amount)
                .append("chatlinkid", chatlinkid)
                .append("singleAmount", singleAmount)
                .append("packetCount", packetCount)
                .append("remark", remark)
                .append("passwd", passwd)
                .append("timestamp", String.valueOf(timestamp))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PaySendRedPacketResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/redPacket/sendRedpacket.tio_x";
    }
}
