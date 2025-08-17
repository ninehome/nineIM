package com.tiocloud.chat.feature.session.common.adapter.msg;

import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2020-02-05
 * desc : 用于 UI 展示的消息模型
 */
public class TioMsg implements Serializable {
    /**
     * 消息 id（主键）
     */
    private String id = null;
    /**
     * 消息类型
     */
    private TioMsgType msgType = null;
    /**
     * true: 发送消息
     * false: 接收消息
     */
    private boolean isSendMsg = false;
    /**
     * 用户头像
     */
    private String avatar = null;
    /**
     * 用户名称
     */
    private String name = null;
    /**
     * 用户名称 "是否显示"
     */
    private boolean showName = true;
    /**
     * 消息时间戳
     */
    private Long time = null;
    /**
     * 消息内容
     */
    private String content = null;
    /**
     * 消息内容对象
     */
    private Object contentObj = null;
    /**
     * 用户 id
     */
    private String uid = null;
    /**
     * 会话 id
     */
    private String chatLinkId = null;
    /**
     * 艾特时的 "用户名称"
     */
    private String aitName = null;
    /**
     * 消息是否已读
     * <p>
     * true: 已读
     * false: 未读
     * null: 不显示
     */
    private Boolean isReadMsg = null;

//    "fromBizId": "270",
//            "fromChatMode": 2,
//            "title": "boos，12、a11111、a123456的聊天记录",

    private String fromBizId;
    private String fromChatMode;
    private String title;

    public String getFromBizId() {
        return fromBizId;
    }

    public void setFromBizId(String fromBizId) {
        this.fromBizId = fromBizId;
    }

    public String getFromChatMode() {
        return fromChatMode;
    }

    public void setFromChatMode(String fromChatMode) {
        this.fromChatMode = fromChatMode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
// ====================================================================================
    // getter
    // ====================================================================================

    public String getId() {
        return id;
    }

    public TioMsgType getMsgType() {
        return msgType;
    }

    public boolean isSendMsg() {
        return isSendMsg;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public boolean isShowName() {
        return showName;
    }

    public Long getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public Object getContentObj() {
        return contentObj;
    }

    public String getUid() {
        return uid;
    }

    public String getChatLinkId() {
        return chatLinkId;
    }

    public String getAitName() {
        return aitName;
    }

    public Boolean getReadMsg() {
        return isReadMsg;
    }

    public boolean isP2PMsg() {
        try {
            String chatLinkId = getChatLinkId();
            long _chatLinkId = Long.parseLong(chatLinkId);
            return _chatLinkId > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isGroupMsg() {
        try {
            String chatLinkId = getChatLinkId();
            long _chatLinkId = Long.parseLong(chatLinkId);
            return _chatLinkId < 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ====================================================================================
    // setter
    // ====================================================================================

    public void setId(String id) {
        this.id = id;
    }

    public void setMsgType(TioMsgType msgType) {
        this.msgType = msgType;
    }

    public void setSendMsg(boolean sendMsg) {
        isSendMsg = sendMsg;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShowName(boolean showName) {
        this.showName = showName;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentObj(Object contentObj) {
        this.contentObj = contentObj;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setChatLinkId(String chatLinkId) {
        this.chatLinkId = chatLinkId;
    }

    public void setAitName(String aitName) {
        this.aitName = aitName;
    }

    public void setReadMsg(Boolean readMsg) {
        this.isReadMsg = readMsg;
    }
}
