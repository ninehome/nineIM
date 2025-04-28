package com.tiocloud.chat.feature.search.curr.all.adapter.model;

import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public class SectionMultipleItem extends SectionMultiEntity<MultiItem> {

    private ItemType itemType;
    public int pageIndex;
    public boolean hasMore;

    public SectionMultipleItem(String header, int pageIndex, boolean hasMore) {
        super(true, header);
        this.pageIndex = pageIndex;
        this.hasMore = hasMore;
    }

    public SectionMultipleItem(String header, int pageIndex, boolean hasMore, ItemType itemType) {
        super(false, header);
        this.pageIndex = pageIndex;
        this.hasMore = hasMore;
        this.itemType = itemType;
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
