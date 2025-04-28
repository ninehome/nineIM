package com.watayouxiang.imclient.model.body.webrtc;

/**
 * a或b --> s   发起结束通话请求
 * 需要传hanguptype
 */
public class WxCall13EndReq extends WxBaseCall {
    /**
     * 通话id
     */
    public String id;

    public WxCall13EndReq(String id) {
        this.id = id;
    }
}
