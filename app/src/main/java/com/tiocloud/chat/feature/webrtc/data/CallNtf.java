package com.tiocloud.chat.feature.webrtc.data;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/04
 *     desc   :
 * </pre>
 */
public class CallNtf implements Serializable {
    /**
     * 通话发起人的userId
     */
    private int fromUid;
    /**
     * 通话类型：1、 音频通话，2、视频通话
     */
    private int type;

    public CallNtf(int fromUid, int type) {
        this.fromUid = fromUid;
        this.type = type;
    }

    public int getFromUid() {
        return fromUid;
    }

    public int getType() {
        return type;
    }
}
