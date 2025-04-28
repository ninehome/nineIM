package com.watayouxiang.androidutils.page.easy;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.watayouxiang.androidutils.page.TioActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/28
 *     desc   :
 * </pre>
 */
public abstract class EasyActivity<T extends ViewDataBinding> extends TioActivity {

    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置window背景色
        Integer backgroundColor = getBackgroundColor();
        if (backgroundColor != null) {
            setBackgroundColor(backgroundColor);
        }
        // 绑定布局
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        // 状态栏
        View statusBarHolder = getStatusBarHolder();
        if (statusBarHolder != null) {
            // 状态栏占位
            addMarginTopEqualStatusBarHeight(statusBarHolder);
            // 状态栏颜色
            Integer statusBarColor = getStatusBarColor();
            if (statusBarColor != null) {
                setStatusBarColor(statusBarColor);
            }
            // 状态栏模式
            Boolean statusBarLightMode = getStatusBarLightMode();
            if (statusBarLightMode != null) {
                setStatusBarLightMode(statusBarLightMode);
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }

    // ====================================================================================
    // background
    // ====================================================================================

    @ColorInt
    protected Integer getBackgroundColor() {
        return null;
    }

    // ====================================================================================
    // statusBar
    // ====================================================================================

    /**
     * true: LightMode(浅色) 状态栏字体为黑
     * false: DarkMode(深色) 状态栏字体为白
     */
    protected Boolean getStatusBarLightMode() {
        return null;
    }

    @ColorInt
    protected Integer getStatusBarColor() {
        return null;
    }

    protected View getStatusBarHolder() {
        return null;
    }

    // ====================================================================================
    // contentView
    // ====================================================================================

    @LayoutRes
    protected abstract int getContentViewId();

    @NonNull
    public T getBinding() {
        return binding;
    }

}
