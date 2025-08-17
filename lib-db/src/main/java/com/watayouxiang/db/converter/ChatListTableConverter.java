package com.watayouxiang.db.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.dao.FocusTableCrud;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.db.utils.Utils;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.httpclient.model.response.internal.ChatListBean;
import com.watayouxiang.imclient.model.body.wx.WxFriendChatNtf;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatNtf;
import com.watayouxiang.imclient.model.body.wx.internal.ChatItems;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/01
 *     desc   :
 * </pre>
 */
public class ChatListTableConverter {

    @NonNull
    public static ChatListTable getInstance(@NonNull WxGroupChatNtf group) {
        ChatListTable item = new ChatListTable();
        update(item, group);
        return item;
    }

    public static void update(@NonNull ChatListTable item, @NonNull WxGroupChatNtf group) {
        // 会话类型
        item.setChatmode(2);
        // 激活会话
        if (group.actflag == 1) {
            // 会话头像
            item.setAvatar(group.actavatar);
            // 会话名称
            item.setName(group.actname);
        }
        // chatLinkId
        item.setId(group.chatlinkid);
        // fromUid
        item.setLastmsguid(group.f);
        // 消息id
        item.setLastmsgid(String.valueOf(group.mid));
        // 是否为系统消息
        item.setSysflag(group.sendbysys);
        // 系统消息key
        item.setSysmsgkey(group.sysmsgkey);
        // 操作者 昵称
        item.setOpernick(group.opernick);
        // 被操作者 昵称
        item.setTonicks(group.tonicks);
        // 发送时间
        item.setSendtime(Utils.long2DataString(group.t));
        // 发送者
        item.setFromnick(group.nick);
        // 最后一条消息
        item.setMsgresume(group.getShowContent());
        // 别人艾特我，我是否已读
        if (Utils.contains(group.at, Utils.getCurrUid())) {
            /* 有人艾特我，就标记有未读艾特消息 */
            item.setAtreadflag(2);
        }
        if (String.valueOf(group.f).equals(Utils.getCurrUid())) {
            /* 如果消息发送人是自己，那么将未读消息标记成已读艾特 */
            if (item.getAtreadflag() == 2) {
                item.setAtreadflag(1);
            }
        }
        // 未读消息数
        if (String.valueOf(group.f).equals(Utils.getCurrUid())) {
            item.setNotreadcount(0);
        } else {
            // 不在焦点表 && 不是系统发送 -> 未读数+1
            if (FocusTableCrud.query(group.chatlinkid) == null && group.sendbysys != 1) {
                item.setNotreadcount(item.getNotreadcount() + 1);
            }
        }
    }

    @NonNull
    public static ChatListTable getInstance(@NonNull WxFriendChatNtf friend) {
        ChatListTable item = new ChatListTable();
        update(item, friend);
        return item;
    }

    public static void update(@NonNull ChatListTable item, @NonNull WxFriendChatNtf friend) {
        // 会话类型
        item.setChatmode(1);
        // 激活会话
        if (friend.actflag == 1) {
            // 会话头像
            item.setAvatar(friend.actavatar);
            // 会话名称
            item.setName(friend.actname);
        }
        // chatLinkId
        item.setId(friend.chatlinkid);
        // 消息id
        item.setLastmsgid(friend.mid);
        // fromUid
        item.setLastmsguid(friend.uid);
        // 发送者
        item.setFromnick(friend.nick);
        // 是否为系统消息
        item.setSysflag(friend.sendbysys);
        // 系统消息key
        item.setSysmsgkey(friend.sysmsgkey);
        // 操作者 昵称
        item.setOpernick(friend.opernick);
        // 被操作者 昵称
        item.setTonicks(friend.tonicks);
        // 发送时间
        item.setSendtime(Utils.long2DataString(friend.t));
        // 最后一条消息
        item.setMsgresume(friend.getShowContent());
        // 对方是否已读
        item.setToreadflag(friend.readflag);
        // 未读消息数
        if (String.valueOf(friend.uid).equals(Utils.getCurrUid())) {
            item.setNotreadcount(0);
        } else if (friend.ct == 10 || friend.ct == 11) {
            // 音频电话
            // 视频电话
            // 未读消息数不计数
        } else {
            // 不在焦点表 && 不是系统发送 -> 未读数+1
            if (FocusTableCrud.query(friend.chatlinkid) == null && friend.sendbysys != 1) {
                item.setNotreadcount(item.getNotreadcount() + 1);
            }
        }
    }

