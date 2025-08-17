package com.tiocloud.chat.util;

import android.net.Uri;
import android.os.Bundle;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.tiocloud.chat.feature.splash.SplashActivity;
import com.watayouxiang.androidutils.util.TioLogger;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/27
 *     desc   :
 * </pre>
 */
public class BadgeUtils {

    public static void setBadge(int count) {
        if (count < 0) count = 0;
        TioLogger.d("设置华为角标：" + count);
        setHuaweiBadge(count);
    }

    private static void setHuaweiBadge(int count) {
        // https://developer.huawei.com/consumer/cn/doc/development/system-Guides/30802
        try {
            Bundle extra = new Bundle();
            extra.putString("package", AppUtils.getAppPackageName());
            extra.putString("class", SplashActivity.class.getCanonicalName());
            extra.putInt("badgenumber", count);
            Uri parse = Uri.parse("content://com.huawei.android.launcher.settings/badge/");
            Utils.getApp().getContentResolver().call(parse, "change_badge", null, extra);
        } catch (Exception ignored) {
        }
    }
}
