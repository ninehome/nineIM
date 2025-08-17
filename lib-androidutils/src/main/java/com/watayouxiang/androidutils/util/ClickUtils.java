package com.watayouxiang.androidutils.util;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 11/20/20
 *     desc   :
 * </pre>
 */
public final class ClickUtils {
    /**
     * 视图单击标签
     */
    private static final int VIEW_SINGLE_CLICK_TAG = -7;
    /**
     * 默认时间间隔
     */
    private static final long DEFAULT_TIME_INTERVAL = 1000;
    /**
     * 上一次点击的时间
     */
    private static long mGlobalPreClickTime;

    /**
     * 是否全局单击
     *
     * @param intervalMillis 时间间隔（毫秒）
     * @return true 是，false 否
     */
    public static boolean isGlobalSingleClick(final long intervalMillis) {
        long curTime = System.currentTimeMillis();
        if (curTime - mGlobalPreClickTime < intervalMillis) {
            // 双击
            return false;
        } else {
            // 单击
            mGlobalPreClickTime = curTime;
            return true;
        }
    }

    public static boolean isGlobalSingleClick() {
        return isGlobalSingleClick(DEFAULT_TIME_INTERVAL);
    }

    /**
     * 是否单击视图
     *
     * @param view           视图
     * @param intervalMillis 时间间隔（毫秒）
     * @return true 是，false 否
     */
    public static boolean isViewSingleClick(@NonNull final View view, final long intervalMillis) {
        long curTime = System.currentTimeMillis();
        Object tag = view.getTag(VIEW_SINGLE_CLICK_TAG);
        if (!(tag instanceof Long)) {
            view.setTag(VIEW_SINGLE_CLICK_TAG, curTime);
            return true;
        }
        long preTime = (Long) tag;
        if (curTime - preTime < 0) {
            view.setTag(VIEW_SINGLE_CLICK_TAG, curTime);
            return false;
        } else if (curTime - preTime <= intervalMillis) {
            return false;
        }
        view.setTag(VIEW_SINGLE_CLICK_TAG, curTime);
        return true;
    }

    public static boolean isViewSingleClick(@NonNull final View view) {
        return isViewSingleClick(view, DEFAULT_TIME_INTERVAL);
    }
}