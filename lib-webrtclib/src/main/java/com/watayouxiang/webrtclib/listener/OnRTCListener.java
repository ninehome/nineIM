package com.watayouxiang.webrtclib.listener;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public interface OnRTCListener {
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
     * 通话取消
     *
     * @param callCancel
     */
    void onCallCancel(WxCall02_2CancelNtf callCancel);

    /**
     * 通话结束
     *
     * @param callEnd
     */
    void onCallEnd(WxCall14EndNtf callEnd);

    /**
     * 通话响应
     * <p>
     * 有 b 端回复了通话请求，所有 b 端都会收到该通知
     *
     * @param callResp
     */
    void onCallResp(WxCall03ReplyReqNtf callResp);

    /**
     * RTC关闭
     */
    void onRTCClosed();

    /**
     * RTC连接成功
     */
    void onRTCConnected();

    /**
     * PeerConnection 出错
     *
     * @param error 错误信息
     */
    void onPeerConnectionError(String error);

    /**
     * 信令服务器出错
     *
     * @param e 错误
     */
    void onWebSocketError(Exception e);

    /**
     * 信令服务器关闭
     */
    void onWebSocketClosed();
}
