package com.watayouxiang.db.event;

import androidx.annotation.Nullable;

import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.model.response.ChatListResp;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   : {@link ChatListTable} 同步通知
 * </pre>
 */
public class ChatListTableEvent {
    /**
     * 同步是否成功
     */
    private boolean ok = false;
    /**
     * 是否同步所有
     */
    private boolean isAll = false;
    /**
     * item集合（插入、更新）
     */
    @Nullable
    private List<ChatListResp.List> chatList = null;
    /**
     * item集合（删除）
     */
    @Nullable
    private List<ChatListResp.List> delList = null;

    public ChatListTableEvent() {
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    @Nullable
    public List<ChatListResp.List> getChatList() {
        return chatList;
    }

    public void setChatList(@Nullable List<ChatListResp.List> chatList) {
        this.chatList = chatList;
    }

    @Nullable
    public List<ChatListResp.List> getDelList() {
        return delList;
    }

    public void setDelList(@Nullable List<ChatListResp.List> delList) {
        this.delList = delList;
    }
}
