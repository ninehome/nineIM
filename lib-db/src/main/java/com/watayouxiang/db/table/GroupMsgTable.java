package com.watayouxiang.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GroupMsgTable {
    @Id
    private Long id;

    private String avatar;

    //消息类型
    private int msgType;

    private String chatName;

    //0-群消息 1-p2p消息
    private int chatMode;
    //0-不是 1是
    private int sysMsg;

    private String syskey;

    private String formUid;
    /**
     * 当前登录账号
     */
    private Long curUid;
    //消息来源类型:1-HistoryGroupMsg, 2-GroupMsg 3-WxGroupChatReq
    private int sourceType;
    //仅当消息来源为3时，该字段有效。0-已发送 1-发送失败
    private int sendStatus;
    //群ID
    private String chatLinkId;
    //消息ID
    private int msgId;
    //消息序列化对象
    private String msgEntity;

    private String content;

    @Generated(hash = 325245834)
    public GroupMsgTable(Long id, String avatar, int msgType, String chatName,
            int chatMode, int sysMsg, String syskey, String formUid, Long curUid,
            int sourceType, int sendStatus, String chatLinkId, int msgId,
            String msgEntity, String content) {
        this.id = id;
        this.avatar = avatar;
        this.msgType = msgType;
        this.chatName = chatName;
        this.chatMode = chatMode;
        this.sysMsg = sysMsg;
        this.syskey = syskey;
        this.formUid = formUid;
        this.curUid = curUid;
        this.sourceType = sourceType;
        this.sendStatus = sendStatus;
        this.chatLinkId = chatLinkId;
        this.msgId = msgId;
        this.msgEntity = msgEntity;
        this.content = content;
    }

    @Generated(hash = 1375493013)
    public GroupMsgTable() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getChatMode() {
        return this.chatMode;
    }

    public void setChatMode(int chatMode) {
        this.chatMode = chatMode;
    }

    public int getSysMsg() {
        return this.sysMsg;
    }

    public void setSysMsg(int sysMsg) {
        this.sysMsg = sysMsg;
    }

    public Long getCurUid() {
        return this.curUid;
    }

    public void setCurUid(Long curUid) {
        this.curUid = curUid;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getSendStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getChatLinkId() {
        return this.chatLinkId;
    }

    public void setChatLinkId(String chatLinkId) {
        this.chatLinkId = chatLinkId;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsgEntity() {
        return this.msgEntity;
    }

    public void setMsgEntity(String msgEntity) {
        this.msgEntity = msgEntity;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSyskey() {
        return this.syskey;
    }

    public void setSyskey(String syskey) {
        this.syskey = syskey;
    }

    public String getChatName() {
        return this.chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFormUid() {
        return this.formUid;
    }

    public void setFormUid(String formUid) {
        this.formUid = formUid;
    }

}
