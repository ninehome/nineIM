package com.tiocloud.chat.feature.group.card.fragment.adapter.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watayouxiang.httpclient.model.response.MailListResp;

/**
 * author : TaoWang
 * date : 2020/3/17
 * desc :
 */
public class MultiModel implements MultiItemEntity {

    public MultiType type;
    public MailListResp.Group group;

    public MultiModel(MailListResp.Group group) {
        this.type = MultiType.GROUP;
        this.group = group;
    }

    @Override
    public int getItemType() {
        return type.value;
    }
}
