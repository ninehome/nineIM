package com.tiocloud.chat.feature.session.common.adapter.model;

public enum TioMsgStatus {
    draft(1),
    sending(2),
    success(3),
    fail(4),
    read(5),
    unread(6);

    private int value;

    private TioMsgStatus(int var3) {
        this.value = var3;
    }

    public static TioMsgStatus ofValue(int value) {
        TioMsgStatus[] enums = values();
        for (TioMsgStatus anEnum : enums) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

    public final int getValue() {
        return this.value;
    }
}
