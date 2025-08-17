package com.tiocloud.chat.feature.main.adapter;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.VibrateUtils;
import com.tiocloud.chat.widget.PagerSlidingTabStrip;
import com.watayouxiang.androidutils.listener.SimpleOnPageChangeListener;

public class MainTabPagerAdapter extends BaseTabPagerAdapter implements PagerSlidingTabStrip.OnTabClickListener,
        PagerSlidingTabStrip.OnTabDoubleTapListener {

    public MainTabPagerAdapter(FragmentManager fm, ViewPager pager, Context context) {
        super(fm, context);
        // 缓存数量
        pager.setOffscreenPageLimit(getCacheCount());
        // 切换效果
        pager.setPageTransformer(true, new FadeInOutPageTransformer());
        // 页面切换监听
        pager.addOnPageChangeListener(new SimpleOnPageChangeListener(pager) {
            @Override
            public void onSelectShow(int position, int count, boolean isInit) {
                super.onSelectShow(position, count, isInit);
                getItem(position).onPageShow(count, isInit);
            }

            @Override
            public void onSelectHide(int position) {
                super.onSelectHide(position);
                getItem(position).onPageHide();
            }
        });
        pager.setAdapter(this);
    }

    // ==================================================================================
    // TAB 的点击、双击监听
    // ==================================================================================

    @Override
    public void onCurrentTabClicked(int position) {
        getItem(position).onTabClicked();
    }

    @Override
    public void onCurrentTabDoubleTap(int position) {
        getItem(position).onTabDoubleTap();
    }
}