package com.tiocloud.chat.feature.group.info.fragment.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tiocloud.chat.feature.group.info.fragment.adapter.model.ButtonModel;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 */
public class MemberItem implements MultiItemEntity {
    public static final int USER = 1;
    public static final int BUTTON = 2;

    public int itemType;
    public GroupUserListResp.GroupMember user;
    public ButtonModel button;

    public MemberItem(GroupUserListResp.GroupMember user) {
        this.itemType = USER;
        this.user = user;
    }

    public MemberItem(ButtonModel button) {
        this.itemType = BUTTON;
        this.button = button;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
