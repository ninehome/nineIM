package com.watayouxiang.webrtclib.interf;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

/**
 * author : TaoWang
 * date : 2020/5/6
 * desc :
 */
public interface WebSocketChannelOp {
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
     * @param id
     */
    void callReplyReq(byte result, String reason, String id);

    /**
     * 挂断
     *
     * @param id
     */
    void callEndReq(String id);

    /**
     * 发送 offer sdp
     *
     * @param sdp
     * @param id
     */
    void offerSdpReq(SessionDescription sdp, String id);

    /**
     * 发送 answer sdp
     *
     * @param sdp
     * @param id
     */
    void answerSdpReq(SessionDescription sdp, String id);

    /**
     * 发送 offer ice
     *
     * @param iceCandidate
     * @param id
     */
    void offerIceReq(IceCandidate iceCandidate, String id);

    /**
     * 发送 answer ice
     *
     * @param iceCandidate
     * @param id
     */
    void answerIceReq(IceCandidate iceCandidate, String id);

    /**
     * 释放资源
     */
    void release();
}
