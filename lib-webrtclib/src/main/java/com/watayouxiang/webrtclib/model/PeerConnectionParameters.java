package com.watayouxiang.webrtclib.model;

/**
 * author : TaoWang
 * date : 2020/4/29
 * desc :
 */
public class PeerConnectionParameters {

    public static final String DISABLE_WEBRTC_AGC_FIELDTRIAL = "WebRTC-Audio-MinimizeResamplingOnMobile/Enabled/";

    public static final String AUDIO_ECHO_CANCELLATION_CONSTRAINT = "googEchoCancellation";
    public static final String AUDIO_AUTO_GAIN_CONTROL_CONSTRAINT = "googAutoGainControl";
    public static final String AUDIO_HIGH_PASS_FILTER_CONSTRAINT = "googHighpassFilter";
    public static final String AUDIO_NOISE_SUPPRESSION_CONSTRAINT = "googNoiseSuppression";
    public static final String AUDIO_CODEC_PARAM_BITRATE = "maxaveragebitrate";
    public static final String AUDIO_TRACK_ID = "ARDAMSa0";
    public static final String AUDIO_CODEC_ISAC = "ISAC";
    public static final String AUDIO_CODEC_OPUS = "opus";

    public static final String VIDEO_FLEXFEC_FIELDTRIAL = "WebRTC-FlexFEC-03-Advertised/Enabled/WebRTC-FlexFEC-03/Enabled/";
    public static final String VIDEO_VP8_INTEL_HW_ENCODER_FIELDTRIAL = "WebRTC-IntelVP8/Enabled/";
    public static final String VIDEO_CODEC_PARAM_START_BITRATE = "x-google-start-bitrate";
    public static final String VIDEO_TRACK_ID = "ARDAMSv0";
    public static final String VIDEO_TRACK_TYPE = "video";
    public static final String VIDEO_CODEC_VP8 = "VP8";
    public static final String VIDEO_CODEC_VP9 = "VP9";
    public static final String VIDEO_CODEC_H264 = "H264";
    public static final String VIDEO_CODEC_H264_HIGH = "H264 High";
    public static final String VIDEO_CODEC_H264_BASELINE = "H264 Baseline";

    public static final int BPS_IN_KBPS = 1000;
    public static final int HD_VIDEO_WIDTH = 1080;
    public static final int HD_VIDEO_HEIGHT = 1920;
    public static final int DEFAULT_VIDEO_FPS = 30;

    ////////////////////////////////////////////
    // PeerConnectionFactory
    ////////////////////////////////////////////
    private boolean loopback = false;// 回路
    private boolean tracing = false;
    private boolean saveInputAudioToFile = false;// 保存音频成文件
    private boolean useOpenSLES = false;
    private String videoCodec = VIDEO_CODEC_VP8;// 视频编解码器
    private boolean videoCodecHwAcceleration = true;
    private boolean videoFlexfecEnabled = false;
    private boolean disableBuiltInAEC = false;
    private boolean disableBuiltInNS = false;

    ////////////////////////////////////////////
    // PeerConnection
    ////////////////////////////////////////////
    private boolean aecDump = false;
    private boolean enableRtcEventLog = false;
    private boolean videoCallEnabled = true;// 是否为视频电话
    private boolean noAudioProcessing = false;// 无音频处理
    private DataChannelParameters dataChannelParameters = null;// 数据通道参数
    private String audioCodec = "OPUS";// 音频编解码器
    private int audioStartBitrate = 0;// 音频开启比特率
    private boolean disableWebRtcAGCAndHPF = false;

    ////////////////////////////////////////////////////
    // 视频捕获
    ////////////////////////////////////////////////////
    private String videoFileAsCamera = null;// 视频文件路径
    private boolean screenCaptureEnabled = false;// 屏幕录制是否开启
    private boolean useCamera2 = true;// 是否使用Camera2
    private boolean captureToTexture = true;

    ////////////////////////////////////////////////////
    // 视频清晰度、帧率
    ////////////////////////////////////////////////////
    private int videoWidth = HD_VIDEO_WIDTH;// 视频宽度
    private int videoHeight = HD_VIDEO_HEIGHT;// 视频高度
    private int videoFps = DEFAULT_VIDEO_FPS;// 视频帧率

    // ====================================================================================
    // getter setter
    // ====================================================================================


