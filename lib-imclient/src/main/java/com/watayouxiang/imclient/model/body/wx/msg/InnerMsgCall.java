package com.watayouxiang.imclient.model.body.wx.msg;

import com.watayouxiang.imclient.model.HangUpType;

/**
 * author : TaoWang
 * date : 2020/5/26
 * desc :
 */
public class InnerMsgCall {
    /**
     * calltype : 10
     * duration : 0
     * hanguptype : 10
     * hangupuid : 23436
     */

    /**
     * 音视频通话类型：10:视频通话；11：音频通话
     */
    public int calltype;
    /**
     * 通话时长-milliseconds
     */
    public String duration;
    /**
     * @see HangUpType
     */
    public int hanguptype;
    /**
     * 挂断用户
     */
    public int hangupuid;

    @Override
    public String toString() {
        return "Call {calltype=" + calltype + "\n, " +
                "duration=" + duration + "\n, " +
                "hanguptype=" + hanguptype + "\n, " +
                "hangupuid=" + hangupuid + '}';
    }

    public HangUpType getHangupType() {
        return HangUpType.valueOf(hanguptype);
    }
}
