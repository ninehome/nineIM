package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * b向a回复Answer，需要提供 e.candidate
 */
public class WxCall11AnswerIceReq extends WxBaseCall {

    /**
     * 通话id
     */
    public String id;
    /**
     * 发送方的candidate,Map<string,object>
     */
    public Map<String, Object> candidate;

    public WxCall11AnswerIceReq(String id, Map<String, Object> candidate) {
        this.id = id;
        this.candidate = candidate;
    }

}
