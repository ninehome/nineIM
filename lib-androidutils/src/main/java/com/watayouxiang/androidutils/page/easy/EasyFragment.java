package com.watayouxiang.androidutils.page.easy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public abstract class EasyFragment<T extends ViewDataBinding> extends TioFragment {
    protected T binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
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
