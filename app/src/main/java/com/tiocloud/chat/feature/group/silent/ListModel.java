package com.tiocloud.chat.feature.group.silent;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   :
 * </pre>
 */
class ListModel implements MultiItemEntity {
    public static final int ITEM_TYPE_NORMAL = 1;

    private final int itemType;
    private final ListNormalItem normalItem;

    public ListModel(ListNormalItem normalItem) {
        this.normalItem = normalItem;
        itemType = ITEM_TYPE_NORMAL;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public ListNormalItem getNormalItem() {
        return normalItem;
    }
}
