package com.watayouxiang.db.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.TransAmountStatusTable;
import com.watayouxiang.db.table.TransAmountStatusTableDao;

public class TransAmountCrud {

    public static void insert(@Nullable TransAmountStatusTable table) {
        if (table == null) return;
        table.setUid(TioDBPreferences.getCurrUid());
        TioDBHelper.getDaoSession().getTransAmountStatusTableDao().insertOrReplace(table);
    }

    public static TransAmountStatusTable queryByRedId(@NonNull Long id){
        return TioDBHelper.getDaoSession().getTransAmountStatusTableDao().queryBuilder()
                .where(TransAmountStatusTableDao.Properties.TransId.eq(id), TransAmountStatusTableDao.Properties.Uid.eq(TioDBPreferences.getCurrUid()))
                .unique();
    }

    public static void update(@Nullable TransAmountStatusTable table) {
        if (table == null) return;
        TioDBHelper.getDaoSession().getTransAmountStatusTableDao().update(table);
    }
}
