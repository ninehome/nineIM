package com.tiocloud.chat.feature.session.group.fragment.mvp;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/07
 *     desc   :
 * </pre>
 */
public class TioGroupMsgList {
    /**
     * 消息
     */
    public @NonNull
    WxGroupMsgResp msgResp;
    /**
     * 处理后的消息
     */
    public @NonNull
    List<TioMsg> tioMsgList;

    public TioGroupMsgList(@NonNull WxGroupMsgResp msgResp, @NonNull List<TioMsg> tioMsgList) {
        this.msgResp = msgResp;
        this.tioMsgList = tioMsgList;
    }
}
