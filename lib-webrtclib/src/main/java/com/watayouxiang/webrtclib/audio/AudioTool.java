package com.watayouxiang.webrtclib.audio;

import android.content.Context;

import androidx.annotation.Nullable;

import com.watayouxiang.webrtclib.interf.PeerConnectionEvents;
import com.watayouxiang.webrtclib.model.PeerConnectionParameters;
import com.watayouxiang.webrtclib.util.RTCLog;

import org.webrtc.audio.AudioDeviceModule;
import org.webrtc.audio.JavaAudioDeviceModule;

/**
 * author : TaoWang
 * date : 2020/6/1
 * desc :
 */
public class AudioTool {
    public static AudioDeviceModule createJavaAudioDevice(PeerConnectionParameters peerConnectionParameters,
                                                          PeerConnectionEvents events,
                                                          Context appContext,
                                                          @Nullable RecordedAudioToFileController saveRecordedAudioToFile) {
        // Set audio record error callbacks.
        JavaAudioDeviceModule.AudioRecordErrorCallback audioRecordErrorCallback = new JavaAudioDeviceModule.AudioRecordErrorCallback() {
            @Override
            public void onWebRtcAudioRecordInitError(String errorMessage) {
                RTCLog.e("onWebRtcAudioRecordInitError: " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }

            @Override
            public void onWebRtcAudioRecordStartError(JavaAudioDeviceModule.AudioRecordStartErrorCode errorCode, String errorMessage) {
                RTCLog.e("onWebRtcAudioRecordStartError: " + errorCode + ". " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }

            @Override
            public void onWebRtcAudioRecordError(String errorMessage) {
                RTCLog.e("onWebRtcAudioRecordError: " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }
        };

        // Set audio track error callbacks.
        JavaAudioDeviceModule.AudioTrackErrorCallback audioTrackErrorCallback = new JavaAudioDeviceModule.AudioTrackErrorCallback() {
            @Override
            public void onWebRtcAudioTrackInitError(String errorMessage) {
                RTCLog.e("onWebRtcAudioTrackInitError: " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }

            @Override
            public void onWebRtcAudioTrackStartError(JavaAudioDeviceModule.AudioTrackStartErrorCode errorCode, String errorMessage) {
                RTCLog.e("onWebRtcAudioTrackStartError: " + errorCode + ". " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }

            @Override
            public void onWebRtcAudioTrackError(String errorMessage) {
                RTCLog.e("onWebRtcAudioTrackError: " + errorMessage);
                events.onPeerConnectionError(errorMessage);
            }
        };

        // Set audio record state callbacks.
        JavaAudioDeviceModule.AudioRecordStateCallback audioRecordStateCallback = new JavaAudioDeviceModule.AudioRecordStateCallback() {
            @Override
            public void onWebRtcAudioRecordStart() {
                RTCLog.i("Audio recording starts");
            }

            @Override
            public void onWebRtcAudioRecordStop() {
                RTCLog.i("Audio recording stops");
            }
        };

        // Set audio track state callbacks.
        JavaAudioDeviceModule.AudioTrackStateCallback audioTrackStateCallback = new JavaAudioDeviceModule.AudioTrackStateCallback() {
            @Override
            public void onWebRtcAudioTrackStart() {
                RTCLog.i("Audio playout starts");
            }

            @Override
            public void onWebRtcAudioTrackStop() {
                RTCLog.i("Audio playout stops");
            }
        };

        return JavaAudioDeviceModule.builder(appContext)
                .setSamplesReadyCallback(saveRecordedAudioToFile)
                .setUseHardwareAcousticEchoCanceler(!peerConnectionParameters.isDisableBuiltInAEC())
                .setUseHardwareNoiseSuppressor(!peerConnectionParameters.isDisableBuiltInNS())
                .setAudioRecordErrorCallback(audioRecordErrorCallback)
                .setAudioTrackErrorCallback(audioTrackErrorCallback)
                .setAudioRecordStateCallback(audioRecordStateCallback)
                .setAudioTrackStateCallback(audioTrackStateCallback)
                .createAudioDeviceModule();
    }
}
