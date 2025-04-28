package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * a向b提供offer，需要提供 e.candidate
 */
public class WxCall09OfferIceReq extends WxBaseCall {

    /**
     * 通话id
     */
    public String id;
    /**
     * 发送方的candidate,Map<string,object>
     */
    public Map<String, Object> candidate;

    public WxCall09OfferIceReq(String id, Map<String, Object> candidate) {
        this.id = id;
        this.candidate = candidate;
    }
}
