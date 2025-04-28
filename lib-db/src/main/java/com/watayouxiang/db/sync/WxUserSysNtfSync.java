package com.watayouxiang.db.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.event.ChatListTableEventBus;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.body.wx.WxUserSysNtf;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/25
 *     desc   :
 * </pre>
 */
public class WxUserSysNtfSync {
    private static class Holder {
        private static WxUserSysNtfSync holder = new WxUserSysNtfSync();
    }

    public static WxUserSysNtfSync getInstance() {
        return Holder.holder;
    }

    private WxUserSysNtfSync() {
    }

    public void sync(@Nullable WxUserSysNtf ntf) {
        if (ntf == null) return;
        TioDBHelper.runInTx(() -> syncInternal(ntf));
    }

    private void syncInternal(@NonNull WxUserSysNtf ntf) {
        if (ntf.code == 33) {
            if (ntf.chatItems != null) {
                ChatListTable table = ChatListTableConverter.getInstance(ntf.chatItems);
                ChatListTableCrud.update(table);
                ChatListResp.List resp = ChatListTableConverter.convert2ChatListResp(table);
                ChatListTableEventBus.update(resp);
            }
        }
    }
}
