package com.tiocloud.chat.feature.session.common.action.model;

import android.content.Intent;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.selectfriend.SelectFriendActivity;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseCardAction;
import com.tiocloud.chat.feature.session.common.model.SessionType;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatReq;
import com.watayouxiang.imclient.packet.TioPacketBuilder;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc : 好友名片
 */
public class UserCardAction extends BaseCardAction {
    public UserCardAction() {
        super(R.drawable.icon_im_qunmingpian, R.string.card);
    }

    @Override
    public void onClick() {
        SelectFriendActivity.start(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectFriendActivity.REQ_CODE_UID && resultCode == SelectFriendActivity.RESP_CODE_UID) {
            // 容错处理
            if (data == null) return;
            int uid = data.getIntExtra(SelectFriendActivity.EXTRA_UID, 0);
            if (uid == 0) return;
            for (String chatLinkId : chatLinkIds){
                // 发好友名片消息
                TioPacket packet = null;
                if (sessionType == SessionType.GROUP || (sessionType == SessionType.GROUPSEND && Integer.parseInt(chatLinkId) <= 0)) {
                    WxGroupChatReq body = new WxGroupChatReq(Integer.parseInt(chatLinkId), uid, (byte) 1);
                    packet = TioPacketBuilder.getWxGroupChatReq(body);
                } else if (sessionType == SessionType.P2P || (sessionType == SessionType.GROUPSEND && Integer.parseInt(chatLinkId) > 0)) {
                    WxFriendChatReq wxP2PChatReq = new WxFriendChatReq(Integer.parseInt(chatLinkId), uid, (byte) 1);
                    packet = TioPacketBuilder.getWxFriendChatReq(wxP2PChatReq);
                }
                if (packet != null) {
                    TioIMClient.getInstance().sendPacket(packet);
                }
            }

        }
    }
}
