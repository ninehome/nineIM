package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;

import java.util.Date;

public class WxFriendAlreadyReadNtf extends BaseResp {
    /**
     * 聊天对方的userid
     */
    public Integer uid;
    /**
     * 阅读时间
     */
    public Date readtime;
}
