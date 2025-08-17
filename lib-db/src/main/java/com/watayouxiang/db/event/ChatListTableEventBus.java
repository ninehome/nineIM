package com.watayouxiang.db.event;

import androidx.annotation.NonNull;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.utils.Utils;
import com.watayouxiang.httpclient.model.response.ChatListResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/18
 *     desc   :
 * </pre>
 */
public class ChatListTableEventBus {
    /**
     * 发送事件
     */
    public static void postInternal(@NonNull ChatListTableEvent event) {
        // 切换到主线程
        // 发送事件
        TioDBHelper.getUIHandler().post(() -> TioDBHelper.getEventEngine().post(event));
    }

    /**
     * 更新同步
     */
    public static void update(@NonNull ChatListResp.List resp) {
        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(false);
        event.setChatList(Utils.newArrayList(resp));
        postInternal(event);
    }

    /**
     * 删除同步
     */
    public static void delete(@NonNull ChatListResp.List resp) {
        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(true);
        event.setAll(false);
        event.setDelList(Utils.newArrayList(resp));
        postInternal(event);
    }

    /**
     * 同步失败
     */
    public static void syncError() {
        ChatListTableEvent event = new ChatListTableEvent();
        event.setOk(false);
        postInternal(event);
    }
}
