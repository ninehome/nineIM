package com.watayouxiang.webrtclib;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;

import androidx.annotation.Nullable;

import com.watayouxiang.imclient.model.body.webrtc.WxCall02Ntf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall02_2CancelNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall03ReplyReqNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall04ReplyNtf;
import com.watayouxiang.imclient.model.body.webrtc.WxCall14EndNtf;
import com.watayouxiang.webrtclib.client.PeerConnectionClient;
import com.watayouxiang.webrtclib.client.WebSocketRTCClient;
import com.watayouxiang.webrtclib.interf.PeerConnectionEvents;
import com.watayouxiang.webrtclib.interf.TaoWebRTCOp;
import com.watayouxiang.webrtclib.interf.WebSocketRTCEvents;
import com.watayouxiang.webrtclib.listener.OnRTCListener;
import com.watayouxiang.webrtclib.listener.OnRTCListenerIterator;
import com.watayouxiang.webrtclib.model.AudioDevice;
import com.watayouxiang.webrtclib.model.PeerConnectionParameters;
import com.watayouxiang.webrtclib.model.RTCViewHolder;
import com.watayouxiang.webrtclib.tool.PermissionTool;
import com.watayouxiang.webrtclib.tool.UiHandler;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.CameraVideoCapturer;
import org.webrtc.EglBase;
import org.webrtc.EglRenderer;
import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.RendererCommon;
import org.webrtc.SessionDescription;
import org.webrtc.VideoSink;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/4/29
 * desc : WebRTC 启动器
 */
public class TioWebRTC implements TaoWebRTCOp, WebSocketRTCEvents, PeerConnectionEvents {

    private final UiHandler uiHandler;
    private final WebSocketRTCClient socketRTCClient;
    private final List<OnRTCListener> onRTCListeners;
    private OnRTCListener onGlobalRTCListener;

    ////////////////////////////////////////////////////
    // release res
    ////////////////////////////////////////////////////

    private RTCViewHolder viewHolder;
    private EglBase rootEglBase;
    private PeerConnectionClient peerConnectionClient;

    private RendererCommon.ScalingType localVideoScalingType;
    private RendererCommon.ScalingType remoteVideoScalingType;
    private int onCallType;// 来电的类型（1 音频电话，2 视频电话）
    private AudioManager audioManager;
    private AudioDevice audioDevice;

    private TioWebRTC() {
        uiHandler = new UiHandler();
        socketRTCClient = new WebSocketRTCClient(this);
        onRTCListeners = new ArrayList<>();

        TioWebRTC.setDebug(BuildConfig.DEBUG);
    }

    private static class InstanceHolder {
        private static final TioWebRTC holder = new TioWebRTC();
    }

    public static TioWebRTC getInstance() {
        return InstanceHolder.holder;
    }

    public static void setDebug(boolean isDebug) {
        RTCLog.setDebug(isDebug);
    }

    // ====================================================================================
    // AppRTCOp
    // ====================================================================================

    @Override
    public void init(RTCViewHolder holder) {
        runOnUiThread(() -> {
            release();
            viewHolder = holder;
            Context context = viewHolder.getLocalVideoView().getContext().getApplicationContext();

            if (!PermissionTool.agreeMandatoryPermissions(context)) {
                onPeerConnectionError("disagree mandatory permissions");
                return;
            }

            rootEglBase = EglBase.create();
            initRTCViews(rootEglBase, viewHolder);
            PeerConnectionParameters peerConnectionParameters = new PeerConnectionParameters();
            peerConnectionClient = new PeerConnectionClient(context, rootEglBase, peerConnectionParameters, TioWebRTC.this);
            peerConnectionClient.createPeerConnectionFactory();

            // 来电：马上初始化 PC 连接
            if (onCallType != 0) {
                createPeerConnection(onCallType == 2);
                switchVideoSink(true);
            }

            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            setAudioDevice(AudioDevice.RECEIVER);
        });
    }

