package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * b向a回复Answer，需要提供 sdp
 */
public class WxCall08AnswerSdpNtf extends WxCallItem {

    public Map<String, Object> sdp;

}
