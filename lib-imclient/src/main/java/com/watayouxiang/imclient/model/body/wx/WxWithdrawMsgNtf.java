package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

public class WxWithdrawMsgNtf extends BaseReq {
    /**
     * mid
     */
    public Long mid;
    /**
     * 群组
     */
    public Long g = null;
    /**
     * 执行撤消操作的用户
     */
    public Integer operUid;
    /**
     * 被撤消消息的用户
     */
    public Integer uid;
}
