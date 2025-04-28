package com.tiocloud.chat.feature.webrtc.data;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public class CallReq implements Serializable {
    /**
     * 对方的userId
     */
    private int toUid;
    /**
     * 通话类型：1、 音频通话，2、视频通话
     */
    private int type;

    public CallReq(int toUid, int type) {
        this.toUid = toUid;
        this.type = type;
    }

    public int getToUid() {
        return toUid;
    }

    public int getType() {
        return type;
    }
}
