package com.tiocloud.chat.feature.home.friend.task.maillist;

import com.tiocloud.chat.feature.home.friend.adapter.model.IData;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class MailListTaskData {
    public IData data;
    public int friendNum;// 好友数量
    public String msg;
    public boolean ok;

    public MailListTaskData(IData data, int friendNum) {
        this.data = data;
        this.friendNum = friendNum;
        this.ok = true;
    }

    public MailListTaskData(String msg) {
        this.msg = msg;
        this.ok = false;
    }
}
