package com.watayouxiang.imclient.model.body.webrtc;

/**
 * a --> s   a向b发起通话请求
 */
public class WxCall01Req extends WxBaseCall {
    /**
     * 通话对方的userid
     */
    public int touid;
    /**
     * 通话类型：1、 音频通话，2、视频通话
     */
    public byte type;

    public WxCall01Req(int touid, byte type) {
        this.touid = touid;
        this.type = type;
    }
}
