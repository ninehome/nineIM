package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * a向b提供offer，需要提供 e.candidate
 */
public class WxCall10OfferIceNtf extends WxCallItem {

	public Map<String, Object> candidate = null;

}
