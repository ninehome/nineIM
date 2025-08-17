package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 用户系统通知
 * <p>
 * {@link TioCommand#WX_USER_SYS_NTF}
 */
public class WxUserSysNtf extends BaseResp {
    /**
     * bizdata : 33080
     * c : 好友发生变更，好友关系id：33080
     * code : 31
     * mid : 375323
     * t : 1582810507725
     * uid : 23436
     */

    /**
     * 激活名称
     */
    public String bizdata;
    /**
     * 内容
     */
    public String c;
    /**
     * 30 申请好友请求
     * 31 好友发生变更-新增
     * 32 好友发生变更-减员
     * 33 好友发生变更-信息修改
     */
    public int code;
    /**
     * 消息id，全局唯一，一条消息一个id
     */
    public String mid;
    /**
     * 消息发送时间(1582810507725)
     */
    public String t;
    /**
     * 发送方的userid
     */
    public int uid;
    /**
     * 当 oper=33 时，该数据为一个"会话"
     */
    public ChatItems chatItems;
}
