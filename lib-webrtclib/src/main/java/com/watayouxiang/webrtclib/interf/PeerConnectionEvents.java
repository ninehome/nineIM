package com.watayouxiang.webrtclib.interf;

import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;

/**
 * author : TaoWang
 * date : 2020/5/14
 * desc :
 */
public interface PeerConnectionEvents {
    /**
     * 设置本地 sdp 成功
     *
     * @param sdp
     */
    void onSetLocalSdpSuccess(SessionDescription sdp);

    /**
     * 收到iceCandidate
     *
     * @param iceCandidate
     * @param initiator
     */
    void onIceCandidate(IceCandidate iceCandidate, boolean initiator);

    /**
     * 移除iceCandidate
     *
     * @param iceCandidates
     */
    void onIceCandidatesRemoved(IceCandidate[] iceCandidates);

    /**
     * PeerConnection 出错
     *
     * @param error
     */
    void onPeerConnectionError(String error);

    /**
     * ice连接状态变化回调
     *
     * @param iceConnectionState
     */
    void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState);

    /**
     * PeerConnection 连接变化
     *
     * @param newState
     */
    void onConnectionChange(PeerConnection.PeerConnectionState newState);
}

