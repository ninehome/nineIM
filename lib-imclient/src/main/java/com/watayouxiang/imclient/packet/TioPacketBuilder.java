package com.watayouxiang.imclient.packet;

import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxSessionOperReq;

public class TioPacketBuilder {

    // 群聊历史记录
    public static TioPacket getWxGroupMsgReq(WxGroupMsgReq wxGroupMsgReq) {
        return getPacket(wxGroupMsgReq, TioCommand.WX_GROUP_MSG_REQ);
    }

    // 私聊历史记录
    public static TioPacket getWxFriendMsgReq(WxFriendMsgReq wxFriendMsgReq) {
        return getPacket(wxFriendMsgReq, TioCommand.WX_FRIEND_MSG_REQ);
    }

    // 会话操作
    public static TioPacket getWxSessionOperReq(WxSessionOperReq wxSessionOperReq) {
        return getPacket(wxSessionOperReq, TioCommand.WX_SESSION_OPER_REQ);
    }

    // 会话的详情
    public static TioPacket getWxChatItemInfoReq(WxChatItemInfoReq wxChatItemInfoReq) {
        return getPacket(wxChatItemInfoReq, TioCommand.WX_CHAT_ITEM_INFO_REQ);
    }

    // 私聊
    public static TioPacket getWxFriendChatReq(WxFriendChatReq wxFriendChatReq) {
        return getPacket(wxFriendChatReq, TioCommand.WX_FRIEND_CHAT_REQ);
    }

    // 群聊
    public static TioPacket getWxGroupChatReq(WxGroupChatReq wxGroupChatReq) {
        return getPacket(wxGroupChatReq, TioCommand.WX_GROUP_CHAT_REQ);
    }

    // 心跳
    public static TioPacket getHeartbeatReq() {
        return getPacket(null, TioCommand.HEARTBEAT_REQ);
    }

    public static TioPacket getPacket(Object body, short command) {
        TioPacket packet = new TioPacket();
        packet.setCommand(command);
        packet.setBody(body);
        return packet;
    }
}
