package com.tiocloud.chat.feature.main.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.feature.main.fragment.Nav1Fragment;
import com.tiocloud.chat.feature.main.model.MainTab;
import com.tiocloud.jpush.utils.LogUtils;

import java.util.List;

abstract class BaseTabPagerAdapter extends FragmentPagerAdapter {

    private final MainTabFragment[] fragments;
    private final Context context;

    BaseTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = new MainTabFragment[MainTab.values().length];
        int i = 0;
        for (MainTab tab : MainTab.values()) {
//            LogUtils.e("fragments=======================tab>"+tab.tabIndex);
//            if (tab.tabIndex == MainTab.GROUP.tabIndex && !showWebTab()){
////                LogUtils.e("fragments=============nav==========tab>不显示");
//                continue;
//            }
            try {
                // 获取已存在的 fragment
                MainTabFragment fragment = null;
                List<Fragment> fs = fm.getFragments();
                for (Fragment f : fs) {
                    if (f.getClass() == tab.clazz) {
                        fragment = (MainTabFragment) f;
                        break;
                    }
                }
                // 不存在则创建
                if (fragment == null) {
                    fragment = tab.clazz.newInstance();
                }
                // 绑定数据
                Log.e("zlb", ""+fragment.getClass().getName()+" "+tab.clazz.getName()+" "+tab.layoutId);
                fragment.attachTabData(tab);
                // 存储到
                fragments[i++] = fragment;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        LogUtils.e("fragments============================>"+fragments.length);
    }

    // ====================================================================================
    // getter
    // ====================================================================================

    public int getImageResource(int position) {
        MainTab tab = MainTab.fromTabIndex(showWebTab()||position<2?position:position+1);
        return tab == null ? 0 : tab.iconId;
    }

    public boolean showWebTab(){
        return Nav1Fragment.findItems != null && Nav1Fragment.findItems.size() > 0;
    }

    public int getCacheCount() {
        return MainTab.values().length - ( showWebTab() ? 0 : 1 );
    }

    public Context getContext() {
        return context;
    }

    // ====================================================================================
    // FragmentPagerAdapter
    // ====================================================================================

    @NonNull
    @Override
    public MainTabFragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return MainTab.values().length- ( showWebTab() ? 0 : 1 );
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MainTab tab = MainTab.fromTabIndex(showWebTab()||position<2?position:position+1);
        return tab == null ? "" : tab.titleId == R.string.my_name ? TioConfig.OpenCloseConfig.getSitename() : getContext().getText(tab.titleId);
    }

}