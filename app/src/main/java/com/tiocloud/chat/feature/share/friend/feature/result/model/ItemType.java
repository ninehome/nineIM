package com.tiocloud.chat.feature.share.friend.feature.result.model;

/**
 * author : TaoWang
 * date : 2020-02-17
 * desc :
 */
public enum ItemType {
    FRIEND(1, "好友"),
    GROUP(2, "群聊");

    public final int value;
    public final String text;

    ItemType(int value, String text) {
        this.value = value;
        this.text = text;
    }
}
