package com.watayouxiang.webrtclib.interf;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * author : TaoWang
 * date : 2020/4/23
 * desc :
 */
public interface WebSocketRTCEvents {
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
     * 有 b 端回复了通话请求，所有 b 端都会收到该通知
     */
    void onCallReplyReqNtf(WxCall03ReplyReqNtf callReplyReqNtf);

    /**
     * 来自远程的sdp
     *
     * @param sdp
     * @param initiator true answer, false offer
     */
    void onRemoteSdp(SessionDescription sdp, boolean initiator);

    /**
     * 来自远程的ice
     *
     * @param candidate
     * @param initiator true answer, false offer
     */
    void onRemoteIceCandidate(IceCandidate candidate, boolean initiator);

    /**
     * WebSocketRTC错误
     *
     * @param e
     */
    void onWebSocketError(Exception e);

    /**
     * WebSocketRTC关闭
     */
    void onWebSocketClosed();
}
