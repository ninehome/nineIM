package com.tiocloud.chat.feature.share.msg.model;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/21
 *     desc   :
 * </pre>
 */
public class MsgForwardTo {

    // 接收方 头像
    public String toAvatar;
    // 接收方 名称
    public String toName;
    // 接收方 用户id
    public String toUid;
    // 接收方 群组id
    public String toGroupId;

    public String bizid;

    public String chatlinkid;

    public MsgForwardTo(String toAvatar, String toName, String toUid, String toGroupId) {
        this.toAvatar = toAvatar;
        this.toName = toName;
        this.toUid = toUid;
        this.toGroupId = toGroupId;
    }

    public MsgForwardTo(String toAvatar, String toName, String toUid, String toGroupId,String bizid,String chatlinkid) {
        this.toAvatar = toAvatar;
        this.toName = toName;
        this.toUid = toUid;
        this.toGroupId = toGroupId;
        this.bizid = bizid;
        this.chatlinkid = chatlinkid;
    }

}
