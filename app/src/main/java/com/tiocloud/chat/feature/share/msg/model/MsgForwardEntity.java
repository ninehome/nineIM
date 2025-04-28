package com.tiocloud.chat.feature.share.msg.model;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/21
 *     desc   :
 * </pre>
 */
public class MsgForwardEntity {

    // 接收方
    public MsgForwardTo to;
    // 发送方 名片id
    public MsgForwardFrom from;

    public MsgForwardEntity(MsgForwardTo to, MsgForwardFrom from) {
        this.to = to;
        this.from = from;
    }
}