    private void initRTCViews(EglBase rootEglBase, RTCViewHolder holder) {
        // init localVideoView
        holder.getLocalVideoView().init(rootEglBase.getEglBaseContext(), null);
        holder.getLocalVideoView().setEnableHardwareScaler(true);
        holder.getLocalVideoView().setMirror(true);
        holder.getLocalVideoView().setScalingType(localVideoScalingType = RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        // init remoteVideoView
        holder.getRemoteVideoView().init(rootEglBase.getEglBaseContext(), null);
        holder.getRemoteVideoView().setEnableHardwareScaler(true);
        holder.getRemoteVideoView().setMirror(false);
        holder.getRemoteVideoView().setScalingType(remoteVideoScalingType = RendererCommon.ScalingType.SCALE_ASPECT_FILL);
//        holder.getRemoteVideoView().addFrameListener(new EglRenderer.FrameListener() {
//            @Override
//            public void onFrame(Bitmap bitmap) {
//
//            }
//        },1f);
    }

    @Override
    public void call(int toUid, byte type) {
        runOnUiThread(() -> {
            // 去电：先初始化 PC 工厂，再初始化 PC 连接。
            createPeerConnection(type == 2);
            switchVideoSink(true);

            if (socketRTCClient != null) {
                socketRTCClient.callReq(toUid, type);
            }
        });
    }

    @Override
    public void callCancel() {
        runOnUiThread(() -> {
            if (socketRTCClient != null) {
                socketRTCClient.callCancelReq();
            }
            releasePeerConnection();
        });
    }

    @Override
    public void callReply(byte result, String reason) {
        runOnUiThread(() -> {
            if (result == 1) {// 同意通话
                switchVideoSink(false);
            } else if (result == 2) {// 拒接
                releasePeerConnection();
            }
            if (socketRTCClient != null) {
                socketRTCClient.callReplyReq(result, reason);
            }
        });
    }

    @Override
    public void hangUp() {
        runOnUiThread(() -> {
            if (socketRTCClient != null) {
                socketRTCClient.callEndReq();
            }
            releasePeerConnection();
        });
    }

    @Override
    public void switchVideoSink(boolean reverseVideoSink) {
        if (peerConnectionClient != null) {
            peerConnectionClient.switchVideoSink(reverseVideoSink);
        }
    }

    @Override
    public boolean isSwitchVideoSink() {
        if (peerConnectionClient != null) {
            return peerConnectionClient.isSwitchVideoSink();
        }
        return false;
    }

    @Override
    public void switchCamera(@Nullable CameraVideoCapturer.CameraSwitchHandler handler) {
        runOnUiThread(() -> {
            if (peerConnectionClient != null) {
                peerConnectionClient.switchCamera(new CameraVideoCapturer.CameraSwitchHandler() {
                    @Override
                    public void onCameraSwitchDone(boolean isFontCamera) {
                        if (handler != null) {
                            handler.onCameraSwitchDone(isFontCamera);
                        }
                        // 切换到前置摄像头：设置镜像
                        // 切换到后置摄像头：取消镜像
                        if (isFontCamera) {
                            viewHolder.getLocalVideoView().setMirror(true);
                        } else {
                            viewHolder.getLocalVideoView().setMirror(false);
                        }
                    }

                    @Override
                    public void onCameraSwitchError(String s) {
                        if (handler != null) {
                            handler.onCameraSwitchError(s);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setLocalVideoScaling(RendererCommon.ScalingType scalingType) {
        runOnUiThread(() -> {
            if (viewHolder != null && viewHolder.getLocalVideoView() != null) {
                viewHolder.getLocalVideoView().setScalingType(localVideoScalingType = scalingType);
            }
        });
    }

    @Override
    public void switchLocalVideoScaling() {
        runOnUiThread(() -> {
            if (localVideoScalingType == RendererCommon.ScalingType.SCALE_ASPECT_FILL) {
                setLocalVideoScaling(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            } else {
                setLocalVideoScaling(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
            }
        });
    }

    @Override
    public void setRemoteVideoScaling(RendererCommon.ScalingType scalingType) {
        runOnUiThread(() -> {
            if (viewHolder != null && viewHolder.getRemoteVideoView() != null) {
                viewHolder.getRemoteVideoView().setScalingType(remoteVideoScalingType = scalingType);
            }
        });
    }

    @Override
    public void switchRemoteVideoScaling() {
        runOnUiThread(() -> {
            if (remoteVideoScalingType == RendererCommon.ScalingType.SCALE_ASPECT_FILL) {
                setRemoteVideoScaling(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            } else {
                setRemoteVideoScaling(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
            }
        });
    }

    @Override
    public void setLocalAudioEnable(boolean enable) {
        runOnUiThread(() -> {
            if (peerConnectionClient != null) {
                peerConnectionClient.setLocalAudioEnable(enable);
            }
        });
    }

    @Override
    public boolean isLocalAudioEnable() {
        if (peerConnectionClient != null) {
            return peerConnectionClient.isLocalAudioEnable();
        }
        return false;
    }

    @Override
    public void toggleLocalAudio() {
        if (isLocalAudioEnable()) {
            setLocalAudioEnable(false);
        } else {
            setLocalAudioEnable(true);
        }
    }

    @Override
    public void setVideoEnable(boolean enable) {
        runOnUiThread(() -> {
            if (peerConnectionClient != null) {
                peerConnectionClient.setVideoEnable(enable);
            }
        });
    }

    @Override
    public boolean isVideoEnable() {
        if (peerConnectionClient != null) {
            return peerConnectionClient.isVideoEnable();
        }
        return false;
    }

    @Override
    public void toggleVideoEnable() {
        if (isVideoEnable()) {
            setVideoEnable(false);
        } else {
            setVideoEnable(true);
        }
    }

    @Override
    public void setAudioDevice(AudioDevice device) {
        runOnUiThread(() -> {
            if (device == AudioDevice.SPEAKER) {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                audioDevice = AudioDevice.SPEAKER;
            } else if (device == AudioDevice.RECEIVER) {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(false);
                audioDevice = AudioDevice.RECEIVER;
            } else if (device == AudioDevice.HEADSET) {
                audioManager.setSpeakerphoneOn(false);
                audioDevice = AudioDevice.HEADSET;
            }
        });
    }

    @Override
    public AudioDevice getAudioDevice() {
        return audioDevice;
    }

    @Override
    public void toggleAudioDevice() {
        if (audioDevice == AudioDevice.SPEAKER) {
            setAudioDevice(AudioDevice.RECEIVER);
        } else if (audioDevice == AudioDevice.RECEIVER) {
            setAudioDevice(AudioDevice.SPEAKER);
        }
    }

    @Override
    public void setOnGlobalRTCListener(OnRTCListener listener) {
        runOnUiThread(() -> onGlobalRTCListener = listener);
    }

    @Override
    public void registerOnRTCListener(OnRTCListener listener) {
        runOnUiThread(() -> {
            if (listener != null && !onRTCListeners.contains(listener)) {
                onRTCListeners.add(listener);
            }
        });
    }

    @Override
    public void unregisterOnRTCListener(OnRTCListener listener) {
        runOnUiThread(() -> {
            if (listener != null) {
                onRTCListeners.remove(listener);
            }
        });
    }

    @Override
    public void release() {
        runOnUiThread(() -> {
            if (peerConnectionClient != null) {
                peerConnectionClient.release();
                peerConnectionClient = null;
            }
            if (viewHolder != null) {
                viewHolder.getLocalVideoView().release();
                viewHolder.getRemoteVideoView().release();
                viewHolder = null;
            }
            if (rootEglBase != null) {
                rootEglBase.release();
                rootEglBase = null;
            }
            if (audioManager != null) {
                audioManager.setMode(AudioManager.MODE_NORMAL);
                audioManager = null;
            }
            localVideoScalingType = null;
            remoteVideoScalingType = null;
            onRTCListeners.clear();
            uiHandler.removeCallbacksAndMessages(null);
        });
    }

    // ====================================================================================
    // WebSocketRTCEvents
    // ====================================================================================

    void runOnUiThread(Runnable runnable) {
        uiHandler.runOnUiThread(runnable);
    }

    private void iteratorOnRTCListeners(OnRTCListenerIterator iterator) {
        if (onGlobalRTCListener != null) {
            iterator.onIterator(onGlobalRTCListener);
        }
        int size = onRTCListeners.size();
        for (int i = 0; i < size; i++) {
            iterator.onIterator(onRTCListeners.get(i));
        }
    }

    private void createPeerConnection(boolean renderVideo) {
        if (peerConnectionClient != null && viewHolder != null) {
            List<VideoSink> remoteRenderers = new ArrayList<>(1);
            remoteRenderers.add(viewHolder.getRemoteVideoView());
            peerConnectionClient.createPeerConnection(viewHolder.getLocalVideoView(), remoteRenderers, null, renderVideo);
        }
    }

    private void releasePeerConnection() {
        onCallType = 0;
        if (peerConnectionClient != null) {
            peerConnectionClient.releasePeerConnection();
        }
        iteratorOnRTCListeners(OnRTCListener::onRTCClosed);
    }

    @Override
    public void onCall(WxCall02Ntf call) {
        runOnUiThread(() -> {
            onCallType = call.type;
            iteratorOnRTCListeners(listener -> listener.onCall(call));
        });
    }

    @Override
    public void onCallReply(WxCall04ReplyNtf callReply) {
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onCallReply(callReply));
            if (callReply.result == 1) {// 同意通话
                if (peerConnectionClient != null) {
                    peerConnectionClient.switchVideoSink(false);
                }
                if (peerConnectionClient != null) {
                    peerConnectionClient.createOffer();
                }
            } else if (callReply.result == 2) {// 拒接
                releasePeerConnection();
            }
        });
    }

    @Override
    public void onCallCancel(WxCall02_2CancelNtf callCancel) {
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onCallCancel(callCancel));
            releasePeerConnection();
        });
    }

    @Override
    public void onCallEnd(WxCall14EndNtf callEnd) {
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onCallEnd(callEnd));
            releasePeerConnection();
        });
    }

    // 有 b 端回复了通话请求，所有 b 端都会收到该通知
    @Override
    public void onCallReplyReqNtf(WxCall03ReplyReqNtf ntf) {
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onCallResp(ntf));

            ////////////////////////////////////////////////////
            // 多端通话处理
            ////////////////////////////////////////////////////

            if (ntf.result == 1) {
                // 同意通话
                if (ntf.todevice != 2) {
                    // 接收端不是安卓
                    if (ntf.fromuid == ntf.touid && ntf.fromdevice == 2) {
                        // 自己账号 && 安卓端拨打 && 其他端同意通话 --> 安卓端不挂断
                        return;
                    }
                    releasePeerConnection();
                } else {
                    // ui需要做的处理：接听按钮隐藏、提示修改为 "接通中..."
                }
            } else if (ntf.result == 2) {
                // 拒接
                releasePeerConnection();
            }

        });
    }

    @Override
    public void onRemoteSdp(SessionDescription sdp, boolean initiator) {
        runOnUiThread(() -> {
            if (initiator) {
                // 来自远程的 answer sdp
                if (peerConnectionClient != null) {
                    peerConnectionClient.setRemoteDescription(sdp);
                }
            } else {
                // 来自远程的 offer sdp
                if (peerConnectionClient != null) {
                    peerConnectionClient.setRemoteDescription(sdp);
                }
                if (peerConnectionClient != null) {
                    peerConnectionClient.createAnswer();
                }
            }
        });
    }

    @Override
    public void onRemoteIceCandidate(IceCandidate candidate, boolean initiator) {
        runOnUiThread(() -> {
            if (peerConnectionClient != null) {
                peerConnectionClient.addRemoteIceCandidate(candidate);
            }
        });
    }

    @Override
    public void onWebSocketError(Exception e) {
        RTCLog.e(e.getMessage());
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onWebSocketError(e));
            releasePeerConnection();
        });
    }

    @Override
    public void onWebSocketClosed() {
        RTCLog.e("onWebSocketClosed");
        runOnUiThread(() -> {
            iteratorOnRTCListeners(OnRTCListener::onWebSocketClosed);
            releasePeerConnection();
        });
    }

    // ====================================================================================
    // PeerConnectionEvents
    // ====================================================================================

    @Override
    public void onPeerConnectionError(String error) {
        RTCLog.e(error);
        runOnUiThread(() -> {
            iteratorOnRTCListeners(listener -> listener.onPeerConnectionError(error));
            // 告诉服务端挂断
            TioWebRTC.getInstance().hangUp();
            // 释放 ICE 连接
            releasePeerConnection();
        });
    }

    @Override
    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        runOnUiThread(() -> {
            if (iceConnectionState == PeerConnection.IceConnectionState.CONNECTED) {
                iteratorOnRTCListeners(OnRTCListener::onRTCConnected);
            } else if (iceConnectionState == PeerConnection.IceConnectionState.CLOSED) {
                iteratorOnRTCListeners(OnRTCListener::onRTCClosed);
            }
        });
    }

    @Override
    public void onConnectionChange(PeerConnection.PeerConnectionState newState) {

    }

    @Override
    public void onSetLocalSdpSuccess(SessionDescription sdp) {
        runOnUiThread(() -> {
            if (sdp.type == SessionDescription.Type.OFFER) {
                // 通话发起者：将本地OfferSdp发送给远程
                if (socketRTCClient != null) {
                    socketRTCClient.offerSdpReq(sdp);
                }
            } else if (sdp.type == SessionDescription.Type.ANSWER) {
                // 通话接受者：将本地AnswerSdp发送给远程
                if (socketRTCClient != null) {
                    socketRTCClient.answerSdpReq(sdp);
                }
            }
        });
    }

    @Override
    public void onIceCandidate(IceCandidate iceCandidate, boolean initiator) {
        runOnUiThread(() -> {
            if (initiator) {
                // 通话发起者：将本地OfferIce发送给远程
                if (socketRTCClient != null) {
                    socketRTCClient.offerIceReq(iceCandidate);
                }
            } else {
                // 通话接受者：将本地AnswerIce发送给远程
                if (socketRTCClient != null) {
                    socketRTCClient.answerIceReq(iceCandidate);
                }
            }
        });
    }

    @Override
    public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {

    }
}
