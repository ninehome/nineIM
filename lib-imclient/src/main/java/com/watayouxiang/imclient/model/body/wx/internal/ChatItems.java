package com.watayouxiang.imclient.model.body.wx.internal;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/21
 *     desc   :
 * </pre>
 */
public class ChatItems {
    /**
     * atnotreadcount : 0
     * atreadflag : 1
     * avatar : /wx/group/avatar/31/4204/1126206/88104259/74541317627/38/184144/1306543858401812480_sm.jpg
     * bizid : 338
     * bizrole : 2
     * chatlinkid : -338
     * chatmode : 2
     * chatuptime : 2020-09-21 15:20:53
     * createtime : 2020-09-17 18:41:20
     * fidkey :
     * focusflag : 2
     * fromnick : wata
     * id : -338
     * joinnum : 12
     * lastmsgid : 351259
     * lastmsguid : 23436
     * linkflag : 1
     * linkid : 73906
     * msgcount : 11
     * msgresume : [笑哭]
     * name : lyj04、lyj小号、老李、lyj2、lyj9、文曲11
     * notreadcount : 0
     * opernick : lyj04
     * readflag : 1
     * sendtime : 2020-09-21 15:20:44
     * startmsgid : 351172
     * sysflag : 2
     * sysmsgkey : join
     * tonicks : byterun
     * topflag : 2
     * toreadflag : 1
     * uid : 23436
     * updatetime : 2020-09-21 15:20:53
     * viewflag : 1
     */

    private int atnotreadcount;
    private int atreadflag;
    private String avatar;
    private String bizid;
    private int bizrole;
    private String chatlinkid;
    private int chatmode;
    private String chatuptime;
    private String createtime;
    private String fidkey;
    private int focusflag;
    private String fromnick;
    private String id;
    private int joinnum;
    private String lastmsgid;
    private int lastmsguid;
    private int linkflag;
    private String linkid;
    private int msgcount;
    private String msgresume;
    private String name;
    private int notreadcount;
    private String opernick;
    private int readflag;
    private String sendtime;
    private String startmsgid;
    private int sysflag;
    private String sysmsgkey;
    private String tonicks;
    private int topflag;
    private int toreadflag;
    private int uid;
    private String updatetime;
    private int viewflag;
    /**
     * 未读开始的消息id（暂时没用）
     */
    private long notreadstartmsgid;
    /**
     * 艾特未读的起始消息id（暂时没用）
     */
    private long atnotreadstartmsgid;

    public int getAtnotreadcount() {
        return atnotreadcount;
    }

    public void setAtnotreadcount(int atnotreadcount) {
        this.atnotreadcount = atnotreadcount;
    }

    public int getAtreadflag() {
        return atreadflag;
    }

    public void setAtreadflag(int atreadflag) {
        this.atreadflag = atreadflag;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public int getBizrole() {
        return bizrole;
    }

    public void setBizrole(int bizrole) {
        this.bizrole = bizrole;
    }

    public String getChatlinkid() {
        return chatlinkid;
    }

    public void setChatlinkid(String chatlinkid) {
        this.chatlinkid = chatlinkid;
    }

    public int getChatmode() {
        return chatmode;
    }

    public void setChatmode(int chatmode) {
        this.chatmode = chatmode;
    }

    public String getChatuptime() {
        return chatuptime;
    }

    public void setChatuptime(String chatuptime) {
        this.chatuptime = chatuptime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFidkey() {
        return fidkey;
    }

    public void setFidkey(String fidkey) {
        this.fidkey = fidkey;
    }

    public int getFocusflag() {
        return focusflag;
    }

    public void setFocusflag(int focusflag) {
        this.focusflag = focusflag;
    }

    public String getFromnick() {
        return fromnick;
    }

    public void setFromnick(String fromnick) {
        this.fromnick = fromnick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJoinnum() {
        return joinnum;
    }

    public void setJoinnum(int joinnum) {
        this.joinnum = joinnum;
    }

    public String getLastmsgid() {
        return lastmsgid;
    }

    public void setLastmsgid(String lastmsgid) {
        this.lastmsgid = lastmsgid;
    }

    public int getLastmsguid() {
        return lastmsguid;
    }

    public void setLastmsguid(int lastmsguid) {
        this.lastmsguid = lastmsguid;
    }

    public int getLinkflag() {
        return linkflag;
    }

    public void setLinkflag(int linkflag) {
        this.linkflag = linkflag;
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public String getMsgresume() {
        return msgresume;
    }

    public void setMsgresume(String msgresume) {
        this.msgresume = msgresume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNotreadcount() {
        return notreadcount;
    }

    public void setNotreadcount(int notreadcount) {
        this.notreadcount = notreadcount;
    }

    public String getOpernick() {
        return opernick;
    }

    public void setOpernick(String opernick) {
        this.opernick = opernick;
    }

    public int getReadflag() {
        return readflag;
    }

    public void setReadflag(int readflag) {
        this.readflag = readflag;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getStartmsgid() {
        return startmsgid;
    }

    public void setStartmsgid(String startmsgid) {
        this.startmsgid = startmsgid;
    }

    public int getSysflag() {
        return sysflag;
    }

    public void setSysflag(int sysflag) {
        this.sysflag = sysflag;
    }

    public String getSysmsgkey() {
        return sysmsgkey;
    }

    public void setSysmsgkey(String sysmsgkey) {
        this.sysmsgkey = sysmsgkey;
    }

    public String getTonicks() {
        return tonicks;
    }

    public void setTonicks(String tonicks) {
        this.tonicks = tonicks;
    }

    public int getTopflag() {
        return topflag;
    }

    public void setTopflag(int topflag) {
        this.topflag = topflag;
    }

    public int getToreadflag() {
        return toreadflag;
    }

    public void setToreadflag(int toreadflag) {
        this.toreadflag = toreadflag;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getViewflag() {
        return viewflag;
    }

    public void setViewflag(int viewflag) {
        this.viewflag = viewflag;
    }

    public long getNotreadstartmsgid() {
        return notreadstartmsgid;
    }

    public void setNotreadstartmsgid(long notreadstartmsgid) {
        this.notreadstartmsgid = notreadstartmsgid;
    }

    public long getAtnotreadstartmsgid() {
        return atnotreadstartmsgid;
    }

    public void setAtnotreadstartmsgid(long atnotreadstartmsgid) {
        this.atnotreadstartmsgid = atnotreadstartmsgid;
    }
}
