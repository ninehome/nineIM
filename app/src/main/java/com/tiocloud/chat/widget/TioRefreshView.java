package com.tiocloud.chat.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc :
 */
public class TioRefreshView extends SwipeRefreshLayout {

    public TioRefreshView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public TioRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    @Override
    public void setOnRefreshListener(@Nullable OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}
