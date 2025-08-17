package com.watayouxiang.db.sync;

import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.dao.FocusTableCrud;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.db.table.FocusTable;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.body.wx.WxFocusNtf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   :
 * </pre>
 */
public class FocusTableSync {

    private static class Holder {
        private static FocusTableSync holder = new FocusTableSync();
    }

    public static FocusTableSync getInstance() {
        return Holder.holder;
    }

    private FocusTableSync() {
    }

    public void sync(@Nullable WxFocusNtf wxFocusNtf) {
        TioDBHelper.runInTx(() -> syncInternal(wxFocusNtf));
    }

    private void syncInternal(@Nullable WxFocusNtf wxFocusNtf) {
        // 获取数据
        List<FocusTable> focusTables = getFocusTables(wxFocusNtf);
        // 同步到 FocusTable
        sync2FocusTable(focusTables);
        // 同步到 ChatListTable
        List<ChatListTable> chatListTables = sync2ChatListTable(focusTables);
        // 同步成功
        onSyncSuccessInternal(chatListTables);
    }

    @Nullable
    private List<ChatListTable> sync2ChatListTable(@Nullable List<FocusTable> tables) {
        if (tables == null || tables.size() == 0) return null;

        // 如果 FocusTable # code 为 1
        // 那么将 (ChatListTable # id == FocusTable # chatLinkId) item 的未读消息字段（notreadcount）改为0
        List<ChatListTable> chatListTables = new ArrayList<>(2);

        for (int i = 0, size = tables.size(); i < size; i++) {
            FocusTable table = tables.get(i);
            if ("1".equals(table.getCode())) {
                String chatLinkId = table.getChatLinkId();
                ChatListTable chatListTable = ChatListTableCrud.update_notreadcount(chatLinkId, 0, 1);
                if (chatListTable != null) {
                    chatListTables.add(chatListTable);
                }
            }
        }

        return chatListTables;
    }

    private void sync2FocusTable(@Nullable List<FocusTable> tables) {
        // 存到本地数据库（清空数据，再存储数据）
        FocusTableCrud.deleteAll();
        FocusTableCrud.insert(tables);
    }

    @Nullable
    private List<FocusTable> getFocusTables(@Nullable WxFocusNtf wxFocusNtf) {
        if (wxFocusNtf == null) return null;
        Map<String, String> focusMap = wxFocusNtf.getFocusMap();
        if (focusMap == null) return null;

        List<FocusTable> tables = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = focusMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            FocusTable focusTable = new FocusTable(key, value);
            tables.add(focusTable);
        }
        return tables;
    }

    private void onSyncSuccessInternal(@Nullable List<ChatListTable> chatListTables) {
        // 切换到主线程
        TioDBHelper.getUIHandler().post(() -> onSyncSuccess(chatListTables));
        // 将改变了的 "List<ChatListTable>" 通过 EventBus 发送给页面
        postChatListTableEvent(chatListTables);
    }

    private void postChatListTableEvent(@Nullable List<ChatListTable> chatListTables) {
        // 将改变了的 "List<ChatListTable>" 通过 EventBus 发送给页面
        List<ChatListResp.List> chatListResps = ChatListTableConverter.convert2ChatListResp(chatListTables);
        if (chatListResps != null && chatListResps.size() > 0) {
            ChatListTableEvent event = new ChatListTableEvent();
            event.setOk(true);
            event.setAll(false);
            event.setChatList(chatListResps);
            TioDBHelper.getUIHandler().post(() -> TioDBHelper.getEventEngine().post(event));
        }
    }

    private void onSyncSuccess(@Nullable List<ChatListTable> chatListTables) {

    }
}
