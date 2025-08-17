package com.watayouxiang.imclient.model;

/**
 * 加入群组响应码
 */
public enum JoinGroupResult {
    /**
     * 进入成功
     */
    OK((byte) 1),
    /**
     * 组不存在
     */
    FAIL_GROUP_NOT_EXIST((byte) 2),
    /**
     * 组满
     */
    FAIL_GROUP_FULL((byte) 3),
    /**
     * 你在黑名单中
     */
    FAIL_IN_BLACK_LIST((byte) 4),
    /**
     * 被踢
     */
    FAIL_KICKED((byte) 5),
    /**
     * 不允许游客进行
     */
    FAIL_TOURIST_NOT_ALLOWED((byte) 6),
    /**
     * 其它原因
     */
    FAIL_OTHER((byte) 99);

    public static JoinGroupResult from(Byte value) {
        JoinGroupResult[] values = JoinGroupResult.values();
        for (JoinGroupResult v : values) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    Byte value;

    private JoinGroupResult(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }
}
