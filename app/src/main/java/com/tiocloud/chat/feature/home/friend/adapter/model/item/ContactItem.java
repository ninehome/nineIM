package com.tiocloud.chat.feature.home.friend.adapter.model.item;

import android.text.TextUtils;

import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.tiocloud.chat.feature.home.friend.adapter.model.IGroup;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.sort.TextComparator;

/**
 * date : 2020-01-16
 * desc : 联系人数据项
 */
public class ContactItem extends IItem<ContactItem> {

    private final IContact contact;// 联系人
    private boolean lastPosition;// 最后一个位置

    public ContactItem(IContact contact) {
        this.contact = contact;
    }

    public IContact getContact() {
        return contact;
    }

    public boolean isLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(boolean lastPosition) {
        this.lastPosition = lastPosition;
    }

    @Override
    public int getType() {
        return IItem.Type.CONTACT;
    }

    @Override
    public String groupId() {
        String contactName = getContactName();
        if (TextUtils.isEmpty(contactName)) {
            return IGroup.ID_OTHER;
        }
        String groupId = TextComparator.getLeadingUp(contactName);
        if (TextUtils.isEmpty(groupId)) {
            return IGroup.ID_OTHER;
        }
        return groupId;
    }

    /**
     * 排序
     *
     * @param item
     * @return
     */
    @Override
    public int compareTo(ContactItem item) {
        int compare = getType() - item.getType();
        if (compare != 0) {
            return compare;
        }
        return TextComparator.compareIgnoreCase(getContactName(), item.getContactName());
    }

    private String getContactName() {
        IContact contact = getContact();
        return contact != null ? contact.getName() : null;
    }
}
