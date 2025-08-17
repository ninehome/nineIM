package com.tiocloud.chat.feature.share.group.feature.result.model;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class SectionMultipleItem extends SectionMultiEntity<MultiItem> {

    public ItemType headType;
    public int totalCount;
    public int showCount;
    public int initCount;

    private ItemType itemType;

    public SectionMultipleItem(@NonNull ItemType headType, int totalCount, int showCount, int initCount) {
        super(true, headType.text);
        this.headType = headType;
        this.totalCount = totalCount;
        this.showCount = showCount;
        this.initCount = initCount;
    }

    public SectionMultipleItem(MultiItem multiItem, ItemType itemType) {
        super(multiItem);
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        if (itemType != null) {
            return itemType.value;
        }
        return 0;
    }
}
