package com.tiocloud.chat.feature.session.common.adapter.msg;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/14
 *     desc   :
 * </pre>
 */
public class TioP2PErrorMsg extends TioMsg {
    @NonNull
    private final WxFriendErrorNtf ntf;
    @NonNull
    private final String toUid;

    public TioP2PErrorMsg(@NonNull WxFriendErrorNtf ntf, @NonNull String toUid) {
        this.ntf = ntf;
        this.toUid = toUid;
    }

    @Override
    public String getId() {
        return ntf.mid;
    }

    @Override
    public TioMsgType getMsgType() {
        return TioMsgType.tip;
    }

    @Override
    public Long getTime() {
        return ntf.t;
    }

    @Override
    public String getContent() {
        return ntf.msg;
    }

    @Override
    public String getUid() {
        return String.valueOf(ntf.uid);
    }

    @Override
    public String getChatLinkId() {
        return ntf.chatlinkid;
    }

    @NonNull
    public WxFriendErrorNtf getWxFriendErrorNtf() {
        return ntf;
    }

    @NonNull
    public String getToUid() {
        return toUid;
    }
}
