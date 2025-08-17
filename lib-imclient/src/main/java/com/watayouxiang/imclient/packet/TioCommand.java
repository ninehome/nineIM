package com.watayouxiang.imclient.packet;

import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoReq;
import com.watayouxiang.imclient.model.body.wx.WxChatItemInfoResp;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxFriendMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgReq;
import com.watayouxiang.imclient.model.body.wx.WxGroupMsgResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxSessionOperReq;
import com.watayouxiang.imclient.model.body.wx.WxUpdateTokenReq;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;
import com.watayouxiang.imclient.model.body.wx.WxUserSysNtf;

public interface TioCommand {

    // =====================================================================================
    // t-io
    // =====================================================================================

    /**
     * 心跳请求
     */
    short HEARTBEAT_REQ = 1;
    /**
     * 握手请求
     */
    short HANDSHAKE_REQ = 2;
    /**
     * 握手响应
     */
    short HANDSHAKE_RESP = 3;
    /**
     * 进入群组请求
     */
    short JOIN_GROUP_REQ = 4;
    /**
     * 进入群组响应
     */
    short JOIN_GROUP_RESP = 5;
    /**
     * 进入群组通知
     */
    short JOIN_GROUP_NTY = 6;
    /**
     * 离开群组通知
     */
    short LEAVE_GROUP_NTY = 7;
    /**
     * 点对点聊天（私聊）请求
     */
    short P2P_CHAT_REQ = 8;
    /**
     * 点对点聊天（私聊）通知
     */
    short P2P_CHAT_NTY = 9;
    /**
     * 群聊请求
     */
    short GROUP_CHAT_REQ = 10;
    /**
     * 群聊通知
     */
    short GROUP_CHAT_NTY = 11;
    /**
     * 获取p2p聊天记录数据-请求
     */
    short P2P_QUERY_CHAT_RECORD_REQ = 12;
    /**
     * 执行一段JS脚本
     */
    short RUN_JS_NTY = 14;
    /**
     * 让客户端关闭当前页面（只作用于WEB端）
     */
    short CLOSE_PAGE = 15;
    /**
     * 消息提示
     */
    short MSG_TIP = 16;
    /**
     * 分页获取在线观众请求
     */
    short PAGE_ONLINE_REQ = 18;
    /**
     * 分页获取在线观众响应
     */
    short PAGE_ONLINE_RESP = 19;
    /**
     * 更新token
     */
    short UPDATE_TOKEN_REQ = 20;
    /**
     * 更新token响应
     */
    short UPDATE_TOKEN_RESP = 21;
    /**
     * 撤回消息
     */
    short UNSEND_MSG_REQ = 22;
    /**
     * 撤回消息通知
     */
    short UNSEND_MSG_NTY = 23;
    /**
     * 用户动作日志
     */
    short USER_ACTION_LOG_REQ = 24;
    /**
     * 已读请求： 我告诉服务器，张三发给我的私聊消息已读
     */
    short P2P_ALREADY_READ_REQ = 25;
    /**
     * 已读通知： 服务器告诉张三，张三发给李四的私聊，李四已经阅读
     */
    short P2P_ALREADY_READ_NTY = 26;
    /**
     * 查询最近私聊列表请求
     */
    short P2P_RECENT_CHAT_LIST_REQ = 27;
    /**
     * 查询最近私聊列表响应
     */
    short P2P_RECENT_CHAT_LIST_RESP = 28;

    // =====================================================================================
    // WX
    // =====================================================================================

    /**
     * 握手请求
     */
    short WX_HANDSHAKE_REQ = 599;
    /**
     * 握手响应
     */
    short WX_HANDSHAKE_RESP = 600;
    /**
     * 服务器通知用户"有人请求加你为好友啦"-- Server-->Client
     */
    @Deprecated
    short WX_APPLY_FRIEND_NTF = 601;
    /**
     * 朋友聊天请求，wx_friend_msg-- Client-->Server
     */
    short WX_FRIEND_CHAT_REQ = 602;
    /**
     * 私聊消息通知
     *
     * @see WxFriendChatNtf
     */
    short WX_FRIEND_CHAT_NTF = 603;
    /**
     * 私聊消息列表请求
     *
     * @see WxFriendMsgReq
     */
    short WX_FRIEND_MSG_REQ = 604;
    /**
     * 私聊消息列表响应
     *
     * @see WxFriendMsgResp
     */
    short WX_FRIEND_MSG_RESP = 605;
    /**
     * 群聊请求-- Client-->Server
     */
    short WX_GROUP_CHAT_REQ = 606;
    /**
     * 群聊通知
     *
     * @see WxGroupChatNtf
     */
    short WX_GROUP_CHAT_NTF = 607;
    /**
     * 已读请求： 告诉服务器，和某人的私聊信息已经阅读了
     */
    @Deprecated
    short WX_FRIEND_ALREADY_READ_REQ = 608;
    /**
     * 已读通知： 服务器转告张三，张三发给李四的私聊，李四已经阅读
     */
    @Deprecated
    short WX_FRIEND_ALREADY_READ_NTF = 609;
    /**
     * 已读请求： 告诉服务器，某群的信息已经阅读了
     */
    @Deprecated
    short WX_GROUP_ALREADY_READ_REQ = 610;
    /**
     * 撤回消息请求
     * 规则：
     * 1、自己只能撤回两分钟以内的消息
     * 2、超级管理员可以不受限制地随时随地撤回任何人的消息（前端用isSuper标识的，后端会二次检查）
     */
    @Deprecated
    short WX_WITHDRAW_MSG_REQ = 612;
    /**
     * 撤回消息通知
     */
    @Deprecated
    short WX_WITHDRAW_MSG_NTF = 613;
    /**
     * 离群通知。当某用户被T出群，或群被删除时，用户会收到这个通知
     * 消息体中有个type字段，用以标示离群原因：1：主动退群；2：被T出群；3：群被删除
     */
    @Deprecated
    short WX_LEAVE_GROUP_NTF = 614;
    /**
     * 你们不是好友
     * 你发消息给对方时，你并不是对方的好友，这时候前端提示当前用户发送申请好友请求
     */
    @Deprecated
    short WX_NOT_FRIEND_NTF = 615;
    /**
     * 群聊消息请求
     *
     * @see WxGroupMsgReq
     */
    short WX_GROUP_MSG_REQ = 620;
    /**
     * 群聊消息响应
     *
     * @see WxGroupMsgResp
     */
    short WX_GROUP_MSG_RESP = 621;

