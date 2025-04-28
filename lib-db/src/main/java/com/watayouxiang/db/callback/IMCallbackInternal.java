package com.watayouxiang.db.callback;

import com.watayouxiang.db.sync.ChatListTableSync;
import com.watayouxiang.db.sync.FocusTableSync;
import com.watayouxiang.db.sync.WxFriendChatNtfSync;
import com.watayouxiang.db.sync.WxGroupChatNtfSync;
import com.watayouxiang.db.sync.WxGroupOperNtfSync;
import com.watayouxiang.db.sync.WxUserOperNtfSync;
import com.watayouxiang.db.sync.WxUserSysNtfSync;
import com.watayouxiang.imclient.client.IMCallback;
import com.watayouxiang.imclient.model.body.wx.WxFocusNtf;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxUserSysNtf;
import com.watayouxiang.imclient.packet.TioCommand;
import com.watayouxiang.imclient.packet.TioPacket;

import java.nio.ByteBuffer;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   : IM 回调
 * </pre>
 */
public class IMCallbackInternal implements IMCallback<TioPacket> {
    @Override
    public void onConnecting() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onSendBegin(TioPacket packet) {

    }

    @Override
    public void onSendCancel(TioPacket packet) {

    }

    @Override
    public void onSendEnd(TioPacket packet) {

    }

    @Override
    public void onReceiveBegin(ByteBuffer buffer) {

    }

    @Override
    public void onReceiveCancel() {

    }

    @Override
    public void onReceiveEnd(TioPacket packet) {
        short command = packet.getCommand();
        Object body = packet.getBody();
        if (command == TioCommand.WX_HANDSHAKE_RESP) {// 握手响应
            ChatListTableSync.getInstance().sync();
        } else if (command == TioCommand.WX_FOCUS_NTF) {// 焦点通知
            WxFocusNtf wxFocusNtf = (WxFocusNtf) body;
            FocusTableSync.getInstance().sync(wxFocusNtf);
        } else if (command == TioCommand.WX_FRIEND_CHAT_NTF) {// 私聊通知
            WxFriendChatNtf wxFriendChatNtf = (WxFriendChatNtf) body;
            WxFriendChatNtfSync.getInstance().sync(wxFriendChatNtf);
        } else if (command == TioCommand.WX_GROUP_CHAT_NTF) {// 群聊通知
            WxGroupChatNtf wxGroupChatNtf = (WxGroupChatNtf) body;
            WxGroupChatNtfSync.getInstance().sync(wxGroupChatNtf);
        } else if (command == TioCommand.WX_USER_OPER_NTF) {// 用户操作通知
            WxUserOperNtf wxUserOperNtf = (WxUserOperNtf) body;
            WxUserOperNtfSync.getInstance().sync(wxUserOperNtf);
        } else if (command == TioCommand.WX_GROUP_OPER_NTF) {// 群操作通知
            WxGroupOperNtf wxGroupOperNtf = (WxGroupOperNtf) body;
            WxGroupOperNtfSync.getInstance().sync(wxGroupOperNtf);
        } else if (command == TioCommand.WX_USER_SYS_NTF) {// 用户系统通知
            WxUserSysNtf wxUserSysNtf = (WxUserSysNtf) body;
            WxUserSysNtfSync.getInstance().sync(wxUserSysNtf);
        }
    }
}
