package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/25
 *     desc   : 会话列表（表）
 * </pre>
 */
@Entity
public class ChatListTable {
    /**
     * 聊天会话的模型：1：私聊；2：群聊
     */
    private int chatmode;
    private int linkflag;
    private int readflag;
    /**
     * 自己的uid
     */
    private int uid;
    /**
     * 最后一条消息系统标识：1：是系统消息；2：是正常消息
     */
    private int sysflag;
    /**
     * 群用户id / 好友关系id（暂时未用）
     */
    private String linkid;
    /**
     * 最后一条消息
     */
    private String msgresume;
    /**
     * 置顶标记
     * 1 置顶，2 非置顶
     */
    private int topflag;
    private String fromnick;
    /**
     * 自己未读的消息条数
     */
    private int notreadcount;
    /**
     * 聊天会话id-全局为chatlinkid
     */
    @Id
    private String id;
    /**
     * 1 已读艾特，2 未读艾特
     */
    private int atreadflag;
    private int viewflag;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 好友的uid或者群的groupid
     */
    private String bizid;
    /**
     * 昵称
     */
    private String name;
    /**
     * 我发的消息，对方是否已读
     * <p>
     * 1已读 2未读
     */
    private int toreadflag;
    /**
     * 艾特信息未读数（暂未用）
     */
    private int atnotreadcount;
    /**
     * 最后一条消息的 id
     */
    private String lastmsgid;
    /**
     * 最后一条消息的发送者的 uid
     */
    private int lastmsguid;
    /**
     * 最后一条消息发送的时间
     */
    private String sendtime;

    /* 系统模版消息所需字段 */
    /**
     * 系统消息key
     * <p>
     * ex: join
     */
    private String sysmsgkey;
    /**
     * 操作者 昵称
     */
    private String opernick;
    /**
     * 被操作者 昵称
     */
    private String tonicks;
    /**
     * 聊天更新时间（必定存在的时间，排序依据）
     */
    private String chatuptime;
    /**
     * 群聊激活时的群人数
     */
    private int joinnum;
    /**
     * 好友组合uid的key-twouid（暂时没用）
     */
    private String fidkey;
    /**
     * 角色：1：群主；2：成员（暂时没用）
     */
    private int bizrole;
    /**
     * 未读开始的消息id（暂时没用）
     */
    private long notreadstartmsgid;
    /**
     * 艾特未读的起始消息id（暂时没用）
     */
    private long atnotreadstartmsgid;

    @Generated(hash = 9777023)
    public ChatListTable(int chatmode, int linkflag, int readflag, int uid, int sysflag, String linkid, String msgresume, int topflag,
            String fromnick, int notreadcount, String id, int atreadflag, int viewflag, String avatar, String bizid, String name,
            int toreadflag, int atnotreadcount, String lastmsgid, int lastmsguid, String sendtime, String sysmsgkey, String opernick,
            String tonicks, String chatuptime, int joinnum, String fidkey, int bizrole, long notreadstartmsgid,
            long atnotreadstartmsgid) {
        this.chatmode = chatmode;
        this.linkflag = linkflag;
        this.readflag = readflag;
        this.uid = uid;
        this.sysflag = sysflag;
        this.linkid = linkid;
        this.msgresume = msgresume;
        this.topflag = topflag;
        this.fromnick = fromnick;
        this.notreadcount = notreadcount;
        this.id = id;
        this.atreadflag = atreadflag;
        this.viewflag = viewflag;
        this.avatar = avatar;
        this.bizid = bizid;
        this.name = name;
        this.toreadflag = toreadflag;
        this.atnotreadcount = atnotreadcount;
        this.lastmsgid = lastmsgid;
        this.lastmsguid = lastmsguid;
        this.sendtime = sendtime;
        this.sysmsgkey = sysmsgkey;
        this.opernick = opernick;
        this.tonicks = tonicks;
        this.chatuptime = chatuptime;
        this.joinnum = joinnum;
        this.fidkey = fidkey;
        this.bizrole = bizrole;
        this.notreadstartmsgid = notreadstartmsgid;
        this.atnotreadstartmsgid = atnotreadstartmsgid;
    }

