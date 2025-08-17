package com.tiocloud.chat.feature.home.friend.adapter.model;

/**
 * date : 2020-01-16
 * desc : 数据项
 */
public abstract class IItem<T extends IItem> implements Comparable<T> {

    public interface Type {
        int HEAD = -1;// 组头

        int FUNC = 0; // 功能项

        int CONTACT = 1; // 联系人

        int NUM_COUNT = 2; // 个数统计

        int BUTTON = 3; //群组//标签等按钮

    }

    /**
     * item类型
     *
     * @see Type
     */
    public abstract int getType();

    /**
     * 组id
     *
     * @return 组id
     */
    public String groupId() {
        return null;
    }

    @Override
    public int compareTo(T o) {
        return 0;
    }
}
