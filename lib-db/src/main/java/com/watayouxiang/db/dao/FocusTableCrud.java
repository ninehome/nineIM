package com.watayouxiang.db.dao;

import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.table.FocusTable;
import com.watayouxiang.db.table.FocusTableDao;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   :
 * </pre>
 */
public class FocusTableCrud {

    public static void deleteAll() {
        TioDBHelper.getDaoSession().getFocusTableDao().deleteAll();
    }

    public static void insert(@Nullable List<FocusTable> tables) {
        if (tables == null || tables.size() == 0) return;
        TioDBHelper.getDaoSession().getFocusTableDao().insertOrReplaceInTx(tables);
    }

    @Nullable
    public static FocusTable query(@Nullable String chatLinkId) {
        if (chatLinkId == null) return null;
        return TioDBHelper.getDaoSession().getFocusTableDao().queryBuilder()
                .where(FocusTableDao.Properties.ChatLinkId.eq(chatLinkId))
                .unique();
    }

    @Nullable
    public static List<FocusTable> queryAll() {
        return TioDBHelper.getDaoSession().getFocusTableDao().loadAll();
    }

}
