package com.watayouxiang.imclient.event;

public class ClearChatMsg {
    private String chatLinckId;

    public ClearChatMsg(String chatLinckId) {
        this.chatLinckId = chatLinckId;
    }

    public String getChatLinckId() {
        return chatLinckId;
    }

    public void setChatLinckId(String chatLinckId) {
        this.chatLinckId = chatLinckId;
    }
}
