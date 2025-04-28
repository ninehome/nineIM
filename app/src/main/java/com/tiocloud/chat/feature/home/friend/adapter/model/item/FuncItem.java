package com.tiocloud.chat.feature.home.friend.adapter.model.item;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.model.IGroup;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc :
 */
public class FuncItem extends IItem {

    public int imgResId = R.mipmap.icon_newfriends;
    public String name = ConstantUtils.context.getString(R.string.xinpengyou);
    public int applyCount;

    public FuncItem(int applyCount) {
        this.applyCount = applyCount;
    }

    @Override
    public int getType() {
        return Type.FUNC;
    }

    @Override
    public String groupId() {
        return IGroup.ID_HEADER;
    }
}
