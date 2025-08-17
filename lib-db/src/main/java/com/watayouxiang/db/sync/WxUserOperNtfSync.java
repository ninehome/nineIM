package com.watayouxiang.db.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.db.utils.Utils;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.body.wx.WxUserOperNtf;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/02
 *     desc   :
 * </pre>
 */
public class WxUserOperNtfSync {
    private static class Holder {
        private static WxUserOperNtfSync holder = new WxUserOperNtfSync();
    }

    public static WxUserOperNtfSync getInstance() {
        return Holder.holder;
    }

    private WxUserOperNtfSync() {
    }

    public void sync(@Nullable WxUserOperNtf wxUserOperNtf) {
        if (wxUserOperNtf == null) return;
        TioDBHelper.runInTx(() -> syncInternal(wxUserOperNtf));
    }

    private void syncInternal(@NonNull WxUserOperNtf ntf) {
        if (ntf.oper == 1 || ntf.oper == 2 || ntf.oper == 5 || ntf.oper == 11) {
            // 1: 删除聊天会话(同时删除聊天记录)；
            // 2: 拉黑
            // 5：主动删除好友通知；
            // 11: 删除聊天会话(不删除聊天记录)；
            ChatListTable table = ChatListTableCrud.query(ntf.chatlinkid);
            ChatListTableCrud.delete(ntf.chatlinkid);
            onDeleteChat(table);
        } else if (ntf.oper == 21) {
            // 置顶
            ChatListTable table = ChatListTableCrud.update_topflag(ntf.chatlinkid, 1);
            onUpdateChat(table);
        } else if (ntf.oper == 22) {
            // 取消置顶
            ChatListTable table = ChatListTableCrud.update_topflag(ntf.chatlinkid, 2);
            onUpdateChat(table);
        } else if (ntf.oper == 7) {
            // 我发的消息，对方是否已读
            ChatListTable table = ChatListTableCrud.update_toreadflag(ntf.chatlinkid, 1);
            onUpdateChat(table);
        } else if (ntf.oper == 3 || ntf.oper == 4) {
            // 3 恢复拉黑
            // 4 激活会话
            if (ntf.chatItems != null) {
                ChatListTable table = ChatListTableConverter.getInstance(ntf.chatItems);
                ChatListTableCrud.insert(table);
                onUpdateChat(table);
            }
        } else if (ntf.oper == 10) {
            // 删除消息
            if (ntf.chatItems != null) {
                ChatListTable table = ChatListTableConverter.getInstance(ntf.chatItems);
                ChatListTableCrud.update(table);
                onUpdateChat(table);
            }
        }
    }

    private void onUpdateChat(@Nullable ChatListTable table) {
        if (table == null) return;

        ChatListResp.List chatListResp = ChatListTableConverter.convert2ChatListResp(table);
        List<ChatListResp.List> chatList = Utils.newArrayList(chatListResp);

        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(false);
        event.setChatList(chatList);

        postChatListTableEventOnMainThread(event);
    }

    private void onDeleteChat(@Nullable ChatListTable table) {
        if (table == null) return;

        ChatListResp.List chatListResp = ChatListTableConverter.convert2ChatListResp(table);
        List<ChatListResp.List> delList = Utils.newArrayList(chatListResp);

        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(false);
        event.setDelList(delList);

        postChatListTableEventOnMainThread(event);
    }

    private void postChatListTableEventOnMainThread(@Nullable ChatListTableEvent event) {
        if (event == null) return;
        TioDBHelper.getUIHandler().post(() -> TioDBHelper.getEventEngine().post(event));
    }
}
