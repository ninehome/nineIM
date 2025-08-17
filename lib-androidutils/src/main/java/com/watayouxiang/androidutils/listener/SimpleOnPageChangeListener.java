package com.watayouxiang.androidutils.listener;

import androidx.viewpager.widget.ViewPager;

import java.util.TreeMap;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class SimpleOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private static final String TAG = "EnhancePageChangeListener";

    private final ViewPager pager;

    // 滚动模式下：key 页面位置, value 页面出现次数
    private final TreeMap<Integer, Integer> mScrollShowCountMap = new TreeMap<>();
    // 滚动模式下：上次页面所在位置
    private int mScrollLastPosition = -1;

    // 选中模式下：key 页面位置, value 页面出现次数
    private final TreeMap<Integer, Integer> mSelectShowCountMap = new TreeMap<>();
    // 选中模式下：上次页面所在位置
    private int mSelectLastPosition = -1;

    public SimpleOnPageChangeListener(ViewPager pager) {
        this.pager = pager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 初始化，选中模式，出现
        if (mSelectLastPosition == -1) {
            mSelectLastPosition = position;
            onSelectShowInternal(position, true);
        }
        // 初始化，滚动模式，出现
        if (mScrollLastPosition == -1) {
            mScrollLastPosition = position;
            onScrollShowInternal(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            // 滚动隐藏
            if (mScrollLastPosition != -1) {
                onScrollHide(mScrollLastPosition);
            }
            // 滚动出现
            int currentItem = pager.getCurrentItem();
            onScrollShowInternal(currentItem);
            // 记录上次位置
            mScrollLastPosition = currentItem;
        }
    }

    @Override
    public void onPageSelected(int position) {
        // 选中隐藏
        if (mSelectLastPosition != -1) {
            onSelectHide(mSelectLastPosition);
        }
        // 选中出现
        onSelectShowInternal(position, false);
        // 记录上次位置
        mSelectLastPosition = position;
    }

    // ====================================================================================
    // 滚动模式
    // ====================================================================================

    private void onScrollShowInternal(int position) {
        // 统计每个页面出现次数
        Integer count = mScrollShowCountMap.get(position);
        if (count == null) {
            mScrollShowCountMap.put(position, 1);
        } else {
            mScrollShowCountMap.put(position, ++count);
        }
        // 获取该页面出现次数
        onScrollShow(position, getScrollShowCount(position));
    }

    /**
     * 获取显示次数
     *
     * @param position 位置
     * @return "滚动模式" 下的显示次数
     */
    public int getScrollShowCount(int position) {
        Integer count = mScrollShowCountMap.get(position);
        if (count == null) count = 0;
        return count;
    }

    /**
     * 滚动 - 显示
     *
     * @param position 位置
     * @param count    页面出现的次数
     */
    public void onScrollShow(int position, int count) {

    }

    /**
     * 滚动 - 隐藏
     *
     * @param position 位置
     */
    public void onScrollHide(int position) {

    }

    // ====================================================================================
    // 选中模式
    // ====================================================================================

    private void onSelectShowInternal(int position, boolean isInit) {
        // 统计每个页面出现次数
        Integer count = mSelectShowCountMap.get(position);
        if (count == null) {
            mSelectShowCountMap.put(position, 1);
        } else {
            mSelectShowCountMap.put(position, ++count);
        }
        // 获取该页面出现次数
        onSelectShow(position, getSelectShowCount(position), isInit);
    }

    /**
     * 获取显示次数
     *
     * @param position 位置
     * @return "选中模式" 下的显示次数
     */
    public int getSelectShowCount(int position) {
        Integer count = mSelectShowCountMap.get(position);
        if (count == null) count = 0;
        return count;
    }

    /**
     * 选中 - 显示
     *
     * @param position 位置
     * @param count    页面出现的次数
     * @param isInit   是否为初始化
     */
    public void onSelectShow(int position, int count, boolean isInit) {

    }

    /**
     * 选中 - 隐藏
     *
     * @param position 位置
     */
    public void onSelectHide(int position) {

    }
}
