package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * a向b提供offer，需要提供 sdp
 */
public class WxCall06OfferSdpNtf extends WxCallItem {

    public Map<String, Object> sdp = null;

}
