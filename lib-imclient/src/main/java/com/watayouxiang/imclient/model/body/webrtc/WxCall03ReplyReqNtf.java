package com.watayouxiang.imclient.model.body.webrtc;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/17
 *     desc   : 有 b 端回复了通话请求，所有 b 端都会收到该通知
 *              {@link com.watayouxiang.imclient.packet.TioCommand#WX_CALL_03_REPLY_REQ_NTF}
 * </pre>
 */
public class WxCall03ReplyReqNtf extends WxCallItem {
    /**
     * 1、同意通话、2、拒接
     */
    public int result;
    /**
     * 不能通话的原因，当result=2时，此字段才有值
     */
    public String reason;
    /**
     * 是否是自己的回辞消息：1：是；2：否
     */
    public int self;
}
