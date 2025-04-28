package com.tiocloud.chat.feature.home.friend.adapter.model.item;

import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;

public class HeadItem extends IItem {
    private final String text;

    public HeadItem(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return Type.HEAD;
    }

    public final String getText() {
        return text;
    }
}
