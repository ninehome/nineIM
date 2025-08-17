package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseReq;

public class WxGroupChatReq extends BaseReq {
    /**
     * 聊天内容
     * 举例：大家好
     */
    public String c;
    /**
     * 艾特哪些用户。此值可为null
     * 举例：434343, 9898989
     */
    public String at;
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
     * 文字消息，带艾特功能
     */
    public WxGroupChatReq(String c, Integer chatlinkid, String at) {
        this.c = c;
        this.at = at;
        this.chatlinkid = chatlinkid;
    }

    /**
     * 文字消息
     */
    public WxGroupChatReq(String content, Integer chatLinkId) {
        this(content, chatLinkId, null);
    }

    /**
     * 名片消息
     */
    public WxGroupChatReq(Integer chatlinkid, Integer cardid, Byte cardtype) {
        this.chatlinkid = chatlinkid;
        this.cardid = cardid;
        this.cardtype = cardtype;
    }
}
