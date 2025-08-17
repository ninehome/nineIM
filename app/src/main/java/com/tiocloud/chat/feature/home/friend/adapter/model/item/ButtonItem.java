package com.tiocloud.chat.feature.home.friend.adapter.model.item;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.model.IGroup;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;

/**
 * 自定义功能按钮
 */
public class ButtonItem extends IItem {
    public String name = "按钮";
    public int imgResId = R.drawable.ic_new_friend;
    public int btnType;

    public interface BtnType{
        int GROUP = 1;
        int LABLE = 2;
        int GROUPSEND = 3;
    }

    public ButtonItem(String name, int btnType, Integer imgResId) {
        this.name = name;
        if (imgResId != null){
            this.imgResId = imgResId;
        }
        this.btnType = btnType;
    }

    @Override
    public int getType() {
        return Type.BUTTON;
    }

    @Override
    public String groupId() {
        return IGroup.ID_HEADER;
    }
}
