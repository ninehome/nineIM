package com.watayouxiang.db.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.db.table.ChatListTableDao;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.httpclient.model.response.internal.ChatListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/28
 *     desc   :
 * </pre>
 */
public class ChatListTableCrud {

    // ====================================================================================
    // insert
    // ====================================================================================

    public static void insert(@NonNull final ChatListTable item) {
        TioDBHelper.getDaoSession().getChatListTableDao().insertOrReplace(item);
    }

    public static void insert(@NonNull Iterable<ChatListTable> tables) {
        TioDBHelper.getDaoSession().getChatListTableDao().insertOrReplaceInTx(tables);
    }

    public static void insert(@NonNull ChatListResp resp) {
        List<ChatListTable> tables = new ArrayList<>();
        for (int i = 0, size = resp.size(); i < size; i++) {
            ChatListResp.List item = resp.get(i);
            ChatListTable table = ChatListTableConverter.getInstance(item);
            tables.add(table);
        }
        insert(tables);
    }

    @Nullable
    public static List<ChatListTable> insert(@Nullable List<ChatListBean> chatList) {
        if (chatList == null || chatList.size() == 0) return null;
        // 数据转换
        List<ChatListTable> tables = new ArrayList<>();
        for (int i = 0, size = chatList.size(); i < size; i++) {
            ChatListBean bean = chatList.get(i);
            ChatListTable table = ChatListTableConverter.getInstance(bean);
            tables.add(table);
        }
        // 数据插入
        insert(tables);
        // 返回插入的数据
        return tables;
    }

    // ====================================================================================
    // delete
    // ====================================================================================

    public static void delete(@Nullable final ChatListTable item) {
        if (item == null) return;
        TioDBHelper.getDaoSession().getChatListTableDao().delete(item);
    }

    public static void deleteAll() {
        TioDBHelper.getDaoSession().getChatListTableDao().deleteAll();
    }

    public static void delete(@NonNull Iterable<ChatListTable> tables) {
        TioDBHelper.getDaoSession().getChatListTableDao().deleteInTx(tables);
    }

    public static void delete(@Nullable String chatLinkId) {
        if (chatLinkId == null) return;
        TioDBHelper.getDaoSession().getChatListTableDao().deleteByKey(chatLinkId);
    }

    @Nullable
    public static List<ChatListTable> delete(@Nullable List<ChatListBean> delList) {
        if (delList == null || delList.size() == 0) return null;

        List<ChatListTable> tables = new ArrayList<>();
        // 数据转换
        for (int i = 0, size = delList.size(); i < size; i++) {
            ChatListBean bean = delList.get(i);
            ChatListTable table = ChatListTableConverter.getInstance(bean);
            tables.add(table);
        }
        // 删除
        delete(tables);
        // 返回删除的数据
        return tables;
    }

    // ====================================================================================
    // update
    // ====================================================================================

    public static void update(@Nullable final ChatListTable item) {
        if (item == null) return;
        TioDBHelper.getDaoSession().getChatListTableDao().update(item);
    }

    @Nullable
    public static ChatListTable update_notreadcount(@Nullable String chatLinkId, int notreadcount, int atreadflag) {
        ChatListTable chatListItem = query(chatLinkId);
        if (chatListItem != null) {
            chatListItem.setNotreadcount(notreadcount);
            chatListItem.setAtreadflag(atreadflag);
            update(chatListItem);
        }
        return chatListItem;
    }

    @Nullable
    public static ChatListTable update_avatar(@Nullable String chatLinkId, String avatar) {
        ChatListTable chatListItem = query(chatLinkId);
        if (chatListItem != null) {
            chatListItem.setAvatar(avatar);
            update(chatListItem);
        }
        return chatListItem;
    }

    @Nullable
    public static ChatListTable update_topflag(@Nullable String chatLinkId, int topFlag) {
        ChatListTable chatListItem = query(chatLinkId);
        if (chatListItem != null) {
            chatListItem.setTopflag(topFlag);
            update(chatListItem);
        }
        return chatListItem;
    }

    @Nullable
    public static ChatListTable update_toreadflag(@Nullable String chatLinkId, int toReadFlag) {
        ChatListTable chatListItem = query(chatLinkId);
        if (chatListItem != null) {
            chatListItem.setToreadflag(toReadFlag);
            update(chatListItem);
        }
        return chatListItem;
    }

    // ====================================================================================
    // query
    // ====================================================================================

    public static List<ChatListTable> queryAll() {
        return TioDBHelper.getDaoSession().getChatListTableDao().loadAll();
    }

    @Nullable
    public static ChatListTable query(@Nullable String chatLinkId) {
        if (chatLinkId == null) return null;
        return TioDBHelper.getDaoSession().getChatListTableDao().queryBuilder()
                .where(ChatListTableDao.Properties.Id.eq(chatLinkId))
                .unique();
    }

    @Nullable
    public static ChatListResp queryAll_ChatListResp() {
        List<ChatListTable> tables = queryAll();
        ChatListResp resp = null;
        if (tables != null && tables.size() > 0) {
            resp = new ChatListResp();
            for (int i = 0; i < tables.size(); i++) {
                ChatListTable table = tables.get(i);
                ChatListResp.List item = ChatListTableConverter.convert2ChatListResp(table);
                resp.add(item);
            }
        }
        return resp;
    }
}
