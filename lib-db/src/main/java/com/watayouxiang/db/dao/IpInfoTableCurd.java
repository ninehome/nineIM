package com.watayouxiang.db.dao;

import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.converter.IpInfoTableConverter;
import com.watayouxiang.db.table.IpInfoTable;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class IpInfoTableCurd {
    public static void insert(@Nullable IpInfoTable table) {
        if (table == null) return;
        TioDBHelper.getDaoSession().getIpInfoTableDao().insertOrReplace(table);
    }

    public static void insert(@Nullable UserCurrResp.IpInfoBean ipInfo, int id) {
        if (ipInfo == null) return;
        IpInfoTable table = IpInfoTableConverter.getInstance(ipInfo, id);
        insert(table);
    }
}
