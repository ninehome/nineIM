package com.watayouxiang.db.dao;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.CacheTable;
import com.watayouxiang.db.table.CacheTableDao;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.db.table.RedPacketStatusTableDao;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

public class CacheTableCrud {
    public static void insertOrUpdate(String cacheKey, Object cacheValue) {
//        String key = TioDBPreferences.getCurrUid()+keyEnd;
        CacheTable unique = TioDBHelper.getDaoSession().getCacheTableDao().queryBuilder()
                .where(CacheTableDao.Properties.Uid.eq(TioDBPreferences.getCurrUid()),
                        CacheTableDao.Properties.CacheKey.eq(cacheKey)).unique();
        if (unique == null){
            unique = new CacheTable();
            unique.setCacheKey(cacheKey);
            unique.setCacheValue(new Gson().toJson(cacheValue));
            unique.setUid(TioDBPreferences.getCurrUid());
            TioDBHelper.getDaoSession().getCacheTableDao().insert(unique);
        }else {
            unique.setCacheValue(new Gson().toJson(cacheValue));
            TioDBHelper.getDaoSession().getCacheTableDao().update(unique);
        }
    }

    public static <T> T getValue(String cacheKey, Class<T> cls){
        CacheTable unique = TioDBHelper.getDaoSession().getCacheTableDao().queryBuilder()
                .where(CacheTableDao.Properties.Uid.eq(TioDBPreferences.getCurrUid()),
                        CacheTableDao.Properties.CacheKey.eq(cacheKey)).unique();
        if (unique == null){
            return null;
        }
        String strValue = unique.getCacheValue();
        return new Gson().fromJson(strValue, cls);
    }

    public static int getGroupRole(String groupId, long uid){
        String key = "groupRole_"+groupId+"_"+uid;
        GroupRoleBean value = getValue(key, GroupRoleBean.class);
        return value == null ? 2 : value.getRole().intValue();
    }

    public static int getGroupRoleMy(String groupId){
        return getGroupRole(groupId, TioDBPreferences.getCurrUid());
    }

    public static int getGroupRoleChatlinkMy(String chatlink){
        return getGroupRole(String.valueOf(-Long.parseLong(chatlink)), TioDBPreferences.getCurrUid());
    }

    public static void setGroupRole(String groupId, String uid, int role){
        String key = "groupRole_"+groupId+"_"+uid;

        insertOrUpdate(key, new GroupRoleBean(role));
    }

    static class GroupRoleBean implements Serializable{
        Integer role;

        public GroupRoleBean(Integer role) {
            this.role = role;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }
    }
}
