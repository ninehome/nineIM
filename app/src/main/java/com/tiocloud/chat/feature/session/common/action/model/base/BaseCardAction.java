package com.tiocloud.chat.feature.session.common.action.model.base;

import android.app.Activity;
import android.content.Intent;

import com.tiocloud.chat.feature.session.common.model.SessionType;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/4/13
 * desc : 名片
 */
public abstract class BaseCardAction extends BaseAction {
    public transient Activity activity;
    public transient List<String> chatLinkIds;
    public transient SessionType sessionType;

    public BaseCardAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
