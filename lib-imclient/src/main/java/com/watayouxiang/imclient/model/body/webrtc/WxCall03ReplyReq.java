package com.watayouxiang.imclient.model.body.webrtc;

/**
 * b回复s：同意通话，或拒绝通话（1、同意通话、2、拒接）
 */
public class WxCall03ReplyReq extends WxBaseCall {
    /**
     * 1 同意通话，2 拒接，3 没有权限
     */
    public byte result;
    /**
     * 如果拒接，可以提供拒接原因
     * <p>
     * 不能通话的原因，当result=2时，此字段才有值
     */
    public String reason;
    /**
     * 通话id
     */
    public String id;

    public WxCall03ReplyReq(byte result, String reason, String id) {
        this.result = result;
        this.reason = reason;
        this.id = id;
    }
}
