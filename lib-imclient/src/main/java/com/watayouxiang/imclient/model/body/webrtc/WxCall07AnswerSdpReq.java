package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * b向a回复Answer，需要提供 sdp
 */
public class WxCall07AnswerSdpReq extends WxBaseCall {

    /**
     * 通话id
     */
    public String id;
    /**
     * 接收方的sdp
     */
    public Map<String, Object> sdp;

    public WxCall07AnswerSdpReq(String id, Map<String, Object> sdp) {
        this.id = id;
        this.sdp = sdp;
    }
}
