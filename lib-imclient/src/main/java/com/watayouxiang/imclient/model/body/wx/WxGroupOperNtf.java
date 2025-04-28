package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;
import com.watayouxiang.imclient.packet.TioCommand;

/**
 * author : TaoWang
 * date : 2020/2/28
 * desc :
 *
 * @see TioCommand#WX_GROUP_OPER_NTF
 */
public class WxGroupOperNtf {
    public static final short COMMAND = TioCommand.WX_GROUP_OPER_NTF;
    /**
     * c : 自动退群
     * chatlinkid : 191
     * chatmode : 2
     * g : 150
     * oper : 5
     * t : 1582876513880
     * uid : 23436
     */

    /**
     * 撤回消息
     *
     * "bizdata":"349293",
     * "chatlinkid":"-240",
     * "chatmode":2,
     * "g":"240",
     * "oper":9
     */

    /**
     * 聊天内容
     */
    public String c;
    /**
     * 聊天会话
     */
    public String chatlinkid;
    /**
     * 激活的聊天模型：1：私聊；2：群聊
     */
    public int chatmode;
    /**
     * 群id
     */
    public String g;
    /**
     * 操作码
     *
     * @see Oper
     */
    public int oper;
    /**
     * 消息发送时间
     */
    public String t;
    /**
     * 发送方的userid
     */
    public int uid;
    /**
     * 消息id，全局唯一，一条消息一个id
     */
    public int mid;
    /**
     * 激活头像
     */
    public String actavatar;
    /**
     * 业务信息-字符串：群用户数量
     * <p>
     * {@link Oper#BACK_MSG} 消息的mid
     * {@link Oper#DEL_MSG} 消息的mid
     */
    public String bizdata;
    /**
     * 激活名称
     */
    public String actname;

    /**
     * 当 oper=10 时，该数据为一个"会话"
     */
    public ChatItems chatItems;

    public enum Oper {
        DEL_GROUP("删除群", 1),
        CHANGE_OUT_GROUP("转让群", 2),
        CHANGE_IN_GROUP("接受群", 3),
        UPDATE_JOINNUM("群加入", 4),
        EXIT_GROUP("自动退群", 5),
        KICK_OUT_GROUP("被踢出群", 6),
        REJOIN_GROUP("重新加入群，进行同步信息", 7),
        BACK_MSG("撤回消息", 9),
        DEL_MSG("删除消息", 10),
        UPDATE_GROUP_AVATAR("修改群头像", 20),
        UPDATE_GROUP_NAME("修改群名称", 21),
        GROUP_MANAGE_UPDATE("群角色变更", 11),
        GROUP_REMOVED("移除群组消息", 24),
        ;

        private final String name;
        private final int code;

        Oper(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public static Oper valueOf(int code) {
            Oper[] values = values();
            for (Oper oper : values) {
                if (oper.code == code) {
                    return oper;
                }
            }
            return null;
        }
    }
}
