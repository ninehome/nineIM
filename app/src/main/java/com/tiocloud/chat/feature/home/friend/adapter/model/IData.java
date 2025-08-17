package com.tiocloud.chat.feature.home.friend.adapter.model;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.home.friend.adapter.model.item.ContactItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.HeadItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.NumCountItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.sort.GroupStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-01-16
 * desc : 通讯录列表数据
 */
public class IData extends ArrayList<IItem> {

    // 组列表
    private LinkedList<IGroup> groups = new LinkedList<>();
    // 组位置索引表（key是groupId，value是groupPosition）
    private LinkedHashMap<String, Integer> groupPositionMap = new LinkedHashMap<>();
    // groupId数组
    private String[] groupIds = new String[]{};

    // ====================================================================================
    // ACCESS
    // ====================================================================================

    public Map<String, Integer> getGroupPositionMap() {
        return groupPositionMap;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    // ====================================================================================
    // BUILD
    // ====================================================================================

    /**
     * 构建数据（耗时）
     * 1、分组，组排序
     * 2、组中的元素排序
     * 3、建立组位置索引表
     * 4、替换数据
     * 5、建立 groupId 数组
     */
    public void build() {
        // 1、分组，组排序
        groups.clear();
        ListIterator<IItem> it = listIterator();
        List<IItem> numCountItems = new ArrayList<>();
        while (it.hasNext()) {
            IItem item = it.next();
            if (item instanceof HeadItem) {
                it.remove();
            }
        }

        final GroupStrategy strategy = new GroupStrategy();
        for (IItem item : this) {
            if (item instanceof  NumCountItem){
                remove(item);
                numCountItems.add(item);
                continue;
            }
            IGroup group = new IGroup(item.groupId());
            int index = Collections.binarySearch(groups, group, new Comparator<IGroup>() {
                @Override
                public int compare(IGroup o1, IGroup o2) {
                    return strategy.compare(o1.getId(), o2.getId());
                }
            });

            if (index >= 0) {
                groups.get(index).add(item);
            } else {
                index = -index - 1;
                group.add(item);
                groups.add(index, group);
            }
        }

        groupPositionMap.clear();
        int position = 0;
        List<String> ids = new ArrayList<>();
        for (IGroup group : groups) {
            String id = group.getId();
            // 2、组中的元素排序
            group.build(strategy.getGroupName(id), position);
            // 3、建立组位置索引表
            groupPositionMap.put(id, position);
            // 5、建立 groupId 数组
            if (id != null) {
                ids.add(id);
            }

            position += group.size();
        }
        groupIds = ids.toArray(new String[0]);

        // 4、替换数据
        this.clear();
        for (IGroup group : groups) {
            for (int i = 0, size = group.size(); i < size; i++) {
                IItem item = group.get(i);
                if (item instanceof ContactItem) {
                    ContactItem contactItem = (ContactItem) item;
                    contactItem.setLastPosition(i == size - 1);
                }
                this.add(item);
            }
        }

        for (IItem iItem : numCountItems){
            this.add(iItem);
        }
    }

    /**
     * 深拷贝
     *
     * @return
     */
    @SuppressWarnings({"unchecked"})
    @NonNull
    @Override
    public IData clone() {
        IData data = (IData) super.clone();
        data.groups = (LinkedList<IGroup>) this.groups.clone();
        data.groupPositionMap = (LinkedHashMap<String, Integer>) this.groupPositionMap.clone();
        data.groupIds = this.groupIds.clone();
        return data;
    }
}
