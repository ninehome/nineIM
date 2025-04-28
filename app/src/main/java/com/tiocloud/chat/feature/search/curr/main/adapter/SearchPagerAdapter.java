package com.tiocloud.chat.feature.search.curr.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.feature.search.curr.main.model.PagerTab;
import com.watayouxiang.androidutils.listener.SimpleOnPageChangeListener;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {

    private final ViewPager pager;
    private PagerTab[] tabs = PagerTab.get();

    public SearchPagerAdapter(@NonNull FragmentManager fm, ViewPager pager) {
        super(fm);
        this.pager = pager;
        pager.setOffscreenPageLimit(getCacheCount());
        pager.addOnPageChangeListener(new SimpleOnPageChangeListener(pager) {
            @Override
            public void onScrollShow(int position, int count) {
                super.onScrollShow(position, count);
                getItem(position).load();
            }
        });
    }

    // ====================================================================================
    // FragmentPagerAdapter
    // ====================================================================================

    public int getCacheCount() {
        return tabs.length;
    }

    @NonNull
    @Override
    public BaseResultFragment getItem(int position) {
        return tabs[position].page;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position].title;
    }

    // ====================================================================================
    // public
    // ====================================================================================

    public <T extends Fragment> T getItem(Class<T> fragmentClass) {
        for (PagerTab tab : tabs) {
            Fragment fragment = tab.page;
            if (fragment.getClass().equals(fragmentClass)) {
                return (T) fragment;
            }
        }
        return null;
    }

    public void search(String keyWord) {
        int currentItem = pager.getCurrentItem();
        for (int i = 0; i < tabs.length; i++) {
            // 更新搜索词
            BaseResultFragment fragment = tabs[i].page;
            fragment.updateKeyWord(keyWord);
            // 加载当前所处页面
            if (currentItem == i) {
                fragment.load();
            }
        }
    }

}
