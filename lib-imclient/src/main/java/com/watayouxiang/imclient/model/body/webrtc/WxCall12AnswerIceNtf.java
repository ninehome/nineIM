package com.watayouxiang.imclient.model.body.webrtc;

import java.util.Map;

/**
 * b向a回复Answer，需要提供 e.candidate
 */
public class WxCall12AnswerIceNtf extends WxCallItem {

    public Map<String, Object> candidate = null;

}
