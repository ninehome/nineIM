package com.watayouxiang.db.dao;


import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.GroupMsgTable;
import com.watayouxiang.db.table.GroupMsgTableDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupMsgTableCrud {


    public static void clear(String chatlinkId){
        List<GroupMsgTable> list = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder()
                .where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatlinkId)).list();
        for (GroupMsgTable groupMsgTable : list){
            TioDBHelper.getDaoSession().getGroupMsgTableDao().delete(groupMsgTable);
        }
    }
    public static void deleteUserMsg(String chatlinkId, String uid){
        List<GroupMsgTable> list = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder()
                .where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatlinkId)
                        , GroupMsgTableDao.Properties.FormUid.eq(uid)
                ).list();
        for (GroupMsgTable groupMsgTable : list){
            TioDBHelper.getDaoSession().getGroupMsgTableDao().delete(groupMsgTable);
        }
    }
    public static void deleteMsg(String chatlinkId, String msgId){
        List<GroupMsgTable> list = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder()
                .where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatlinkId)
                        , GroupMsgTableDao.Properties.MsgId.eq(msgId)
                ).list();
        for (GroupMsgTable groupMsgTable : list){
            TioDBHelper.getDaoSession().getGroupMsgTableDao().delete(groupMsgTable);
        }
    }

    public static void insertOrUpdate(int sourceType,String formUid, String chatlinkId, String chatName, String avatar, int sysFlag, String syskey, @NonNull int msgId, int chatMode, int msgType, String content, Object obj){
        GroupMsgTable groupMsgTable = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder()
                .where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatlinkId),
                        GroupMsgTableDao.Properties.MsgId.eq(msgId)).unique();
        if (groupMsgTable == null){
            groupMsgTable = new GroupMsgTable();
            groupMsgTable.setCurUid(TioDBPreferences.getCurrUid());
            groupMsgTable.setChatLinkId(chatlinkId);
            groupMsgTable.setMsgEntity(new Gson().toJson(obj));
            groupMsgTable.setSysMsg(sysFlag);
            groupMsgTable.setFormUid(formUid);
            groupMsgTable.setMsgType(msgType);
            groupMsgTable.setChatMode(chatMode);
            groupMsgTable.setContent(content);
            groupMsgTable.setMsgId(msgId);
            groupMsgTable.setSendStatus(0);
            groupMsgTable.setSourceType(sourceType);
            groupMsgTable.setSyskey(syskey);
            groupMsgTable.setChatName(chatName);
            groupMsgTable.setAvatar(avatar);
            TioDBHelper.getDaoSession().getGroupMsgTableDao().insert(groupMsgTable);
            return;
        }
        groupMsgTable.setSourceType(sourceType);
        groupMsgTable.setMsgEntity(new Gson().toJson(obj));
        groupMsgTable.setAvatar(avatar);
        groupMsgTable.setContent(content);
        if (groupMsgTable != null){
            groupMsgTable.setChatName(chatName);
        }
        TioDBHelper.getDaoSession().getGroupMsgTableDao().update(groupMsgTable);
    }

    public static List<GroupMsgTable>  queryList(int chatMode, String chatLinkId, Integer startMsgId){
        List<GroupMsgTable> groupMsgTableList = null;
        if (startMsgId != null){
            groupMsgTableList = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder().
                    where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                            GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId),
                            GroupMsgTableDao.Properties.MsgId.ge(startMsgId),
                            GroupMsgTableDao.Properties.ChatMode.eq(chatMode)
                    ).orderDesc(GroupMsgTableDao.Properties.MsgId).list();
            if (groupMsgTableList.size() < 150){
                groupMsgTableList = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder().
                        where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                                GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId),
                                GroupMsgTableDao.Properties.ChatMode.eq(chatMode)
                        ).orderDesc(GroupMsgTableDao.Properties.MsgId).limit(150).list();
            }
            Collections.reverse(groupMsgTableList);
        }else {
            groupMsgTableList = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder().
                    where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                            GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId),
                            GroupMsgTableDao.Properties.ChatMode.eq(chatMode)
                    ).orderDesc(GroupMsgTableDao.Properties.MsgId).limit(150).list();
            Collections.reverse(groupMsgTableList);
        }
        return groupMsgTableList;
    }

    public static GroupMsgTable getOne(int chatMode, String chatLinkId){
        GroupMsgTable groupMsgTable = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder().
                where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId),
                        GroupMsgTableDao.Properties.ChatMode.eq(chatMode)).orderDesc(GroupMsgTableDao.Properties.Id).limit(1).unique();
        return groupMsgTable;
    }

    public static List<GroupMsgTable> queryList(String keyword, int chatMode, String chatLinkId){
        List<GroupMsgTable> groupMsgTables = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder().
                where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId),
                        GroupMsgTableDao.Properties.ChatMode.eq(chatMode),
                        GroupMsgTableDao.Properties.SysMsg.notEq(1),
                        GroupMsgTableDao.Properties.MsgType.eq(1),
                        GroupMsgTableDao.Properties.Content.like("%"+keyword+"%")
                )
                .orderDesc(GroupMsgTableDao.Properties.Id)
                .list();
        return groupMsgTables;
    }


    public static List<Map<String, Object>> queryChatMsgGroup(String keyword){
        String sql = "select CHAT_MODE,CHAT_LINK_ID,count(1) as count from GROUP_MSG_TABLE where CUR_UID = "+ TioDBPreferences.getCurrUid()
                +" and SYS_MSG <> 1 and MSG_TYPE == 1 and CONTENT like '%"+keyword+"%' group by CHAT_MODE, CHAT_LINK_ID";
        Cursor cursor = TioDBHelper.getDaoSession().getDatabase().rawQuery(sql, null);
        List<Map<String, Object>> mapList = new ArrayList<>();
        while (cursor.moveToNext()){
            Map<String, Object> map = new HashMap<>();
            int chatMode = cursor.getInt(0);
            String chatlinkId = cursor.getString(1);
            map.put("chatMode", chatMode);
            map.put("chatLinkId", chatlinkId);
            map.put("count", cursor.getInt(2));

            GroupMsgTable groupMsgTable = getOne(chatMode, chatlinkId);
            if (groupMsgTable != null){
                map.put("name", groupMsgTable.getChatName());
                map.put("avatar", groupMsgTable.getAvatar());
            }
            mapList.add(map);
        }
        cursor.close();
        return mapList;
    }
    //删除mid>=的消息
    public static void deleteLast(String chatLinkId, int msgId){
        List<GroupMsgTable> list = TioDBHelper.getDaoSession().getGroupMsgTableDao().queryBuilder()
                .where(GroupMsgTableDao.Properties.CurUid.eq(TioDBPreferences.getCurrUid()),
                        GroupMsgTableDao.Properties.ChatLinkId.eq(chatLinkId)
                        , GroupMsgTableDao.Properties.MsgId.ge(msgId)
                ).list();
        for (GroupMsgTable groupMsgTable : list){
            TioDBHelper.getDaoSession().getGroupMsgTableDao().delete(groupMsgTable);
        }
    }
}
