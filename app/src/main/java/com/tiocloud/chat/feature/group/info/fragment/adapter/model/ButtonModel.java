package com.tiocloud.chat.feature.group.info.fragment.adapter.model;

import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020/3/16
 * desc :
 */
public abstract class ButtonModel {
    public String name;
    public int iconRes;

    public ButtonModel(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public abstract void onItemClick(TioActivity activity, String groupId);
}
