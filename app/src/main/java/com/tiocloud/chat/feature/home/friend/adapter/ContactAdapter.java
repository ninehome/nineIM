package com.tiocloud.chat.feature.home.friend.adapter;

import android.content.Context;
import android.widget.ListView;

import com.blankj.utilcode.util.CollectionUtils;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.FuncItem;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.ButtonHolder;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.ContactHolder;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.FuncHolder;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.HeadHolder;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.NumCountHolder;
import com.tiocloud.chat.widget.ContactsCatalogView;

import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-02-07
 * desc :
 */
public class ContactAdapter extends BaseContactAdapter {

    // 字母检索
    private ContactsCatalogView catalogView;

    public ContactAdapter(Context context) {
        super(context);
        addViewHolder(IItem.Type.CONTACT, ContactHolder.class);
        addViewHolder(IItem.Type.HEAD, HeadHolder.class);
        addViewHolder(IItem.Type.FUNC, FuncHolder.class);
        addViewHolder(IItem.Type.NUM_COUNT, NumCountHolder.class);
        addViewHolder(IItem.Type.BUTTON, ButtonHolder.class);
    }

    /**
     * 安装字母检索插件
     *
     * @param catalogView
     * @param listView
     */
    public void installCatalogView(ContactsCatalogView catalogView, final ListView listView) {
        this.catalogView = catalogView;
        catalogView.setOnTouchChangedListener(new ContactsCatalogView.OnTouchChangedListener() {
            @Override
            public void onHit(String letter) {
                Map<String, Integer> positionMap = getData().getGroupPositionMap();
                Integer position = positionMap.get(letter);
                if (position == null) return;

                if (position >= 0 && position < listView.getCount()) {
                    listView.setSelection(position);
                }
            }
        });
    }

    @Override
    public void setData(IData data) {
        super.setData(data);
        if (catalogView != null) {
            String[] groupIds = data.getGroupIds();
            catalogView.updateLetters(groupIds);
        }
    }

    /**
     * 更新好友申请数
     */
    public void updateFriendApplyNum(int integer) {
        IData data = getData();
        if (CollectionUtils.isEmpty(data)) return;
        for (int i = 0, size = data.size(); i < size; i++) {
            IItem item = data.get(i);
            if (item instanceof FuncItem) {
                ((FuncItem) item).applyCount = integer;
                notifyDataSetChanged();
                break;
            }
        }
    }
}
