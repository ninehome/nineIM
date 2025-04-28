package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * 撤回消息请求体
 */
public class WxWithdrawMsgReq extends BaseReq {
    /**
     * mid
     */
    public Long mid;
    /**
     * 群组，如果这空则表示是私聊
     */
    public Long g;
}
