package com.watayouxiang.webrtclib.interf;

import androidx.annotation.Nullable;

import com.watayouxiang.webrtclib.listener.OnRTCListener;
import com.watayouxiang.webrtclib.model.AudioDevice;
import com.watayouxiang.webrtclib.model.RTCViewHolder;

import org.webrtc.CameraVideoCapturer;
import org.webrtc.RendererCommon;

/**
 * author : TaoWang
 * date : 2020/5/6
 * desc :
 */
public interface TaoWebRTCOp {
    /**
     * 初始化
     *
     * @param holder
     */
    void init(RTCViewHolder holder);

    /**
     * 去电
     *
     * @param toUid 对方的uid
     * @param type  通话类型：1、 音频通话，2、视频通话
     */
    void call(int toUid, byte type);

    /**
     * 来电回应
     *
     * @param result 1、同意通话、2、拒接
     * @param reason 如果拒接，可以提供拒接原因。不能通话的原因，当result=2时，此字段才有值。
     */
    void callReply(byte result, String reason);

    /**
     * 取消去电
     */
    void callCancel();

    /**
     * 挂断
     */
    void hangUp();

    /**
     * 切换视频容器
     * <p>
     * 是否互换 "远端视频" 和 "本地视频"
     */
    void switchVideoSink(boolean reverseVideoSink);

    /**
     * 是否反转视频容器
     * <p>
     * 是否互换了 "远端视频" 和 "本地视频"
     */
    boolean isSwitchVideoSink();

    /**
     * 切换摄像头
     */
    void switchCamera(@Nullable CameraVideoCapturer.CameraSwitchHandler handler);

    /**
     * 设置本地视频缩放比率
     *
     * @param scalingType
     */
    void setLocalVideoScaling(RendererCommon.ScalingType scalingType);

    /**
     * 切换本地视频缩放样式
     */
    void switchLocalVideoScaling();

    /**
     * 设置远程视频缩放比率
     *
     * @param scalingType
     */
    void setRemoteVideoScaling(RendererCommon.ScalingType scalingType);

    /**
     * 切换远程视频缩放样式
     */
    void switchRemoteVideoScaling();

    /**
     * 设置音频开关
     *
     * @param enable
     */
    void setLocalAudioEnable(boolean enable);

    /**
     * 音频是否可用
     *
     * @return
     */
    boolean isLocalAudioEnable();

    /**
     * 开关麦克风
     */
    void toggleLocalAudio();

    /**
     * 设置视频渲染开关
     *
     * @param enable
     */
    void setVideoEnable(boolean enable);

    /**
     * 视频渲染是否可用
     *
     * @return
     */
    boolean isVideoEnable();

    /**
     * 切换视频渲染开关
     */
    void toggleVideoEnable();

    /**
     * 选择音频设备
     *
     * @param device
     */
    void setAudioDevice(AudioDevice device);

    AudioDevice getAudioDevice();

    /**
     * 切换音频设备
     */
    void toggleAudioDevice();

    /**
     * 设置全局监听器
     *
     * @param listener
     */
    void setOnGlobalRTCListener(OnRTCListener listener);

    /**
     * 注册RTC监听
     *
     * @param listener
     */
    void registerOnRTCListener(OnRTCListener listener);

    /**
     * 注销RTC监听
     *
     * @param listener
     */
    void unregisterOnRTCListener(OnRTCListener listener);

    /**
     * 释放资源
     */
    void release();
}
