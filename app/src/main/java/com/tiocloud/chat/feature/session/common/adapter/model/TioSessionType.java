package com.tiocloud.chat.feature.session.common.adapter.model;

public enum TioSessionType {
    P2P(1),
    Group(2);

    private int value;

    private TioSessionType(int var3) {
        this.value = var3;
    }

    public static TioSessionType ofValue(int value) {
        TioSessionType[] enums = values();
        for (TioSessionType anEnum : enums) {
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