    @NonNull
    public static ChatListTable getInstance(@NonNull ChatListBean resp) {
        ChatListTable table = new ChatListTable();

        table.setChatmode(resp.getChatmode());
        table.setLinkflag(resp.getLinkflag());
        table.setReadflag(resp.getReadflag());
        table.setUid(resp.getUid());
        table.setSysflag(resp.getSysflag());
        table.setLinkid(resp.getLinkid());
        table.setMsgresume(resp.getMsgresume());
        table.setTopflag(resp.getTopflag());
        table.setFromnick(resp.getFromnick());
        table.setNotreadcount(resp.getNotreadcount());
        table.setId(resp.getChatlinkid());
        table.setAtreadflag(resp.getAtreadflag());
        table.setViewflag(resp.getViewflag());
        table.setAvatar(resp.getAvatar());
        table.setBizid(resp.getBizid());
        table.setName(resp.getName());
        table.setToreadflag(resp.getToreadflag());
        table.setAtnotreadcount(resp.getAtnotreadcount());
        table.setLastmsgid(resp.getLastmsgid());
        table.setLastmsguid(resp.getLastmsguid());
        table.setSendtime(resp.getSendtime());
        table.setSysmsgkey(resp.getSysmsgkey());
        table.setOpernick(resp.getOpernick());
        table.setTonicks(resp.getTonicks());
        table.setChatuptime(resp.getChatuptime());
        table.setJoinnum(resp.getJoinnum());

        return table;
    }

    @NonNull
    public static ChatListTable getInstance(@NonNull ChatListResp.List resp) {
        ChatListTable table = new ChatListTable();

        table.setChatmode(resp.chatmode);
        table.setLinkflag(resp.linkflag);
        table.setReadflag(resp.readflag);
        table.setUid(resp.uid);
        table.setSysflag(resp.sysflag);
        table.setLinkid(resp.linkid);
        table.setMsgresume(resp.msgresume);
        table.setTopflag(resp.topflag);
        table.setFromnick(resp.fromnick);
        table.setNotreadcount(resp.notreadcount);
        table.setId(resp.id);
        table.setAtreadflag(resp.atreadflag);
        table.setViewflag(resp.viewflag);
        table.setAvatar(resp.avatar);
        table.setBizid(resp.bizid);
        table.setName(resp.name);
        table.setToreadflag(resp.toreadflag);
        table.setAtnotreadcount(resp.atnotreadcount);
        table.setLastmsgid(resp.lastmsgid);
        table.setLastmsguid(resp.lastmsguid);
        table.setSendtime(resp.sendtime);
        table.setSysmsgkey(resp.sysmsgkey);
        table.setOpernick(resp.opernick);
        table.setTonicks(resp.tonicks);
        table.setChatuptime(resp.chatuptime);
        table.setJoinnum(resp.joinnum);
//        table.setFidkey();
//        table.setBizrole();
//        table.setNotreadstartmsgid();
//        table.setAtnotreadstartmsgid();

        return table;
    }

    @NonNull
    public static ChatListResp.List convert2ChatListResp(@NonNull ChatListTable table) {
        ChatListResp.List list = new ChatListResp.List();

        list.chatmode = table.getChatmode();
        list.linkflag = table.getLinkflag();
        list.readflag = table.getReadflag();
        list.uid = table.getUid();
        list.sysflag = table.getSysflag();
        list.linkid = table.getLinkid();
        list.msgresume = table.getMsgresume();
        list.topflag = table.getTopflag();
        list.fromnick = table.getFromnick();
        list.notreadcount = table.getNotreadcount();
        list.id = table.getId();
        list.atreadflag = table.getAtreadflag();
        list.viewflag = table.getViewflag();
        list.avatar = table.getAvatar();
        list.bizid = table.getBizid();
        list.name = table.getName();
        list.toreadflag = table.getToreadflag();
        list.atnotreadcount = table.getAtnotreadcount();
        list.lastmsgid = table.getLastmsgid();
        list.lastmsguid = table.getLastmsguid();
        list.sendtime = table.getSendtime();
        list.sysmsgkey = table.getSysmsgkey();
        list.opernick = table.getOpernick();
        list.tonicks = table.getTonicks();
        list.chatuptime = table.getChatuptime();
        list.joinnum = table.getJoinnum();

        return list;
    }

