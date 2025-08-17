package com.tiocloud.chat.feature.session.common.action.model;

import android.content.Intent;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.card.GroupCardActivity;
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
 * desc : 群名片
 */
public class GroupCardAction extends BaseCardAction {
    public GroupCardAction() {
        super(R.drawable.icon_im_mingpian, R.string.group_card);
    }

    @Override
    public void onClick() {
        GroupCardActivity.start(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GroupCardActivity.REQ_CODE_GROUP_ID && resultCode == GroupCardActivity.RESP_CODE_GROUP_ID) {
            // 容错处理
            if (data == null) return;
            String groupId = data.getStringExtra(GroupCardActivity.EXTRA_GROUP_ID);
            if (groupId == null) return;

            for (String chatLinkId : chatLinkIds){
                // 发送群名片消息
                TioPacket packet = null;
                if (sessionType == SessionType.GROUP) {
                    packet = TioPacketBuilder.getWxGroupChatReq(new WxGroupChatReq(
                            Integer.parseInt(chatLinkId),
                            Integer.parseInt(groupId),
                            (byte) 2)
                    );
                } else if (sessionType == SessionType.P2P) {
                    packet = TioPacketBuilder.getWxFriendChatReq(new WxFriendChatReq(
                            Integer.parseInt(chatLinkId),
                            Integer.parseInt(groupId),
                            (byte) 2)
                    );
                }
                if (packet != null) {
                    TioIMClient.getInstance().sendPacket(packet);
                }
            }

        }
    }
}
