package com.watayouxiang.db.temp;

import com.watayouxiang.imclient.model.HangUpType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;

import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 * </pre>
 * {@link InnerMsgCall}
 */
public class InMsgCallTable {
    @Id
    private long id;
    /**
     * 音视频通话类型：10:视频通话；11：音频通话
     */
    private int calltype;
    /**
     * 通话时长-milliseconds
     */
    private String duration;
    /**
     * @see HangUpType
     */
    private int hanguptype;
    /**
     * 挂断用户
     */
    private int hangupuid;
}
