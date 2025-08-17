package com.tiocloud.chat.feature.session.p2p.fragment.mvp;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/07
 *     desc   :
 * </pre>
 */
public class TioP2PMsgList {
    /**
     * 消息
     */
    public @NonNull
    WxFriendMsgResp msgResp;
    /**
     * 处理后的消息
     */
    public @NonNull
    List<TioMsg> tioMsgList;

    public TioP2PMsgList(@NonNull WxFriendMsgResp msgResp, @NonNull List<TioMsg> tioMsgList) {
        this.msgResp = msgResp;
        this.tioMsgList = tioMsgList;
    }
}
