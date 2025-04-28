package com.tiocloud.chat.feature.home.friend.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.tiocloud.chat.feature.home.friend.adapter.viewholder.BaseViewHolder;


/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 */
public class BaseContactAdapter extends BaseAdapter {

    private final Context context;
    // 列表数据
    private IData data = new IData();
    // viewHolder 检索表（key是itemType，value是viewHolder）
    private final SparseArray<Class<? extends BaseViewHolder<? extends IItem>>> viewHolders = new SparseArray<>(3);

    public BaseContactAdapter(Context context) {
        this.context = context;
    }

    public IData getData() {
        return data;
    }

    public void setData(IData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 添加viewHolder
     *
     * @param itemType
     * @param viewHolder
     */
    protected void addViewHolder(int itemType, Class<? extends BaseViewHolder<? extends IItem>> viewHolder) {
        this.viewHolders.put(itemType, viewHolder);
    }

    // ====================================================================================
    // 列表构造
    // ====================================================================================

    @Override
    public int getItemViewType(int position) {
        IItem item = getItem(position);
        if (item != null) {
            return item.getType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        int size = viewHolders.size();
        if (size != 0) {
            return size;
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public IItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // item
        IItem item = getItem(position);
        if (item == null) {
            return null;
        }

        // holder
        BaseViewHolder<IItem> holder;

        if (convertView == null) {
            holder = getHolder(item);

            if (holder != null) {
                convertView = holder.getView();
                convertView.setTag(holder);
            }

        } else {
            holder = (BaseViewHolder<IItem>) convertView.getTag();
        }

        if (holder == null) {
            return null;
        }

        // update itemView
        holder.convert(this, position, item);

        return convertView;
    }

    @SuppressWarnings({"unchecked"})
    private BaseViewHolder<IItem> getHolder(IItem item) {
        BaseViewHolder<IItem> holder = null;
        try {
            Class<? extends BaseViewHolder<? extends IItem>> holderClass = viewHolders.get(item.getType());
            holder = (BaseViewHolder<IItem>) holderClass.newInstance();
            holder.create(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder;
    }
}
