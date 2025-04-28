package com.tiocloud.chat.feature.session.common.action.model.base;

import android.app.Activity;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   :
 * </pre>
 */
public abstract class BaseRedPaperAction extends BaseAction {
    public transient Activity activity;
    public transient List<String> chatLinkIds;

    public BaseRedPaperAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    @Override
    public void onClick() {

    }
}
