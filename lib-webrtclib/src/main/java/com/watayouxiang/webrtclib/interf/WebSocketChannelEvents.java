package com.watayouxiang.webrtclib.interf;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall06OfferSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall08AnswerSdpNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall10OfferIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall12AnswerIceNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;

/**
 * author : TaoWang
 * date : 2020/4/30
 * desc :
 */
public interface WebSocketChannelEvents {
    /**
     * 来电
     *
     * @param call
     */
    void onCall(WxCall02Ntf call);

    /**
     * 去电回复
     *
     * @param callReply
     */
    void onCallReply(WxCall04ReplyNtf callReply);

    /**
     * 取消通话
     *
     * @param callCancel
     */
    void onCallCancel(WxCall02_2CancelNtf callCancel);

    /**
     * 结束通话
     *
     * @param callEnd
     */
    void onCallEnd(WxCall14EndNtf callEnd);

    /**
     * 提供SDP
     *
     * @param offerSdp
     */
    void onOfferSdp(WxCall06OfferSdpNtf offerSdp);

    /**
     * 回复SDP
     *
     * @param answerSdp
     */
    void onAnswerSdp(WxCall08AnswerSdpNtf answerSdp);

    /**
     * 提供IceCandidate
     *
     * @param offerIce
     */
    void onOfferIceCandidate(WxCall10OfferIceNtf offerIce);

    /**
     * 回复IceCandidate
     *
     * @param answerIce
     */
    void onAnswerIceCandidate(WxCall12AnswerIceNtf answerIce);

    /**
     * WebSocketChannel出错
     *
     * @param e
     */
    void onWebSocketChannelError(Exception e);

    /**
     * WebSocketChannel关闭
     */
    void onWebSocketChannelClosed();

    /**
     * 有 b 端回复了通话请求，所有 b 端都会收到该通知
     */
    void onCallReplyReqNtf(WxCall03ReplyReqNtf callReplyReqNtf);
}
