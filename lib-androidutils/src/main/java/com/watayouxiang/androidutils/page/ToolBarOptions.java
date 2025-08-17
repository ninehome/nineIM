package com.watayouxiang.androidutils.page;

import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.blankj.utilcode.util.Utils;

public class ToolBarOptions {
    /**
     * 标题
     */
    private CharSequence title;
    /**
     * 副标题
     */
    private CharSequence subTitle;
    /**
     * logo
     */
    private Drawable logo;
    /**
     * 返回按钮
     */
    private Drawable navigationIcon;

    // ====================================================================================
    // 标题
    // ====================================================================================

    public CharSequence getTitle() {
        return title;
    }

    public ToolBarOptions setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public ToolBarOptions setTitle(int resId) {
        setTitle(Utils.getApp().getText(resId));
        return this;
    }

    // ====================================================================================
    // 副标题
    // ====================================================================================

    public CharSequence getSubTitle() {
        return subTitle;
    }

    public ToolBarOptions setSubTitle(CharSequence subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public ToolBarOptions setSubTitle(int resId) {
        setSubTitle(Utils.getApp().getText(resId));
        return this;
    }

    // ====================================================================================
    // logo
    // ====================================================================================

    public Drawable getLogo() {
        return logo;
    }

    public ToolBarOptions setLogo(Drawable logo) {
        this.logo = logo;
        return this;
    }

    public ToolBarOptions setLogo(int logoId) {
        setLogo(AppCompatResources.getDrawable(Utils.getApp(), logoId));
        return this;
    }

    // ====================================================================================
    // navigationIcon
    // ====================================================================================

    public Drawable getNavigationIcon() {
        return navigationIcon;
    }

    public ToolBarOptions setNavigationIcon(Drawable navigationIcon) {
        this.navigationIcon = navigationIcon;
        return this;
    }

    public ToolBarOptions setNavigationIcon(int resId) {
        setNavigationIcon(AppCompatResources.getDrawable(Utils.getApp(), resId));
        return this;
    }
}
