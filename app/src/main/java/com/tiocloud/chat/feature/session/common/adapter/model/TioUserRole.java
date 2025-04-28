package com.tiocloud.chat.feature.session.common.adapter.model;

public enum TioUserRole {
    admin(1),
    master(2);

    private int value;

    private TioUserRole(int var3) {
        this.value = var3;
    }

    public static TioUserRole ofValue(int value) {
        TioUserRole[] enums = values();
        for (TioUserRole anEnum : enums) {
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
