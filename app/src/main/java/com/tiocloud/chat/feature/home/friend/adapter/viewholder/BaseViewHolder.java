package com.tiocloud.chat.feature.home.friend.adapter.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;

import com.tiocloud.chat.feature.home.friend.adapter.BaseContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;

/**
 * date : 2020-01-16
 * desc :
 */
public abstract class BaseViewHolder<T extends IItem> {

    private View view;
    private Context context;

    public void create(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    /**
     * 布局id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 刷新 itemView
     *
     * @param adapter
     * @param position
     * @param item
     */
    public abstract void convert(BaseContactAdapter adapter, int position, T item);

    public View getView() {
        return view;
    }

    public Context getContext() {
        return context;
    }

    protected final <T extends View> T findViewById(@IdRes int id) {
        if (view == null)
            throw new NullPointerException("rootView is null");
        return view.findViewById(id);
    }
}
