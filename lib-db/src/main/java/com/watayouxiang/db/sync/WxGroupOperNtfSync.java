package com.watayouxiang.db.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.event.ChatListTableEventBus;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.imclient.model.body.wx.WxGroupOperNtf;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class WxGroupOperNtfSync {
    private static class Holder {
        private static WxGroupOperNtfSync holder = new WxGroupOperNtfSync();
    }

    public static WxGroupOperNtfSync getInstance() {
        return Holder.holder;
    }

    private WxGroupOperNtfSync() {
    }

    public void sync(@Nullable WxGroupOperNtf wxGroupOperNtf) {
        if (wxGroupOperNtf == null) return;
        TioDBHelper.runInTx(() -> syncInternal(wxGroupOperNtf));
    }

    private void syncInternal(@NonNull WxGroupOperNtf ntf) {
        if (ntf.oper == 1 || ntf.oper == 5) {
            // 删除群
            // 自动退群
            ChatListTable table = ChatListTableCrud.query(ntf.chatlinkid);
            if (table != null) {
                ChatListTableCrud.delete(ntf.chatlinkid);
                ChatListResp.List resp = ChatListTableConverter.convert2ChatListResp(table);
                ChatListTableEventBus.delete(resp);
            }
        } else {
            // 更新 model
            if (ntf.chatItems != null) {
                String chatlinkid = ntf.chatItems.getChatlinkid();
                ChatListTable table = ChatListTableCrud.query(chatlinkid);
                if (table != null) {
                    ChatListTableConverter.update(table, ntf.chatItems);
                    ChatListTableCrud.update(table);
                    ChatListResp.List resp = ChatListTableConverter.convert2ChatListResp(table);
                    ChatListTableEventBus.update(resp);
                }
            }
        }
    }
}
