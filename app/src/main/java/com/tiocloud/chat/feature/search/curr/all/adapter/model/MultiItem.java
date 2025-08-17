package com.tiocloud.chat.feature.search.curr.all.adapter.model;

import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class MultiItem {
    public MailListResp.Friend friend;
    public MailListResp.Group group;
    public Map<String, Object> msg;

    public MultiItem(MailListResp.Friend friend) {
        this.friend = friend;
    }

    public MultiItem(MailListResp.Group group) {
        this.group = group;
    }

    public MultiItem(Map<String, Object> msg) {
        this.msg = msg;
    }
}
