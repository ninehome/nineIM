package com.watayouxiang.db.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/02
 *     desc   :
 * </pre>
 */
public class WxGroupChatNtfSync {
    private static class Holder {
        private static WxGroupChatNtfSync holder = new WxGroupChatNtfSync();
    }

    public static WxGroupChatNtfSync getInstance() {
        return Holder.holder;
    }

    private WxGroupChatNtfSync() {
    }

    public void sync(@Nullable WxGroupChatNtf wxGroupChatNtf) {
        if (wxGroupChatNtf == null) return;
        TioDBHelper.runInTx(() -> syncInternal(wxGroupChatNtf));
    }

    private void syncInternal(@NonNull WxGroupChatNtf ntf) {
        // 查询数据库是否存在
        ChatListTable table = ChatListTableCrud.query(ntf.chatlinkid);
        if (table != null) {
            // 如果存在，则更新
            ChatListTableConverter.update(table, ntf);
            ChatListTableCrud.update(table);
        } else {
            // 如果不存在，则新增
            table = ChatListTableConverter.getInstance(ntf);
            ChatListTableCrud.insert(table);
        }
        onSyncSuccessInternal(table);
    }

    private void onSyncSuccessInternal(@NonNull ChatListTable table) {
        // 通知ui，数据更新了
        List<ChatListResp.List> tables = new ArrayList<>();
        ChatListResp.List chatListResp = ChatListTableConverter.convert2ChatListResp(table);
        tables.add(chatListResp);

        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(false);
        event.setChatList(tables);

        TioDBHelper.getUIHandler().post(() -> TioDBHelper.getEventEngine().post(event));
    }
}
