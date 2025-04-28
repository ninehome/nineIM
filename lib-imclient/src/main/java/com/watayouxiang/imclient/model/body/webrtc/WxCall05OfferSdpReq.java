package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * a向b提供offer，需要提供 sdp
 */
public class WxCall05OfferSdpReq extends WxBaseCall {

    /**
     * 通话id
     */
    public String id;
    /**
     * 发起方的sdp
     */
    public Map<String, Object> sdp;

    public WxCall05OfferSdpReq(String id, Map<String, Object> sdp) {
        this.id = id;
        this.sdp = sdp;
    }
}
