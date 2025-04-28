package com.watayouxiang.webrtclib.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.webrtclib.R;
import com.watayouxiang.webrtclib.interf.PeerConnectionEvents;
import com.watayouxiang.webrtclib.model.PeerConnectionParameters;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.FileVideoCapturer;
import org.webrtc.MediaStreamTrack;
import org.webrtc.PeerConnection;
import org.webrtc.RtpSender;
import org.webrtc.RtpTransceiver;
import org.webrtc.ScreenCapturerAndroid;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoTrack;

import java.io.IOException;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/6/1
 * desc :
 */
public class VideoTool {

    // ====================================================================================
    // 获取 VideoCapturer
    // ====================================================================================

    public @Nullable
    static VideoCapturer createVideoCapturer(@NonNull PeerConnectionParameters peerConnectionParameters, @NonNull PeerConnectionEvents events, @NonNull Context appContext) {
        VideoCapturer videoCapturer = null;
        if (peerConnectionParameters.isVideoCallEnabled()) {
            if (peerConnectionParameters.getVideoFileAsCamera() != null) {
                try {
                    videoCapturer = new FileVideoCapturer(peerConnectionParameters.getVideoFileAsCamera());
                } catch (IOException e) {
                    events.onPeerConnectionError("Failed to open video file for emulated camera");
                    return null;
                }
            } else if (peerConnectionParameters.isScreenCaptureEnabled()) {
                return createScreenCapturer(events);
            } else if (useCamera2(appContext, peerConnectionParameters)) {
                if (!captureToTexture(peerConnectionParameters)) {
                    events.onPeerConnectionError(appContext.getString(R.string.camera2_texture_only_error));
                    return null;
                }

                RTCLog.d("Creating capturer using camera2 API.");
                videoCapturer = createCameraCapturer(new Camera2Enumerator(appContext));
            } else {
                RTCLog.d("Creating capturer using cam`era1 API.");
                videoCapturer = createCameraCapturer(new Camera1Enumerator(captureToTexture(peerConnectionParameters)));
            }
            if (videoCapturer == null) {
                events.onPeerConnectionError("Failed to open camera");
                return null;
            }
        }
        return videoCapturer;
    }

    private static boolean captureToTexture(@NonNull PeerConnectionParameters peerConnectionParameters) {
        return peerConnectionParameters.isCaptureToTexture();
    }

    private @Nullable
    static VideoCapturer createCameraCapturer(@NonNull CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();

        // First, try to find front facing camera
        RTCLog.d("Looking for front facing cameras.");
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                RTCLog.d("Creating front facing camera capturer.");
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);

                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        // Front facing camera not found, try something else
        RTCLog.d("Looking for other cameras.");
        for (String deviceName : deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                RTCLog.d("Creating other camera capturer.");
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);

                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        return null;
    }

    private static boolean useCamera2(@NonNull Context appContext, @NonNull PeerConnectionParameters peerConnectionParameters) {
        return Camera2Enumerator.isSupported(appContext) && peerConnectionParameters.isUseCamera2();
    }

    // ====================================================================================
    // 屏幕录制
    // ====================================================================================

    private static final int CAPTURE_PERMISSION_REQUEST_CODE = 3301;
    private static Intent mediaProjectionPermissionResultData;
    private static int mediaProjectionPermissionResultCode;

    // 第二步：权限获取成功才能调用
    @TargetApi(21)
    private static @Nullable
    VideoCapturer createScreenCapturer(@NonNull PeerConnectionEvents events) {
        if (mediaProjectionPermissionResultCode != Activity.RESULT_OK) {
            events.onPeerConnectionError("User didn't give permission to capture the screen.");
            return null;
        }
        return new ScreenCapturerAndroid(mediaProjectionPermissionResultData, new MediaProjection.Callback() {
            @Override
            public void onStop() {
                events.onPeerConnectionError("User revoked permission to capture the screen.");
            }
        });
    }

    // 第一步：获取权限
    @TargetApi(21)
    public static void startScreenCapture(@NonNull Activity activity) {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager != null) {
            activity.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), CAPTURE_PERMISSION_REQUEST_CODE);
        }
    }

    // 第一步：权限获取结果回调
    public static void onActivityResult(int requestCode, int resultCode, Intent data, @Nullable OnScreenCaptureCallback callback) {
        if (requestCode != CAPTURE_PERMISSION_REQUEST_CODE) {
            if (callback != null) {
                callback.onReqScreenCapture(false);
            }
            return;
        }
        mediaProjectionPermissionResultCode = resultCode;
        mediaProjectionPermissionResultData = data;
        if (callback != null) {
            callback.onReqScreenCapture(true);
        }
    }

    public interface OnScreenCaptureCallback {
        void onReqScreenCapture(boolean ok);
    }

    // ====================================================================================
    // 获取 RemoteVideoTrack
    // ====================================================================================

    public static @Nullable
    VideoTrack getRemoteVideoTrack(@NonNull PeerConnection peerConnection) {
        for (RtpTransceiver transceiver : peerConnection.getTransceivers()) {
            MediaStreamTrack track = transceiver.getReceiver().track();
            if (track instanceof VideoTrack) {
                return (VideoTrack) track;
            }
        }
        return null;
    }

    // ====================================================================================
    // findVideoSender
    // ====================================================================================

    public static @Nullable
    RtpSender findVideoSender(@NonNull PeerConnection peerConnection) {
        RtpSender localVideoSender = null;
        List<RtpSender> senders = peerConnection.getSenders();
        for (RtpSender sender : senders) {
            MediaStreamTrack track = sender.track();
            if (track != null) {
                String trackType = track.kind();
                if (trackType.equals(PeerConnectionParameters.VIDEO_TRACK_TYPE)) {
                    localVideoSender = sender;
                }
            }
        }
        return localVideoSender;
    }
    
}