    @Nullable
    public static List<ChatListResp.List> convert2ChatListResp(@Nullable List<ChatListTable> tables) {
        if (tables == null || tables.size() == 0) return null;
        List<ChatListResp.List> lists = new ArrayList<>();
        for (ChatListTable table : tables) {
            ChatListResp.List list = ChatListTableConverter.convert2ChatListResp(table);
            lists.add(list);
        }
        return lists;
    }

    @NonNull
    public static ChatListTable getInstance(@NonNull ChatItems chat) {
        ChatListTable table = new ChatListTable();
        update(table, chat);
        return table;
    }

    public static void update(@NonNull ChatListTable table, @NonNull ChatItems chat) {
        int chatmode = chat.getChatmode();
        if (chatmode != 0) {
            table.setChatmode(chatmode);
        }
        int linkflag = chat.getLinkflag();
        if (linkflag != 0) {
            table.setLinkflag(linkflag);
        }
        int readflag = chat.getReadflag();
        if (readflag != 0) {
            table.setReadflag(readflag);
        }
        int uid = chat.getUid();
        if (uid != 0) {
            table.setUid(uid);
        }
        int sysflag = chat.getSysflag();
        if (sysflag != 0) {
            table.setSysflag(sysflag);
        }
        String linkid = chat.getLinkid();
        if (linkid != null) {
            table.setLinkid(linkid);
        }
        String msgresume = chat.getMsgresume();
        if (msgresume != null) {
            table.setMsgresume(msgresume);
        }
        int topflag = chat.getTopflag();
        if (topflag != 0) {
            table.setTopflag(topflag);
        }
        String fromnick = chat.getFromnick();
        if (fromnick != null) {
            table.setFromnick(fromnick);
        }
        int notreadcount = chat.getNotreadcount();
        if (notreadcount != 0) {
            table.setNotreadcount(notreadcount);
        }
        String chatlinkid = chat.getChatlinkid();
        if (chatlinkid != null) {
            table.setId(chatlinkid);
        }
        int atreadflag = chat.getAtreadflag();
        if (atreadflag != 0) {
            table.setAtreadflag(atreadflag);
        }
        int viewflag = chat.getViewflag();
        if (viewflag != 0) {
            table.setViewflag(viewflag);
        }
        String avatar = chat.getAvatar();
        if (avatar != null) {
            table.setAvatar(avatar);
        }
        String bizid = chat.getBizid();
        if (bizid != null) {
            table.setBizid(bizid);
        }
        String name = chat.getName();
        if (name != null) {
            table.setName(name);
        }
        int toreadflag = chat.getToreadflag();
        if (toreadflag != 0) {
            table.setToreadflag(toreadflag);
        }
        int atnotreadcount = chat.getAtnotreadcount();
        if (atnotreadcount != 0) {
            table.setAtnotreadcount(atnotreadcount);
        }
        String lastmsgid = chat.getLastmsgid();
        if (lastmsgid != null) {
            table.setLastmsgid(lastmsgid);
        }
        int lastmsguid = chat.getLastmsguid();
        if (lastmsguid != 0) {
            table.setLastmsguid(lastmsguid);
        }
        String sendtime = chat.getSendtime();
        if (sendtime != null) {
            table.setSendtime(sendtime);
        }
        String sysmsgkey = chat.getSysmsgkey();
        if (sysmsgkey != null) {
            table.setSysmsgkey(sysmsgkey);
        }
        String opernick = chat.getOpernick();
        if (opernick != null) {
            table.setOpernick(opernick);
        }
        String tonicks = chat.getTonicks();
        if (tonicks != null) {
            table.setTonicks(tonicks);
        }
        String chatuptime = chat.getChatuptime();
        if (chatuptime != null) {
            table.setChatuptime(chatuptime);
        }
        int joinnum = chat.getJoinnum();
        if (joinnum != 0) {
            table.setJoinnum(joinnum);
        }
        String fidkey = chat.getFidkey();
        if (fidkey != null) {
            table.setFidkey(fidkey);
        }
        int bizrole = chat.getBizrole();
        if (bizrole != 0) {
            table.setBizrole(bizrole);
        }
        long notreadstartmsgid = chat.getNotreadstartmsgid();
        if (notreadstartmsgid != 0) {
            table.setNotreadstartmsgid(notreadstartmsgid);
        }
        long atnotreadstartmsgid = chat.getAtnotreadstartmsgid();
        if (atnotreadstartmsgid != 0) {
            table.setAtnotreadstartmsgid(atnotreadstartmsgid);
        }
    }
}
