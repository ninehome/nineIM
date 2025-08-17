package com.tiocloud.chat.feature.webrtc.data;

import com.watayouxiang.webrtclib.model.AudioDevice;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public class CallConst {

    /**
     * 来电
     */
    public static final String EXTRA_CALL_NTF = "call_ntf";
    /**
     * 去电
     */
    public static final String EXTRA_CALL_REQ = "call_req";

    public static final AudioDevice VIDEO___AUDIO_DEVICE = AudioDevice.SPEAKER;
    public static final AudioDevice AUDIO___AUDIO_DEVICE = AudioDevice.RECEIVER;
}
