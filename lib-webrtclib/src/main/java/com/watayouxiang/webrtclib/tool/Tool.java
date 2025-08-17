package com.watayouxiang.webrtclib.tool;

import com.watayouxiang.webrtclib.model.PeerConnectionParameters;

/**
 * author : TaoWang
 * date : 2020/5/28
 * desc :
 */
public class Tool {

    public static String getFieldTrials(PeerConnectionParameters peerConnectionParameters) {
        String fieldTrials = "";
        if (peerConnectionParameters.isVideoFlexfecEnabled()) {
            fieldTrials += PeerConnectionParameters.VIDEO_FLEXFEC_FIELDTRIAL;
        }
        fieldTrials += PeerConnectionParameters.VIDEO_VP8_INTEL_HW_ENCODER_FIELDTRIAL;
        if (peerConnectionParameters.isDisableWebRtcAGCAndHPF()) {
            fieldTrials += PeerConnectionParameters.DISABLE_WEBRTC_AGC_FIELDTRIAL;
        }
        return fieldTrials;
    }

}
