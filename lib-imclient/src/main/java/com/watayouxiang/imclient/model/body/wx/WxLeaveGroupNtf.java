package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;

/**
 * 离群通知。当某用户被T出群，或群被删除时，用户会收到这个通知
 */
public class WxLeaveGroupNtf extends BaseResp {
    /**
     * 离开了哪个群
     */
    public Long groupid;
    /**
     * 1：主动退群；2：被T出群；3：群被删除
     */
    public Byte type;
}