    @Generated(hash = 751614282)
    public ChatListTable() {
    }

    public int getChatmode() {
        return this.chatmode;
    }

    public void setChatmode(int chatmode) {
        this.chatmode = chatmode;
    }

    public int getLinkflag() {
        return this.linkflag;
    }

    public void setLinkflag(int linkflag) {
        this.linkflag = linkflag;
    }

    public int getReadflag() {
        return this.readflag;
    }

    public void setReadflag(int readflag) {
        this.readflag = readflag;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSysflag() {
        return this.sysflag;
    }

    public void setSysflag(int sysflag) {
        this.sysflag = sysflag;
    }

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getMsgresume() {
        return this.msgresume;
    }

    public void setMsgresume(String msgresume) {
        this.msgresume = msgresume;
    }

    public int getTopflag() {
        return this.topflag;
    }

    public void setTopflag(int topflag) {
        this.topflag = topflag;
    }

    public String getFromnick() {
        return this.fromnick;
    }

    public void setFromnick(String fromnick) {
        this.fromnick = fromnick;
    }

    public int getNotreadcount() {
        return this.notreadcount;
    }

    public void setNotreadcount(int notreadcount) {
        this.notreadcount = notreadcount;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAtreadflag() {
        return this.atreadflag;
    }

    public void setAtreadflag(int atreadflag) {
        this.atreadflag = atreadflag;
    }

    public int getViewflag() {
        return this.viewflag;
    }

    public void setViewflag(int viewflag) {
        this.viewflag = viewflag;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBizid() {
        return this.bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getToreadflag() {
        return this.toreadflag;
    }

    public void setToreadflag(int toreadflag) {
        this.toreadflag = toreadflag;
    }

    public int getAtnotreadcount() {
        return this.atnotreadcount;
    }

    public void setAtnotreadcount(int atnotreadcount) {
        this.atnotreadcount = atnotreadcount;
    }

    public String getLastmsgid() {
        return this.lastmsgid;
    }

    public void setLastmsgid(String lastmsgid) {
        this.lastmsgid = lastmsgid;
    }

    public int getLastmsguid() {
        return this.lastmsguid;
    }

    public void setLastmsguid(int lastmsguid) {
        this.lastmsguid = lastmsguid;
    }

    public String getSendtime() {
        return this.sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSysmsgkey() {
        return this.sysmsgkey;
    }

    public void setSysmsgkey(String sysmsgkey) {
        this.sysmsgkey = sysmsgkey;
    }

    public String getOpernick() {
        return this.opernick;
    }

    public void setOpernick(String opernick) {
        this.opernick = opernick;
    }

    public String getTonicks() {
        return this.tonicks;
    }

    public void setTonicks(String tonicks) {
        this.tonicks = tonicks;
    }

    public String getChatuptime() {
        return this.chatuptime;
    }

    public void setChatuptime(String chatuptime) {
        this.chatuptime = chatuptime;
    }

    public int getJoinnum() {
        return this.joinnum;
    }

    public void setJoinnum(int joinnum) {
        this.joinnum = joinnum;
    }

    public String getFidkey() {
        return this.fidkey;
    }

    public void setFidkey(String fidkey) {
        this.fidkey = fidkey;
    }

    public int getBizrole() {
        return this.bizrole;
    }

    public void setBizrole(int bizrole) {
        this.bizrole = bizrole;
    }

    public long getNotreadstartmsgid() {
        return this.notreadstartmsgid;
    }

    public void setNotreadstartmsgid(long notreadstartmsgid) {
        this.notreadstartmsgid = notreadstartmsgid;
    }

    public long getAtnotreadstartmsgid() {
        return this.atnotreadstartmsgid;
    }

    public void setAtnotreadstartmsgid(long atnotreadstartmsgid) {
        this.atnotreadstartmsgid = atnotreadstartmsgid;
    }
}
