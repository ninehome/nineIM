package com.tiocloud.chat.feature.home.chat.model;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.watayouxiang.httpclient.model.response.ChatListResp;

import java.util.Comparator;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/16
 *     desc   :
 * </pre>
 */
public class ItemComparator implements Comparator<ChatListResp.List> {
    /**
     * 默认情况下会根据升序进行排序
     * <p>
     * item1 等于 item2 返回 0
     * item1 在前返回 -1
     * item2 在前返回 1
     */
    @Override
    public int compare(@Nullable ChatListResp.List item1, @Nullable ChatListResp.List item2) {
        // 排除空可能
        if (item1 == null && item2 == null) {
            return 0;
        } else if (item1 != null && item2 == null) {
            return -1;
        } else if (item1 == null && item2 != null) {
            return 1;
        }

        // 置顶 排序
        boolean top1 = item1.topflag == 1;
        boolean top2 = item2.topflag == 1;
        if (top1 && !top2) {
            return -1;
        } else if (!top1 && top2) {
            return 1;
        }

        // sendtime 排序
        long sendTime1 = getTime(item1.sendtime);
        long sendTime2 = getTime(item2.sendtime);
        if (sendTime1 != -1 && sendTime2 != -1) {
            int deltaSendTime = Long.compare(sendTime2, sendTime1);
            if (deltaSendTime != 0) return deltaSendTime;
        }

        // chatuptime 排序
        long chatUpTime1 = getTime(item1.chatuptime);
        long chatUpTime2 = getTime(item2.chatuptime);
        if (chatUpTime1 != -1 && chatUpTime2 != -1) {
            int deltaChatUpTime = Long.compare(chatUpTime2, chatUpTime1);
            if (deltaChatUpTime != 0) return deltaChatUpTime;
        }

        // 昵称 排序
        String name1 = item1.name;
        String name2 = item2.name;
        if (name1 != null && name2 != null) {
            int deltaName = name2.compareTo(name1);
            if (deltaName != 0) return deltaName;
        }

        // id 排序
        long id1 = getId(item1.id);
        long id2 = getId(item2.id);
        return Long.compare(id2, id1);
    }

    private long getId(String id) {
        long _id = -1;
        try {
            _id = Long.parseLong(id);
        } catch (Exception ignored) {
        }
        return _id;
    }

    private long getTime(String time) {
        long _sendTime = -1;
        try {
            _sendTime = TimeUtils.string2Millis(time);
        } catch (Exception ignored) {
        }
        return _sendTime;
    }
}
