package com.tiocloud.chat.feature.home.chat.mvp;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ThreadUtils;
import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.feature.home.chat.model.ItemComparator;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ChatListReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;

import java.util.Collections;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class ChatModel extends ChatContract.Model {

    public ChatModel() {
        super(false);
    }

    @Override
    public void getChatList(@NonNull DataProxy<ChatListResp> proxy) {
        ChatListReq chatListReq = new ChatListReq();
        chatListReq.setCancelTag(this);
        chatListReq.setCacheMode(CacheMode.NO_CACHE);
        chatListReq.get(new TioCallback<ChatListResp>() {
            @Override
            public void onTioSuccess(ChatListResp resp) {
                proxy.onSuccess(resp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                proxy.onFinish();
            }
        });
    }

    // ====================================================================================
    // 查询数据库
    // ====================================================================================

    @Override
    public void queryAllFromDB(@NonNull DataProxy<ChatListResp> proxy) {
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<ChatListResp>() {
            @Override
            public ChatListResp doInBackground() throws Throwable {
                // 查询所有数据
                ChatListResp resp = ChatListTableCrud.queryAll_ChatListResp();
                // 排序
                if (resp != null) {
                    Collections.sort(resp, new ItemComparator());
                }
                return resp;
            }

            @Override
            public void onSuccess(ChatListResp result) {
                proxy.onSuccess(result);
            }
        });
    }
}