    public boolean isLoopback() {
        return loopback;
    }

    public void setLoopback(boolean loopback) {
        this.loopback = loopback;
    }

    public boolean isTracing() {
        return tracing;
    }

    public void setTracing(boolean tracing) {
        this.tracing = tracing;
    }

    public boolean isSaveInputAudioToFile() {
        return saveInputAudioToFile;
    }

    public void setSaveInputAudioToFile(boolean saveInputAudioToFile) {
        this.saveInputAudioToFile = saveInputAudioToFile;
    }

    public boolean isUseOpenSLES() {
        return useOpenSLES;
    }

    public void setUseOpenSLES(boolean useOpenSLES) {
        this.useOpenSLES = useOpenSLES;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public boolean isVideoCodecHwAcceleration() {
        return videoCodecHwAcceleration;
    }

    public void setVideoCodecHwAcceleration(boolean videoCodecHwAcceleration) {
        this.videoCodecHwAcceleration = videoCodecHwAcceleration;
    }

    public boolean isVideoFlexfecEnabled() {
        return videoFlexfecEnabled;
    }

    public void setVideoFlexfecEnabled(boolean videoFlexfecEnabled) {
        this.videoFlexfecEnabled = videoFlexfecEnabled;
    }

    public boolean isDisableBuiltInAEC() {
        return disableBuiltInAEC;
    }

    public void setDisableBuiltInAEC(boolean disableBuiltInAEC) {
        this.disableBuiltInAEC = disableBuiltInAEC;
    }

    public boolean isDisableBuiltInNS() {
        return disableBuiltInNS;
    }

    public void setDisableBuiltInNS(boolean disableBuiltInNS) {
        this.disableBuiltInNS = disableBuiltInNS;
    }

    public boolean isAecDump() {
        return aecDump;
    }

    public void setAecDump(boolean aecDump) {
        this.aecDump = aecDump;
    }

    public boolean isEnableRtcEventLog() {
        return enableRtcEventLog;
    }

    public void setEnableRtcEventLog(boolean enableRtcEventLog) {
        this.enableRtcEventLog = enableRtcEventLog;
    }

    public boolean isVideoCallEnabled() {
        return videoCallEnabled;
    }

    public void setVideoCallEnabled(boolean videoCallEnabled) {
        this.videoCallEnabled = videoCallEnabled;
    }

    public boolean isNoAudioProcessing() {
        return noAudioProcessing;
    }

    public void setNoAudioProcessing(boolean noAudioProcessing) {
        this.noAudioProcessing = noAudioProcessing;
    }

    public DataChannelParameters getDataChannelParameters() {
        return dataChannelParameters;
    }

    public void setDataChannelParameters(DataChannelParameters dataChannelParameters) {
        this.dataChannelParameters = dataChannelParameters;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public int getAudioStartBitrate() {
        return audioStartBitrate;
    }

    public void setAudioStartBitrate(int audioStartBitrate) {
        this.audioStartBitrate = audioStartBitrate;
    }

    public boolean isDisableWebRtcAGCAndHPF() {
        return disableWebRtcAGCAndHPF;
    }

    public void setDisableWebRtcAGCAndHPF(boolean disableWebRtcAGCAndHPF) {
        this.disableWebRtcAGCAndHPF = disableWebRtcAGCAndHPF;
    }

    public String getVideoFileAsCamera() {
        return videoFileAsCamera;
    }

    public void setVideoFileAsCamera(String videoFileAsCamera) {
        this.videoFileAsCamera = videoFileAsCamera;
    }

    public boolean isScreenCaptureEnabled() {
        return screenCaptureEnabled;
    }

    public void setScreenCaptureEnabled(boolean screenCaptureEnabled) {
        this.screenCaptureEnabled = screenCaptureEnabled;
    }

    public boolean isUseCamera2() {
        return useCamera2;
    }

    public void setUseCamera2(boolean useCamera2) {
        this.useCamera2 = useCamera2;
    }

    public boolean isCaptureToTexture() {
        return captureToTexture;
    }

    public void setCaptureToTexture(boolean captureToTexture) {
        this.captureToTexture = captureToTexture;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public int getVideoFps() {
        return videoFps;
    }

    public void setVideoFps(int videoFps) {
        this.videoFps = videoFps;
    }
}
