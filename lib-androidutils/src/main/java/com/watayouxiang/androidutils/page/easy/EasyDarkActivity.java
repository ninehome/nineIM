package com.watayouxiang.androidutils.page.easy;

import android.graphics.Color;

import androidx.databinding.ViewDataBinding;

import com.watayouxiang.androidutils.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/29
 *     desc   : 深色主题
 * </pre>
 */
public abstract class EasyDarkActivity<T extends ViewDataBinding> extends EasyActivity<T> {
    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return false;
    }

    @Override
    public Integer getStatusBarColor() {
        return getResources().getColor(R.color.white);
    }
}
