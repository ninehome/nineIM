package com.watayouxiang.webrtclib.client;

import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.webrtclib.audio.AudioTool;
import com.watayouxiang.webrtclib.audio.RecordedAudioToFileController;
import com.watayouxiang.webrtclib.interf.PeerConnectionEvents;
import com.watayouxiang.webrtclib.interf.PeerConnectionOp;
import com.watayouxiang.webrtclib.log.LogTool;
import com.watayouxiang.webrtclib.log.RtcEventLog;
import com.watayouxiang.webrtclib.model.PeerConnectionParameters;
import com.watayouxiang.webrtclib.model.SignalingParameters;
import com.watayouxiang.webrtclib.tool.BitrateTool;
import com.watayouxiang.webrtclib.tool.CodecTool;
import com.watayouxiang.webrtclib.tool.ICETool;
import com.watayouxiang.webrtclib.tool.Tool;
import com.watayouxiang.webrtclib.tool.VideoTool;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.CameraVideoCapturer;
import org.webrtc.CandidatePairChangeEvent;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.Logging;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpParameters;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpSender;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.SoftwareVideoDecoderFactory;
import org.webrtc.SoftwareVideoEncoderFactory;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoDecoderFactory;
import org.webrtc.VideoEncoderFactory;
import org.webrtc.VideoSink;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;
import org.webrtc.audio.AudioDeviceModule;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * author : TaoWang
 * date : 2020/4/30
 * desc : ICE连接客户端
 */
public class PeerConnectionClient implements PeerConnectionOp {

    ////////////////////////////////////////////
    // init
    ////////////////////////////////////////////
    @NonNull
    private final EglBase rootEglBase;
    @NonNull
    private final Context appContext;
    @NonNull
    private final PeerConnectionEvents events;
    @NonNull
    private final PeerConnectionParameters peerConnectionParameters;// 连接参数

    @NonNull
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();// 单一线程池
    @NonNull
    private final PCObserver pcObserver = new PCObserver();
    @NonNull
    private final SDPObserver sdpObserver = new SDPObserver();
    private final boolean dataChannelEnabled;// 数据通道是否可用

    ////////////////////////////////////////////
    // PeerConnectionFactory
    ////////////////////////////////////////////
    @Nullable
    private PeerConnectionFactory factory;// 点对点连接工厂
    @Nullable
    private RecordedAudioToFileController saveRecordedAudioToFile;// 保存成录音文件
    private boolean preferIsac;

    ////////////////////////////////////////////
    // PeerConnection
    ////////////////////////////////////////////
    @Nullable
    private VideoCapturer videoCapturer;// 视频获取器
    @Nullable
    private List<IceCandidate> queuedRemoteCandidates;// ice集合
    @Nullable
    private PeerConnection peerConnection;// 点对点连接
    @Nullable
    private MediaConstraints audioConstraints;// 音频约束
    @Nullable
    private MediaConstraints sdpMediaConstraints;// sdp约束
    @Nullable
    private VideoTrack localVideoTrack;// 本地视频Track
    @Nullable
    private AudioTrack localAudioTrack;// 本地音频Track
    @Nullable
    private RtpSender localVideoSender;// 本地视频Sender
    @Nullable
    private VideoTrack remoteVideoTrack;// 远端视频Track
    @Nullable
    private VideoSource videoSource;// 视频源
    @Nullable
    private AudioSource audioSource;//音频源
    @Nullable
    private DataChannel dataChannel;// 数据通道
    @Nullable
    private RtcEventLog rtcEventLog;// 日志
    private Boolean initiator;// true 通话发起人，false 通话接收人
    @Nullable
    private VideoSink localSink;// 本地视频容器
    @Nullable
    private List<VideoSink> remoteSinks;// 远端视频容器
    private boolean reverseVideoSink;// 是否反转视频容器

