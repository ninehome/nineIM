package com.tiocloud.chat.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tiocloud.chat.R;

public class YxRefreshLayout extends SmartRefreshLayout {
    public static void init() {
// 指定全局的下拉Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new BCRefreshHeader(context);
            }
        });

        // 指定全局的上拉Footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });

    }

    public YxRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public YxRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setReboundDuration(300); // 设置回弹动画时长
        setPrimaryColorsId(R.color.theme_color);  // 主题色
        setEnableAutoLoadMore(false); // 设置是否监听列表在滚动到底部时触发加载事件
    }

    // 下拉/上拉完成
    public void complete() {
        if (mState == RefreshState.Loading) {
            finishLoadMore();
        } else {
            finishRefresh();
        }
    }
}
