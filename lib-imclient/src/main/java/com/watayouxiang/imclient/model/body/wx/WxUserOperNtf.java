package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.BaseResp;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 用户操作通知
 * {@link TioCommand#WX_USER_OPER_NTF}
 */
public class WxUserOperNtf extends BaseResp {

    /**
     * actflag : 2
     * c : 删除好友
     * chatlinkid : 189
     * joinnum : 2
     * mid : 375315
     * oper : 5
     * t : 1582809348785
     * uid : 23436
     */

    /**
     * 激活状态 (1:是；2：否)
     */
    public int actflag;
    /**
     * 内容
     */
    public String c;
    /**
     * 会话id
     */
    public String chatlinkid;
    /**
     * 群聊激活时的群人数
     */
    public int joinnum;
    /**
     * 消息id，全局唯一，一条消息一个id
     */
    public String mid;
    /**
     * 操作码：
     * 1: 删除聊天会话(同时删除聊天记录)；
     * 2：拉黑；
     * 3：恢复拉黑(不拉黑)；
     * 4：激活通知；
     * 5：主动删除好友通知；
     * 6：被好友删除通知；
     * 7：好友已读通知；
     * 8：清空聊天消息通知
     * 9: 撤回消息
     * 10: 删除消息
     * 11: 删除聊天会话(不删除聊天记录)；
     * 21: 置顶
     * 22: 取消置顶
     * 55: 正在输入
     * 81: 平台通知
     */
    public int oper;
    /**
     * 消息发送时间（1582809348785）
     */
    public String t;
    /**
     * 发送方的userid
     */
    public int uid;
    /**
     * 对应：私聊的 mid
     */
    public String operbizdata;
    /**
     * 头像
     */
    public String actavatar;
    /**
     * 名称
     */
    public String actname;
    /**
     * 聊天会话的模型：1：私聊；2：群聊
     */
    public int chatmode;
    /**
     * 当 oper=4 || 10 时，该数据为一个"会话"
     */
    public ChatItems chatItems;
}
