package com.watayouxiang.webrtclib.interf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.webrtclib.model.SignalingParameters;

import org.webrtc.CameraVideoCapturer;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;
import org.webrtc.VideoSink;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/5/6
 * desc :
 */
public interface PeerConnectionOp {
    /**
     * 创建 PeerConnectionFactory
     */
    void createPeerConnectionFactory();

    /**
     * 创建 PeerConnection
     *
     * @param localRender
     * @param remoteSinks
     * @param signalingParameters
     */
    void createPeerConnection(@NonNull VideoSink localRender, @NonNull List<VideoSink> remoteSinks, @Nullable SignalingParameters signalingParameters, boolean renderVideo);

    /**
     * 释放 PeerConnection
     */
    void releasePeerConnection();

    /**
     * 创建 Sdp Offer
     */
    void createOffer();

    /**
     * 创建 Sdp Answer
     */
    void createAnswer();

    /**
     * 设置远程 Sdo
     *
     * @param sdp
     */
    void setRemoteDescription(SessionDescription sdp);

    /**
     * 添加 ice candidate 到 List 中
     *
     * @param candidate
     */
    void addRemoteIceCandidate(IceCandidate candidate);

    /**
     * 移除 ice
     *
     * @param candidates
     */
    void removeRemoteIceCandidates(IceCandidate[] candidates);

    /**
     * 是否反转视频容器
     * <p>
     * 是否互换了 "远端视频" 和 "本地视频"
     */
    boolean isSwitchVideoSink();

    /**
     * 切换视频容器
     * <p>
     * 是否互换 "远端视频" 和 "本地视频"
     */
    void switchVideoSink(boolean reverseVideoSink);

    /**
     * 设置视频最大比特率
     *
     * @param videoMaxBitrate
     */
    void setVideoMaxBitrate(Integer videoMaxBitrate);

    /**
     * 切换摄像头
     */
    void switchCamera(CameraVideoCapturer.CameraSwitchHandler handler);

    /**
     * 改变分辨率
     *
     * @param width
     * @param height
     * @param fps
     */
    void changeCaptureFormat(final int width, final int height, final int fps);

    /**
     * 是否为 HD
     *
     * @return
     */
    boolean isHDVideo();

    /**
     * 设置音频开关
     *
     * @param enable
     */
    void setLocalAudioEnable(final boolean enable);

    /**
     * 音频是否开启
     *
     * @return
     */
    boolean isLocalAudioEnable();

    /**
     * 设置视频流开关
     *
     * @param enable
     */
    void setVideoEnable(final boolean enable);

    /**
     * 视频流是否开启
     *
     * @return
     */
    boolean isVideoEnable();

    /**
     * 设置本地视频流开关
     *
     * @param enable
     */
    void setLocalVideoEnable(boolean enable);

    /**
     * 本地视频流是否开启
     *
     * @return
     */
    boolean isLocalVideoEnable();

    /**
     * 设置远程视频流开关
     *
     * @param enable
     */
    void setRemoteVideoEnable(boolean enable);

    /**
     * 远程视频流是否开启
     *
     * @return
     */
    boolean isRemoteVideoEnable();

    /**
     * 关闭视频源
     */
    void stopVideoSource();

    /**
     * 开启视频源
     */
    void startVideoSource();

    /**
     * 释放
     */
    void release();
}