    // ====================================================================================
    // xufei-im-friend
    // ====================================================================================

    /**
     * 用户操作通知
     *
     * @see WxUserOperNtf
     */
    short WX_USER_OPER_NTF = 700;
    /**
     * 好友错误通知(异常通知)
     *
     * @see WxFriendErrorNtf
     */
    short WX_FRIEND_ERROR_NTF = 701;
    /**
     * 会话详情信息请求
     *
     * @see WxChatItemInfoReq
     */
    short WX_CHAT_ITEM_INFO_REQ = 708;
    /**
     * 会话详情信息响应
     *
     * @see WxChatItemInfoResp
     */
    short WX_CHAT_ITEM_INFO_RESP = 709;
    /**
     * 进入会话接口、跳出所有会话
     *
     * @see WxSessionOperReq
     */
    short WX_SESSION_OPER_REQ = 710;
    /**
     * 用户系统通知
     *
     * @see WxUserSysNtf
     */
    short WX_USER_SYS_NTF = 738;
    /**
     * 群操作通知
     *
     * @see WxGroupOperNtf
     */
    short WX_GROUP_OPER_NTF = 750;
    /**
     * 更新token
     *
     * @see WxUpdateTokenReq
     */
    short WX_UPDATE_TOKEN_REQ = 760;
    /**
     * 更新token响应
     */
    short WX_UPDATE_TOKEN_RESP = 761;
    /**
     * 焦点状态机请求
     */
    short WX_FOCUS_REQ = 776;
    /**
     * 焦点状态机通知
     */
    short WX_FOCUS_NTF = 777;

    // ====================================================================================
    // webrtc
    // ====================================================================================

    /**
     * a --> s   a向b发起通话请求
     */
    short WX_CALL_01_REQ = 800;
    /**
     * s --> b   s通知b，此时a和b要处于“占线”状态，后续呼入要直接拒绝
     */
    short WX_CALL_02_NTF = 801;
    /**
     * a --> s   取消通话请求（a发起通话后，在对方响应前进行了取消操作）
     */
    short WX_CALL_021_CANCEL_REQ = 814;
    /**
     * s --> a&b   取消通话通知（a发起通话后，在对方响应前进行了取消操作）
     */
    short WX_CALL_022_CANCEL_NTF = 815;
    /**
     * b --> s   b回复s：同意通话，或拒绝通话（拒绝原因：1、对方拒接，2、对方不在线， 3、对方占线，99、其它原因）
     */
    short WX_CALL_03_REPLY_REQ = 802;
    /**
     * 有 b 端回复了通话请求，所有 b 端都会收到该通知
     * <p>
     * 当 b --> s 发送 {@link com.watayouxiang.imclient.packet.TioCommand#WX_CALL_03_REPLY_REQ} 请求时，
     * 信令服务器给所有在线的 b 端发送 {@link com.watayouxiang.imclient.packet.TioCommand#WX_CALL_03_REPLY_REQ_NTF} 通知。
     */
    short WX_CALL_03_REPLY_REQ_NTF = 888;
    /**
     * s --> a   s转告a
     */
    short WX_CALL_04_REPLY_NTF = 803;
    /**
     * a --> s   a向b提供offer，需要提供 sdp
     */
    short WX_CALL_05_OFFER_SDP_REQ = 804;
    /**
     * s --> b   s转发给b
     */
    short WX_CALL_06_OFFER_SDP_NTF = 805;
    /**
     * b --> s   b向a回复Answer，需要提供 sdp
     */
    short WX_CALL_07_ANSWER_SDP_REQ = 806;
    /**
     * s --> a   s转发给a
     */
    short WX_CALL_08_ANSWER_SDP_NTF = 807;
    /**
     * a --> s   a向b提供offer，需要提供 e.candidate
     */
    short WX_CALL_09_OFFER_ICE_REQ = 808;
    /**
     * s --> b   s转发给b
     */
    short WX_CALL_10_OFFER_ICE_NTF = 809;
    /**
     * b --> s   b向a回复Answer，需要提供 e.candidate
     */
    short WX_CALL_11_ANSWER_ICE_REQ = 810;
    /**
     * s --> a   s转发给a
     */
    short WX_CALL_12_ANSWER_ICE_NTF = 811;
    /**
     * a或b --> s   发起结束通话请求
     */
    short WX_CALL_13_END_REQ = 812;
    /**
     * s --> a和b   通知结束通话，通话原因：1、对方主动挂电话；2、网络不好
     */
    short WX_CALL_14_END_NTF = 813;

    // =====================================================================================
    // 其他
    // =====================================================================================

    /**
     * DEMO 请求消息
     */
    short DEMO_REQ = 10000;
    /**
     * DEMO 通知消息
     */
    short DEMO_NTY = 10001;

    /**
     * 全局通知
     */
    short ALL_EVENT_PUSH = 10006;

    short xxxxx = (short) 99999;
}
