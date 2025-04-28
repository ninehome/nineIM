package com.watayouxiang.androidutils.widget;

import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 来源 https://github.com/Dovar66/DToast
 */
public class TioToast {

    public static void showShort(@Nullable CharSequence msg) {
        ToastUtils.showShort(msg);
    }

    public static void showShort(@StringRes final int resId) {
        ToastUtils.showShort(resId);
    }

    public static void showLong(@Nullable CharSequence msg) {
        ToastUtils.showLong(msg);
    }

    public static void showLong(@StringRes final int resId) {
        ToastUtils.showLong(resId);
    }

    /**
     * @see TioToast#showShort(CharSequence)
     */
    @Deprecated
    public static void show(Activity activity, @Nullable CharSequence msg) {
        showShort(msg);
    }

    /**
     * @see TioToast#showLong(CharSequence)
     */
    @Deprecated
    public static void showLong(Activity activity, @Nullable CharSequence msg) {
        showLong(msg);
    }

}
