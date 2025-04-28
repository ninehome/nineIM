package com.tiocloud.chat.feature.home.friend.adapter.model;

import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.home.friend.adapter.model.item.HeadItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * author : TaoWang
 * date : 2020-01-21
 * desc : 组
 */
public class IGroup extends ArrayList<IItem> {

    public static final String ID_HEADER = "*";//42
    public static final String ID_OTHER = "#";//35

    // id（首字母）
    private final String id;
    // 名字
    private String name;
    // 位置
    private int position;

    public IGroup(String id) {
        this.id = id;
    }

    // ====================================================================================
    // ACCESS
    // ====================================================================================

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    // ====================================================================================
    // BUILD
    // ====================================================================================

    /**
     * 构建数据（耗时）
     * 1、设置组的 name，position
     * 2、item 排序
     * 3、自动添加"组头"
     *
     * @param name     组名（如果为空，则没有组头）
     * @param position 租所在的位置
     */
    @SuppressWarnings({"unchecked"})
    public void build(@Nullable String name, int position) {
        this.name = name;
        this.position = position;

        Collections.sort(this);

        if (name != null) {
            HeadItem head = new HeadItem(name);
            add(0, head);
        }
    }
}
