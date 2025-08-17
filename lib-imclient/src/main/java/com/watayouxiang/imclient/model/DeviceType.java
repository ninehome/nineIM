package com.watayouxiang.imclient.model;

/**
 * 设备类型
 */
public enum DeviceType {
    /**
     * WS
     */
    PC((byte) 1),
    /**
     * 安卓
     */
    ANDROID((byte) 2),
    /**
     * IOS
     */
    IOS((byte) 3),
    /**
     * H5
     */
    H5((byte) 4);

    public static DeviceType from(Byte value) {
        DeviceType[] values = DeviceType.values();
        for (DeviceType v : values) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    Byte value;

    private DeviceType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }
}
