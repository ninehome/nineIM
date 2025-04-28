package com.watayouxiang.androidutils.page.easy;

import android.graphics.Color;

import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/29
 *     desc   : 浅色主题
 * </pre>
 */
public abstract class EasyLightActivity<T extends ViewDataBinding> extends EasyActivity<T> {
    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    protected Integer getStatusBarColor() {
        return Color.WHITE;
    }
}
