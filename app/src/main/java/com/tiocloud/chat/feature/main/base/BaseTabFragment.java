package com.tiocloud.chat.feature.main.base;

import com.tiocloud.chat.feature.main.model.MainTab;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.TioLogger;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
abstract class BaseTabFragment extends TioFragment {
    private final static String TAG = "BaseTabFragment";

    private MainTab tabData;
    private boolean currPageShow;

    // 绑定数据
    public void attachTabData(MainTab tab) {
        this.tabData = tab;
    }

    // 获取绑定数据
    public MainTab getTabData() {
        return tabData;
    }

    // 页面出现
    public void onPageShow(int count, boolean isInit) {
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onPageShow (count = %d, isInit = %s)", getClass().getSimpleName(), count, isInit));
        currPageShow = true;
        // 切换到当前页面 && 并非初始化 -> 刷新当前页面
        if (!isInit) {
            onRefresh();
        }
    }

    // 页面隐藏
    public void onPageHide() {
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onPageHide", getClass().getSimpleName()));
        currPageShow = false;
    }

    // 单击tab
    public void onTabClicked() {
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onTabClicked", getClass().getSimpleName()));
    }

    // 双击tab
    public void onTabDoubleTap() {
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onTabDoubleTap", getClass().getSimpleName()));
    }

    @Override
    public void onStart(int count) {
        super.onStart(count);
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onStart", getClass().getSimpleName()));
        // 页面返回前台 && 显示的是当前页面 -> 刷新当前页面
        if (count > 1 && currPageShow) {
            onRefresh();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        TioLogger.d(TAG, String.format(Locale.getDefault(), "%s # onStop", getClass().getSimpleName()));
    }

    /**
     * 刷新
     * <p>
     * 调用情况：页面返回前台、选中页面
     */
    protected void onRefresh() {
        TioLogger.i(TAG, String.format(Locale.getDefault(), "%s # onRefresh", getClass().getSimpleName()));
    }

    /**
     * Fragment 显示
     */
    protected void onFragmentShow() {

    }

    /**
     * Fragment 隐藏
     */
    protected void onFragmentHide() {

    }
}
