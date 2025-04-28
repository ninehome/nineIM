package com.watayouxiang.db.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.db.table.RedPacketStatusTableDao;

public class RedPacketCrud {

    public static void insert(@Nullable RedPacketStatusTable table) {
        if (table == null) return;
        table.setUid(TioDBPreferences.getCurrUid());
        TioDBHelper.getDaoSession().getRedPacketStatusTableDao().insertOrReplace(table);
    }

    public static RedPacketStatusTable queryByRedId(@NonNull Long id){
        return TioDBHelper.getDaoSession().getRedPacketStatusTableDao().queryBuilder()
                .where(RedPacketStatusTableDao.Properties.RedId.eq(id), RedPacketStatusTableDao.Properties.Uid.eq(TioDBPreferences.getCurrUid()))
                .unique();
    }

    public static void update(@Nullable RedPacketStatusTable table) {
        if (table == null) return;
        TioDBHelper.getDaoSession().getRedPacketStatusTableDao().update(table);
    }
}
