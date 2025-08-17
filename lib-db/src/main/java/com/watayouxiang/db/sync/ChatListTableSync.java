package com.watayouxiang.db.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;
import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.db.event.ChatListTableEventBus;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.SynAckReq;
import com.watayouxiang.httpclient.model.request.SynChatReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.httpclient.model.response.SynChatResp;
import com.watayouxiang.httpclient.model.response.internal.ChatListBean;
import com.watayouxiang.httpclient.model.response.internal.SynItemBean;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   : HTTP 同步 {@link ChatListTable}
 * </pre>
 */
public class ChatListTableSync {
    private static class Holder {
        private static ChatListTableSync holder = new ChatListTableSync();
    }

    public static ChatListTableSync getInstance() {
        return Holder.holder;
    }

    private ChatListTableSync() {
    }

    /**
     * 开始同步
     */
    public void sync() {
        TioDBHelper.runInTx(this::syncInternal);
    }

    public void onSuccess(@NonNull ChatListTableEvent event) {
        ChatListTableEventBus.postInternal(event);
    }

    public void onError() {
        ChatListTableEventBus.syncError();
    }

    private void syncInternal() {
        // 获取 "同步时间"
        String synTime = getSynTime();
        // 获取 "会话数据"
        SynChatResp synChatResp = getSynChatResp(synTime);
        if (synChatResp == null) {
            // 失败回调
            onError();
            return;
        }
        // 同步到本地数据库
        ChatListTableEvent event = sync2ChatListTable(synChatResp);
        // 确认 "同步成功"
        boolean synAck = false;
        SynItemBean synitem = synChatResp.getSynitem();
        if (synitem != null) {
            int id = synitem.getId();
            synAck = sendSynAckReq(String.valueOf(id));
        }
        if (!synAck) {
            // 失败回调
            onError();
            return;
        }
        // 成功回调
        onSuccess(event);
    }

    /**
     * 同步确认（同步发送）
     *
     * @param synid 同步id
     */
    private boolean sendSynAckReq(@NonNull String synid) {
        SynAckReq synAckReq = new SynAckReq(synid);
        Response<BaseResp<String>> response = synAckReq.post();
        return response.isSuccessful();
    }

    /**
     * 同步到本地数据库
     */
    @NonNull
    private ChatListTableEvent sync2ChatListTable(@NonNull SynChatResp synChatResp) {
        boolean isAll = synChatResp.getAll() == 1;
        if (isAll) {
            ChatListTableCrud.deleteAll();
        }
        // 插入、更新
        List<ChatListBean> chatlist = synChatResp.getChatlist();
        List<ChatListTable> insertTables = ChatListTableCrud.insert(chatlist);
        List<ChatListResp.List> insertLists = ChatListTableConverter.convert2ChatListResp(insertTables);
        // 删除
        List<ChatListBean> dellist = synChatResp.getDellist();
        List<ChatListTable> deleteTables = ChatListTableCrud.delete(dellist);
        List<ChatListResp.List> deleteLists = ChatListTableConverter.convert2ChatListResp(deleteTables);
        // 构造 event
        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(isAll);
        event.setChatList(insertLists);
        event.setDelList(deleteLists);
        return event;
    }

    /**
     * 获取 "会话数据"
     */
    @Nullable
    private SynChatResp getSynChatResp(@Nullable String syntime) {
        SynChatReq synChatReq = new SynChatReq(syntime);
        Response<BaseResp<SynChatResp>> response = synChatReq.get();
        if (!response.isSuccessful()) {
            return null;
        }
        BaseResp<SynChatResp> body = response.body();
        if (body == null) {
            return null;
        }
        return body.getData();
    }

    /**
     * 获取 "同步时间"
     */
    @Nullable
    private String getSynTime() {
        String syntime = null;
        // TODO: 2020/8/31 修改时间，获取所有数据
//        List<ChatListTable> tables = ChatListTableUtils.queryAll();
//        if (tables == null || tables.size() == 0) {
//
//        }
        return syntime;
    }
}
