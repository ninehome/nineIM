package com.tiocloud.chat.feature.home.friend.task.maillist;

import android.text.TextUtils;

import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.watayouxiang.httpclient.model.response.MailListResp;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class MailListModel implements IContact {

    private final MailListResp.Friend friend;

    public MailListModel(MailListResp.Friend f) {
        friend = f;
    }

    @Override
    public String getAvatar() {
        return friend.avatar;
    }

    @Override
    public String getName() {
        if (!TextUtils.isEmpty(friend.remarkname)) {
            return friend.remarkname;
        }
        return friend.nick;
    }

    @Override
    public String getId() {
        return String.valueOf(friend.uid);
    }
}
