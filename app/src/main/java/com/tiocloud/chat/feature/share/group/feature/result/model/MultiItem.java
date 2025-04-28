package com.tiocloud.chat.feature.share.group.feature.result.model;

import com.watayouxiang.httpclient.model.response.MailListResp;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class MultiItem {
    public MailListResp.Friend friend;
    public MailListResp.Group group;

    public MultiItem(MailListResp.Friend friend) {
        this.friend = friend;
    }

    public MultiItem(MailListResp.Group group) {
        this.group = group;
    }
}
