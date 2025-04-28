package com.tiocloud.chat.feature.session.common.action.model.base;

import java.io.Serializable;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 多功能item基类
 */
public abstract class BaseAction implements Serializable {
    /**
     * 图标resId
     */
    public int iconResId;

    /**
     * 标题stringId
     */
    public int titleId;

    public BaseAction(int iconResId, int titleId) {
        this.iconResId = iconResId;
        this.titleId = titleId;
    }

    /**
     * 点击事件
     */
    public abstract void onClick();
}
