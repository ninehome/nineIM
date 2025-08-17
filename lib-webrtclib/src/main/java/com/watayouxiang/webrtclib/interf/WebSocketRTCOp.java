package com.watayouxiang.webrtclib.interf;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * author : TaoWang
 * date : 2020/5/6
 * desc :
 */
public interface WebSocketRTCOp {
    /**
     * 去电
     *
     * @param toUid
     * @param type
     */
    void callReq(int toUid, byte type);

    /**
     * 取消去电
     */
    void callCancelReq();

    /**
     * 来电回复
     *
     * @param result
     * @param reason
     */
    void callReplyReq(byte result, String reason);

    /**
     * 挂断
     */
    void callEndReq();

    /**
     * 发送 offer sdp
     *
     * @param sdp
     */
    void offerSdpReq(SessionDescription sdp);

    /**
     * 发送 answer sdp
     *
     * @param sdp
     */
    void answerSdpReq(SessionDescription sdp);

    /**
     * 发送 offer ice candidate
     *
     * @param iceCandidate
     */
    void offerIceReq(IceCandidate iceCandidate);

    /**
     * 发送 answer ice candidate
     *
     * @param iceCandidate
     */
    void answerIceReq(IceCandidate iceCandidate);

    /**
     * 资源释放
     */
    void release();
}
