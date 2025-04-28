package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * 获取两好友间聊天记录--请求-- Client-->Server
 */
public class WxQueryFriendMsgReq extends BaseReq {
    /**
     * 对方的uid
     */
    public Integer uid;
}
