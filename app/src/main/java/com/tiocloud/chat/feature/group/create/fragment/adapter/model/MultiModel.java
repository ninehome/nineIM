package com.tiocloud.chat.feature.group.create.fragment.adapter.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watayouxiang.httpclient.model.response.MailListResp;

/**
 * author : TaoWang
 * date : 2020/3/17
 * desc :
 */
public class MultiModel implements MultiItemEntity {

    public MultiType type;
    /**
     * 是否隐藏下滑先
     */
    public boolean isHideDivider = true;

    public MailListResp.Friend contact;
    public SectionModel section;

    public MultiModel(SectionModel section) {
        this.type = MultiType.SECTION;
        this.section = section;
    }

    public MultiModel(MailListResp.Friend contact) {
        this.type = MultiType.CONTACT;
        this.contact = contact;
    }

    @Override
    public int getItemType() {
        return type.value;
    }
}
