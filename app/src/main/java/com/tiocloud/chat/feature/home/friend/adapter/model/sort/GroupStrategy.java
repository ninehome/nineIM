package com.tiocloud.chat.feature.home.friend.adapter.model.sort;

import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.home.friend.adapter.model.IGroup;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * date : 2020-01-22
 * desc : 分组比较器
 */
public class GroupStrategy implements Comparator<String> {

    @Override
    public int compare(String groupId1, String groupId2) {
        Integer groupOrder1 = getGroupOrder(groupId1);
        Integer groupOrder2 = getGroupOrder(groupId2);

        if (groupOrder1 == null && groupOrder2 == null) {
            return 0;
        } else if (groupOrder1 == null) {
            return -1;
        } else if (groupOrder2 == null) {
            return 1;
        } else {
            return groupOrder1 - groupOrder2;
        }
    }

    // ====================================================================================
    // ACCESS
    // ====================================================================================

    /**
     * 获取组编号
     *
     * @param groupId 组id
     * @return
     */
    private Integer getGroupOrder(@Nullable String groupId) {
        Group group = map.get(groupId);
        return group != null ? group.order : null;
    }

    /**
     * 获取组名
     *
     * @param groupId 组id
     * @return
     */
    public final @Nullable
    String getGroupName(String groupId) {
        Group group = map.get(groupId);
        return group != null ? group.name : null;
    }

    // ==========================================================================================
    // BUILD
    // ==========================================================================================

    // key = groupId，value = group
    private final Map<String, Group> map = new LinkedHashMap<>();

    public GroupStrategy() {
        int order = add(IGroup.ID_HEADER, 0, null);
        order = addABC(order);
        order = add(IGroup.ID_OTHER, order, IGroup.ID_OTHER);
        add(null, order, null);
    }

    private int addABC(int order) {
        for (char i = 0; i < 26; i++) {
            String id = Character.toString((char) ('A' + i));
            order = add(id, order, id);
        }
        return order;
    }

    private int add(String id, int order, String name) {
        map.put(id, new Group(order++, name));
        return order;
    }

    private static final class Group {
        private final int order;

        private final String name;

        Group(int order, String name) {
            this.order = order;
            this.name = name;
        }
    }
}
