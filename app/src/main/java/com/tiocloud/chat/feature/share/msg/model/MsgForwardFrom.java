package com.tiocloud.chat.feature.share.msg.model;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/21
 *     desc   :
 * </pre>
 */
public class MsgForwardFrom {

    // 发送方 会话id
    public String chatLinkId;
    // 消息id（多条 "," 分割）
    public String mids;

    public MsgForwardFrom(String chatLinkId, String mids) {
        this.chatLinkId = chatLinkId;
        this.mids = mids;
    }
}
