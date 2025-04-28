package com.tiocloud.chat.feature.search.curr.all.adapter.model;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public enum ItemType {
    FRIEND(1),
    GROUP(2),
    BOTTOM(3),
    FRIEND_BOTTOM(4),
    GROUP_BOTTOM(5),
    MSG(6),
    MSG_BOTTOM(7)
    ;

    public final int value;

    ItemType(int value) {
        this.value = value;
    }
}