    public PeerConnectionClient(@NonNull Context appContext, @NonNull EglBase eglBase, @NonNull PeerConnectionParameters peerConnectionParameters, @NonNull PeerConnectionEvents events) {
        this.rootEglBase = eglBase;
        this.appContext = appContext;
        this.events = events;
        this.peerConnectionParameters = peerConnectionParameters;
        this.dataChannelEnabled = peerConnectionParameters.getDataChannelParameters() != null;

        final String fieldTrials = Tool.getFieldTrials(peerConnectionParameters);
        executor.execute(() -> PeerConnectionFactory.initialize(
                PeerConnectionFactory.InitializationOptions.builder(appContext)
                        .setFieldTrials(fieldTrials)
                        .setEnableInternalTracer(true)
                        .createInitializationOptions()));
    }

    // ====================================================================================
    // PeerConnectionOp
    // ====================================================================================

    @Override
    public void createPeerConnectionFactory() {
        executor.execute(() -> {
            if (factory != null) {
                throw new IllegalStateException("PeerConnectionFactory has already been constructed");
            }
            createPeerConnectionFactoryInternal();
        });
    }

    private void createPeerConnectionFactoryInternal() {
        // options
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        if (peerConnectionParameters.isLoopback()) {
            options.networkIgnoreMask = 0;
        }

        // tracing
        if (peerConnectionParameters.isTracing()) {
            PeerConnectionFactory.startInternalTracingCapture(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "webrtc-trace.txt");
        }

        // Check if ISAC is used by default.
        preferIsac = peerConnectionParameters.getAudioCodec() != null && peerConnectionParameters.getAudioCodec().equals(PeerConnectionParameters.AUDIO_CODEC_ISAC);

        // AudioDeviceModule
        if (peerConnectionParameters.isSaveInputAudioToFile()) {
            if (!peerConnectionParameters.isUseOpenSLES()) {
                RTCLog.d("Enable recording of microphone input audio to file");
                saveRecordedAudioToFile = new RecordedAudioToFileController(executor);
            } else {
                RTCLog.e("Recording of input audio is not supported for OpenSL ES");
            }
        }
        final AudioDeviceModule audioDeviceModule = AudioTool.createJavaAudioDevice(peerConnectionParameters, events, appContext, saveRecordedAudioToFile);

        // Create peer connection factory.
        final boolean enableH264HighProfile = PeerConnectionParameters.VIDEO_CODEC_H264_HIGH.equals(peerConnectionParameters.getVideoCodec());
        final VideoEncoderFactory encoderFactory;
        final VideoDecoderFactory decoderFactory;

        if (peerConnectionParameters.isVideoCodecHwAcceleration()) {
            encoderFactory = new DefaultVideoEncoderFactory(rootEglBase.getEglBaseContext(), true, enableH264HighProfile);
            decoderFactory = new DefaultVideoDecoderFactory(rootEglBase.getEglBaseContext());
        } else {
            encoderFactory = new SoftwareVideoEncoderFactory();
            decoderFactory = new SoftwareVideoDecoderFactory();
        }

        factory = PeerConnectionFactory.builder()
                .setOptions(options)
                .setAudioDeviceModule(audioDeviceModule)
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory)
                .createPeerConnectionFactory();
        audioDeviceModule.release();
        RTCLog.d("peer connection factory created");
    }

    @Override
    public void createPeerConnection(@NonNull VideoSink localSink, @NonNull List<VideoSink> remoteSinks, @Nullable SignalingParameters signalingParameters, boolean renderVideo) {
        executor.execute(() -> {
            if (factory == null) {
                throw new NullPointerException("not create PeerConnectionFactory");
            }
            try {
                this.reverseVideoSink = false;
                this.localSink = localSink;
                this.remoteSinks = remoteSinks;
                videoCapturer = VideoTool.createVideoCapturer(peerConnectionParameters, events, appContext);
                createMediaConstraintsInternal();
                createPeerConnectionInternal(localSink, remoteSinks, signalingParameters, renderVideo);
                maybeCreateAndStartRtcEventLog();
            } catch (Exception e) {
                events.onPeerConnectionError("create peer connection error：" + e.getMessage());
                throw e;
            }
        });
    }

    private void createMediaConstraintsInternal() {
        // Create video constraints if video call is enabled.
        if (isVideoCallEnabled()) {
            // If video resolution is not specified, default to HD.
            if (peerConnectionParameters.getVideoWidth() == 0 || peerConnectionParameters.getVideoHeight() == 0) {
                peerConnectionParameters.setVideoWidth(PeerConnectionParameters.HD_VIDEO_WIDTH);
                peerConnectionParameters.setVideoHeight(PeerConnectionParameters.HD_VIDEO_HEIGHT);
            }
            // If fps is not specified, default to 30.
            if (peerConnectionParameters.getVideoFps() == 0) {
                peerConnectionParameters.setVideoFps(30);
            }
            RTCLog.d("Capturing format: " + peerConnectionParameters.getVideoWidth() + "x" + peerConnectionParameters.getVideoHeight() + "@" + peerConnectionParameters.getVideoFps());
        }

        // Create audio constraints.
        audioConstraints = new MediaConstraints();
        // added for audio performance measurements
        if (peerConnectionParameters.isNoAudioProcessing()) {
            RTCLog.d("Disabling audio processing");
            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair(PeerConnectionParameters.AUDIO_ECHO_CANCELLATION_CONSTRAINT, "false"));
            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair(PeerConnectionParameters.AUDIO_AUTO_GAIN_CONTROL_CONSTRAINT, "false"));
            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair(PeerConnectionParameters.AUDIO_HIGH_PASS_FILTER_CONSTRAINT, "false"));
            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair(PeerConnectionParameters.AUDIO_NOISE_SUPPRESSION_CONSTRAINT, "false"));
        }
        // Create SDP constraints.
        sdpMediaConstraints = new MediaConstraints();
        sdpMediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        sdpMediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", Boolean.toString(isVideoCallEnabled())));
    }

    private void createPeerConnectionInternal(@NonNull VideoSink localRender, @NonNull List<VideoSink> remoteSinks, @Nullable SignalingParameters signalingParameters, boolean renderVideo) {
        RTCLog.d("renderVideo = " + renderVideo);
        if (factory == null) {
            return;
        }

        queuedRemoteCandidates = new LinkedList<>();

        ////////////////////////////////////////////////////
        // peerConnection
        ////////////////////////////////////////////////////
        List<PeerConnection.IceServer> iceServers = ICETool.getIceServers(signalingParameters);
        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(iceServers);
        // TCP candidates are only useful when connecting to a server that supports
        // ICE-TCP.
        rtcConfig.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
        rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
        rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
        rtcConfig.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
        // Use ECDSA encryption.
        rtcConfig.keyType = PeerConnection.KeyType.ECDSA;
        // Enable DTLS for normal calls and disable for loopback calls.
        rtcConfig.enableDtlsSrtp = !peerConnectionParameters.isLoopback();
        rtcConfig.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN;

        peerConnection = factory.createPeerConnection(rtcConfig, pcObserver);

        ////////////////////////////////////////////////////
        // dataChannel
        ////////////////////////////////////////////////////
        if (dataChannelEnabled) {
            DataChannel.Init init = new DataChannel.Init();
            init.ordered = peerConnectionParameters.getDataChannelParameters().ordered;
            init.negotiated = peerConnectionParameters.getDataChannelParameters().negotiated;
            init.maxRetransmits = peerConnectionParameters.getDataChannelParameters().maxRetransmits;
            init.maxRetransmitTimeMs = peerConnectionParameters.getDataChannelParameters().maxRetransmitTimeMs;
            init.id = peerConnectionParameters.getDataChannelParameters().id;
            init.protocol = peerConnectionParameters.getDataChannelParameters().protocol;
            if (peerConnection != null) {
                dataChannel = peerConnection.createDataChannel("ApprtcDemo data", init);
            }
        }

        // Set INFO libjingle logging.
        // NOTE: this _must_ happen while |factory| is alive!
        Logging.enableLogToDebugOutput(Logging.Severity.LS_INFO);

        ////////////////////////////////////////////////////
        // init video
        ////////////////////////////////////////////////////
        List<String> mediaStreamLabels = Collections.singletonList("ARDAMS");
        if (isVideoCallEnabled()) {
            // create videoSource
            videoSource = factory.createVideoSource(videoCapturer.isScreencast());
            if (videoSource != null) {
                // start capture video
                SurfaceTextureHelper surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", rootEglBase.getEglBaseContext());
                videoCapturer.initialize(surfaceTextureHelper, appContext, videoSource.getCapturerObserver());
                videoCapturer.startCapture(peerConnectionParameters.getVideoWidth(), peerConnectionParameters.getVideoHeight(), peerConnectionParameters.getVideoFps());
                // create localVideoTrack
                localVideoTrack = factory.createVideoTrack(PeerConnectionParameters.VIDEO_TRACK_ID, videoSource);
                localVideoTrack.setEnabled(renderVideo);
                localVideoTrack.addSink(localRender);
            }
            // peerConnection add localVideoTrack
            if (peerConnection != null && localVideoTrack != null) {
                peerConnection.addTrack(localVideoTrack, mediaStreamLabels);
            }
            // find remoteVideoTrack
            if (peerConnection != null) {
                remoteVideoTrack = VideoTool.getRemoteVideoTrack(peerConnection);
            }
            if (remoteVideoTrack != null) {
                remoteVideoTrack.setEnabled(renderVideo);
                for (VideoSink remoteSink : remoteSinks) {
                    remoteVideoTrack.addSink(remoteSink);
                }
            }
        }
        // create AudioSource
        if (audioConstraints != null) {
            audioSource = factory.createAudioSource(audioConstraints);
        }
        // create localAudioTrack
        if (audioSource != null) {
            localAudioTrack = factory.createAudioTrack(PeerConnectionParameters.AUDIO_TRACK_ID, audioSource);
            localAudioTrack.setEnabled(true);
        }
        // peerConnection add localAudioTrack
        if (localAudioTrack != null && peerConnection != null) {
            peerConnection.addTrack(localAudioTrack, mediaStreamLabels);
        }
        // find localVideoSender
        if (peerConnection != null) {
            localVideoSender = VideoTool.findVideoSender(peerConnection);
        }

        ////////////////////////////////////////////////////
        // startAecDump
        ////////////////////////////////////////////////////

        if (peerConnectionParameters.isAecDump()) {
            try {
                ParcelFileDescriptor aecDumpFileDescriptor = ParcelFileDescriptor.open(
                        new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Download/audio.aecdump"),
                        ParcelFileDescriptor.MODE_READ_WRITE | ParcelFileDescriptor.MODE_CREATE | ParcelFileDescriptor.MODE_TRUNCATE);
                factory.startAecDump(aecDumpFileDescriptor.detachFd(), -1);
            } catch (IOException e) {
                RTCLog.e("Can not open aecdump file " + e.getMessage());
            }
        }

        ////////////////////////////////////////////////////
        // start saveRecordedAudioToFile
        ////////////////////////////////////////////////////

        if (saveRecordedAudioToFile != null) {
            if (saveRecordedAudioToFile.start()) {
                RTCLog.d("Recording input audio to file is activated");
            }
        }

        RTCLog.d("peer connection created");
    }

    private void maybeCreateAndStartRtcEventLog() {
        if (peerConnection != null && peerConnectionParameters.isEnableRtcEventLog()) {
            rtcEventLog = new RtcEventLog(peerConnection);
            rtcEventLog.start(LogTool.createRtcEventLogOutputFile(appContext));
        } else {
            RTCLog.d("RtcEventLog is disabled.");
        }
    }

    private boolean isVideoCallEnabled() {
        return peerConnectionParameters.isVideoCallEnabled() && videoCapturer != null;
    }

    @Override
    public void createOffer() {
        executor.execute(() -> {
            if (peerConnection != null && sdpMediaConstraints != null) {
                peerConnection.createOffer(sdpObserver, sdpMediaConstraints);
            }
        });
    }

    @Override
    public void createAnswer() {
        executor.execute(() -> {
            if (peerConnection != null && sdpMediaConstraints != null) {
                peerConnection.createAnswer(sdpObserver, sdpMediaConstraints);
            }
        });
    }

    private void setLocalDescriptionInternal(SessionDescription sdp) {
        if (peerConnection != null) {
            String sdpDescription = sdp.description;
            if (preferIsac) {
                sdpDescription = CodecTool.preferCodec(sdpDescription, PeerConnectionParameters.AUDIO_CODEC_ISAC, true);
            }
            if (isVideoCallEnabled()) {
                sdpDescription = CodecTool.preferCodec(sdpDescription, CodecTool.getSdpVideoCodecName(peerConnectionParameters), false);
            }
            SessionDescription sdpLocal = new SessionDescription(sdp.type, sdpDescription);
            peerConnection.setLocalDescription(sdpObserver, sdpLocal);
        }
    }

    @Override
    public void setRemoteDescription(SessionDescription sdp) {
        executor.execute(() -> {
            if (peerConnection != null) {
                String sdpDescription = sdp.description;
                if (preferIsac) {
                    sdpDescription = CodecTool.preferCodec(sdpDescription, PeerConnectionParameters.AUDIO_CODEC_ISAC, true);
                }
                if (isVideoCallEnabled()) {
                    sdpDescription = CodecTool.preferCodec(sdpDescription, CodecTool.getSdpVideoCodecName(peerConnectionParameters), false);
                }
                if (peerConnectionParameters.getAudioStartBitrate() > 0) {
                    sdpDescription = BitrateTool.setStartBitrate(PeerConnectionParameters.AUDIO_CODEC_OPUS, false, sdpDescription, peerConnectionParameters.getAudioStartBitrate());
                }
                SessionDescription sdpRemote = new SessionDescription(sdp.type, sdpDescription);
                peerConnection.setRemoteDescription(sdpObserver, sdpRemote);
            }
        });
    }

    @Override
    public void addRemoteIceCandidate(IceCandidate candidate) {
        executor.execute(() -> {
            if (queuedRemoteCandidates != null) {
                queuedRemoteCandidates.add(candidate);
            } else {
                // 如果 queuedRemoteCandidates = null，说明已经设置好 localSdp 和 remoteSdp 了
                // 那么就直接添加ice到peerConnection
                addIceCandidateInternal(candidate);
            }
        });
    }

    @Override
    public void removeRemoteIceCandidates(IceCandidate[] candidates) {
        executor.execute(() -> {
            if (peerConnection != null) {
                // Drain the queued remote candidates if there is any so that
                // they are processed in the proper order.
                drainCandidatesInternal();
                peerConnection.removeIceCandidates(candidates);
            }
        });
    }

    /**
     * 将 List 中所有的 ice candidate 一次性写入 PeerConnection
     */
    private void drainCandidatesInternal() {
        if (queuedRemoteCandidates != null) {
            for (IceCandidate candidate : queuedRemoteCandidates) {
                addIceCandidateInternal(candidate);
            }
            queuedRemoteCandidates = null;
        }
    }

    private void addIceCandidateInternal(IceCandidate candidate) {
        if (peerConnection != null) {
            peerConnection.addIceCandidate(candidate);
            RTCLog.d("---> addIceCandidate");
        }
    }

    @Override
    public boolean isSwitchVideoSink() {
        return reverseVideoSink;
    }

    @Override
    public void switchVideoSink(boolean reverseVideoSink) {
        executor.execute(() -> {
            if (remoteVideoTrack != null && remoteSinks != null && remoteSinks.size() != 0
                    && localVideoTrack != null && localSink != null) {

                VideoSink remoteSink = remoteSinks.get(0);

                if (reverseVideoSink) {
                    localVideoTrack.removeSink(localSink);
                    remoteVideoTrack.removeSink(remoteSink);

                    localVideoTrack.addSink(remoteSink);
                    remoteVideoTrack.addSink(localSink);
                } else {
                    localVideoTrack.removeSink(remoteSink);
                    remoteVideoTrack.removeSink(localSink);

                    localVideoTrack.addSink(localSink);
                    remoteVideoTrack.addSink(remoteSink);
                }

                this.reverseVideoSink = reverseVideoSink;
            }
        });
    }

    @Override
    public void setVideoMaxBitrate(Integer maxBitrateKbps) {
        executor.execute(() -> {
            if (localVideoSender != null) {
                RTCLog.d("Requested max video bitrate: " + maxBitrateKbps);
                RtpParameters parameters = localVideoSender.getParameters();
                if (parameters.encodings.size() == 0) {
                    RTCLog.w("RtpParameters are not ready.");
                    return;
                }
                for (RtpParameters.Encoding encoding : parameters.encodings) {
                    // Null value means no limit.
                    encoding.maxBitrateBps = maxBitrateKbps == null ? null : maxBitrateKbps * PeerConnectionParameters.BPS_IN_KBPS;
                }
                if (!localVideoSender.setParameters(parameters)) {
                    RTCLog.e("RtpSender.setParameters failed.");
                }
                RTCLog.d("Configured max video bitrate to: " + maxBitrateKbps);
            }
        });
    }

    @Override
    public void switchCamera(CameraVideoCapturer.CameraSwitchHandler handler) {
        executor.execute(() -> {
            if (videoCapturer instanceof CameraVideoCapturer) {
                CameraVideoCapturer cameraVideoCapturer = (CameraVideoCapturer) videoCapturer;
                cameraVideoCapturer.switchCamera(handler);
            }
        });
    }

    @Override
    public void changeCaptureFormat(final int width, final int height, final int fps) {
        executor.execute(() -> {
            if (videoSource != null) {
                videoSource.adaptOutputFormat(width, height, fps);
                RTCLog.d("changeCaptureFormat: " + width + "x" + height + "@" + fps);
            }
        });
    }

    @Override
    public boolean isHDVideo() {
        return isVideoCallEnabled() && peerConnectionParameters.getVideoWidth() * peerConnectionParameters.getVideoHeight() >= 1280 * 720;
    }

    @Override
    public void setLocalAudioEnable(final boolean enable) {
        executor.execute(() -> {
            if (localAudioTrack != null) {
                localAudioTrack.setEnabled(enable);
            }
        });
    }

    @Override
    public boolean isLocalAudioEnable() {
        if (localAudioTrack != null) {
            return localAudioTrack.enabled();
        }
        return false;
    }

    @Override
    public void setLocalVideoEnable(boolean enable) {
        executor.execute(() -> {
            if (localVideoTrack != null) {
                localVideoTrack.setEnabled(enable);
            }
        });
    }

    @Override
    public boolean isLocalVideoEnable() {
        if (localVideoTrack != null) {
            return localVideoTrack.enabled();
        }
        return false;
    }

    @Override
    public void setRemoteVideoEnable(boolean enable) {
        executor.execute(() -> {
            if (remoteVideoTrack != null) {
                remoteVideoTrack.setEnabled(enable);
            }
        });
    }

    @Override
    public boolean isRemoteVideoEnable() {
        if (remoteVideoTrack != null) {
            return remoteVideoTrack.enabled();
        }
        return false;
    }


    @Override
    public void setVideoEnable(final boolean enable) {
        setLocalVideoEnable(enable);
        setRemoteVideoEnable(enable);
    }

    @Override
    public boolean isVideoEnable() {
        return isLocalVideoEnable() && isRemoteVideoEnable();
    }

    @Override
    public void startVideoSource() {
        executor.execute(() -> {
            if (videoCapturer != null) {
                RTCLog.d("Restart video source.");
                videoCapturer.startCapture(peerConnectionParameters.getVideoWidth(), peerConnectionParameters.getVideoHeight(), peerConnectionParameters.getVideoFps());
            }
        });
    }

    @Override
    public void stopVideoSource() {
        executor.execute(() -> {
            if (videoCapturer != null) {
                RTCLog.d("Stop video source.");
                try {
                    videoCapturer.stopCapture();
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    @Override
    public void release() {
        executor.execute(() -> {
            releasePeerConnectionInternal();
            releasePeerConnectionFactoryInternal();
        });
    }

    @Override
    public void releasePeerConnection() {
        executor.execute(this::releasePeerConnectionInternal);
    }

    private void releasePeerConnectionFactoryInternal() {
        if (saveRecordedAudioToFile != null) {
            saveRecordedAudioToFile.stop();
            saveRecordedAudioToFile = null;
        }
        if (factory != null) {
            if (peerConnectionParameters.isAecDump()) {
                factory.stopAecDump();
            }
            factory.dispose();
            factory = null;
        }
        PeerConnectionFactory.stopInternalTracingCapture();
        PeerConnectionFactory.shutdownInternalTracer();
    }

    private void releasePeerConnectionInternal() {
        if (dataChannel != null) {
            dataChannel.dispose();
            dataChannel = null;
        }
        if (rtcEventLog != null) {
            // RtcEventLog should stop before the peer connection is disposed.
            rtcEventLog.stop();
            rtcEventLog = null;
        }
        if (peerConnection != null) {
            peerConnection.dispose();
            peerConnection = null;
        }
        if (audioSource != null) {
            audioSource.dispose();
            audioSource = null;
        }
        if (videoCapturer != null) {
            try {
                videoCapturer.stopCapture();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            videoCapturer.dispose();
            videoCapturer = null;
        }
        if (videoSource != null) {
            videoSource.dispose();
            videoSource = null;
        }
        if (queuedRemoteCandidates != null) {
            queuedRemoteCandidates.clear();
            queuedRemoteCandidates = null;
        }
        audioConstraints = null;
        sdpMediaConstraints = null;
        localVideoTrack = null;
        localAudioTrack = null;
        localVideoSender = null;
        remoteVideoTrack = null;
        initiator = null;
    }

    // ====================================================================================
    // SdpObserver
    // ====================================================================================

    private class SDPObserver implements SdpObserver {
        private boolean setLocalSdpSuccess;

        @Override
        public void onCreateSuccess(SessionDescription sdp) {
            RTCLog.d(String.format(Locale.getDefault(), "---> create sdp(%s) successful", sdp.type));
            executor.execute(() -> {
                if (sdp.type == SessionDescription.Type.OFFER) {
                    initiator = true;
                } else if (sdp.type == SessionDescription.Type.ANSWER) {
                    initiator = false;
                }

                // 设置本地sdp
                setLocalSdpSuccess = false;
                setLocalDescriptionInternal(sdp);
            });
        }

        @Override
        public void onSetSuccess() {
            executor.execute(() -> {
                if (peerConnection == null) {
                    return;
                }

                SessionDescription localSdp = peerConnection.getLocalDescription();
                SessionDescription remoteSdp = peerConnection.getRemoteDescription();

                String localSdpType = localSdp != null ? String.valueOf(localSdp.type) : "null";
                String remoteSdpType = remoteSdp != null ? String.valueOf(remoteSdp.type) : "null";
                RTCLog.d(String.format(Locale.getDefault(), "---> set sdp successful: localSdp = %s, remoteSdp = %s", localSdpType, remoteSdpType));

                // 创建localSdp成功的回调
                if (localSdp != null && !setLocalSdpSuccess) {
                    setLocalSdpSuccess = true;
                    events.onSetLocalSdpSuccess(localSdp);
                }

                // 当 localSdp 和 remoteSdp 都设置成功后，再添加ice，保证有序性
                if (localSdp != null && remoteSdp != null) {
                    drainCandidatesInternal();
                }
            });
        }

        @Override
        public void onCreateFailure(String error) {
            executor.execute(() -> events.onPeerConnectionError("create sdp failure: " + error));
        }

        @Override
        public void onSetFailure(String error) {
            executor.execute(() -> events.onPeerConnectionError("set sdp failure: " + error));
        }
    }

    // ====================================================================================
    // PeerConnection.Observer
    // ====================================================================================

    private class PCObserver implements PeerConnection.Observer {
        @Override
        public void onIceCandidate(IceCandidate iceCandidate) {
            RTCLog.d("PeerConnection.Observer # onIceCandidate: " + iceCandidate);
            executor.execute(() -> {
                if (initiator == null) {
                    events.onPeerConnectionError("unknown initiator");
                    return;
                }
                events.onIceCandidate(iceCandidate, initiator);
            });
        }

        @Override
        public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
            RTCLog.d("PeerConnection.Observer # onIceCandidatesRemoved: " + Arrays.toString(iceCandidates));
            executor.execute(() -> events.onIceCandidatesRemoved(iceCandidates));
        }

        @Override
        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            RTCLog.d("PeerConnection.Observer # onSignalingChange: " + signalingState);
        }

        @Override
        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            RTCLog.d("PeerConnection.Observer # onIceConnectionChange: " + iceConnectionState);
            executor.execute(() -> {
                events.onIceConnectionChange(iceConnectionState);
                if (iceConnectionState == PeerConnection.IceConnectionState.FAILED) {
                    events.onPeerConnectionError("ice connect failed");
                }
            });
        }

        @Override
        public void onConnectionChange(final PeerConnection.PeerConnectionState newState) {
            RTCLog.d("PeerConnection.Observer # onConnectionChange: " + newState);
            executor.execute(() -> {
                events.onConnectionChange(newState);
                if (newState == PeerConnection.PeerConnectionState.FAILED) {
                    events.onPeerConnectionError("DTLS connection failed");
                }
            });
        }

        @Override
        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            RTCLog.d("PeerConnection.Observer # onIceGatheringChange: " + iceGatheringState);
        }

        @Override
        public void onIceConnectionReceivingChange(boolean receiving) {
            RTCLog.d("PeerConnection.Observer # onIceConnectionReceivingChange: " + receiving);
        }

        @Override
        public void onSelectedCandidatePairChanged(CandidatePairChangeEvent event) {
            RTCLog.d("PeerConnection.Observer # onSelectedCandidatePairChanged: " + event);
        }

        @Override
        public void onAddStream(MediaStream mediaStream) {
            RTCLog.d("PeerConnection.Observer # onAddStream: " + mediaStream);
        }

        @Override
        public void onRemoveStream(MediaStream mediaStream) {
            RTCLog.d("PeerConnection.Observer # onRemoveStream: " + mediaStream);
        }

        @Override
        public void onDataChannel(DataChannel channel) {
            RTCLog.d("PeerConnection.Observer # onDataChannel: " + channel);

            if (!dataChannelEnabled) return;

            channel.registerObserver(new DataChannel.Observer() {
                public void onBufferedAmountChange(long previousAmount) {

                }

                @Override
                public void onStateChange() {

                }

                @Override
                public void onMessage(final DataChannel.Buffer buffer) {
                    if (buffer.binary) {
                        RTCLog.d("Received binary msg over " + channel);
                        return;
                    }
                    ByteBuffer data = buffer.data;
                    final byte[] bytes = new byte[data.capacity()];
                    data.get(bytes);
                    String strData = new String(bytes, Charset.forName("UTF-8"));
                    RTCLog.d("Got msg: " + strData + " over " + channel);
                }
            });
        }

        @Override
        public void onRenegotiationNeeded() {
            RTCLog.d("PeerConnection.Observer # onRenegotiationNeeded");
        }

        @Override
        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
            RTCLog.d(String.format(Locale.getDefault(), "PeerConnection.Observer # onAddTrack(%s, %s)", rtpReceiver, Arrays.toString(mediaStreams)));
        }
    }
}
