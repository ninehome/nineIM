package com.watayouxiang.imclient.model.body.wx;


import com.watayouxiang.imclient.model.body.BaseReq;

/**
 * 朋友间的聊天请求-- Client-->Server
 */
public class WxFriendChatReq extends BaseReq {
    /**
     * 聊天内容
     */
    public String c;
    /**
     * 用户的聊天会话id-新增字段
     */
    public Integer chatlinkid;
    /**
     * 名片id:好友的用户id/群的id
     */
    public Integer cardid;
    /**
     * 名片类型：1：好友名片；2：群名片
     */
    public Byte cardtype;

    /**
     * 文字消息
     */
    public WxFriendChatReq(String c, Integer chatlinkid) {
        this.c = c;
        this.chatlinkid = chatlinkid;
    }

    /**
     * 名片消息
     */
    public WxFriendChatReq(Integer chatlinkid, Integer cardid, Byte cardtype) {
        this.chatlinkid = chatlinkid;
        this.cardid = cardid;
        this.cardtype = cardtype;
    }
}
