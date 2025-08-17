package com.watayouxiang.androidutils.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.watayouxiang.androidutils.feature.TioBrowserActivity;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc :
 */
public class BrowserUtils {
    /**
     * 用默认浏览器打开一个链接
     *
     * @param activity Activity
     * @param url      链接
     */
    public static void openOsBrowser(Activity activity, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    /**
     * 用本地浏览器打开一个链接
     *
     * @param activity Activity
     * @param url      链接
     */
    public static void openBrowserActivity(Activity activity, String url) {
        TioBrowserActivity.start(activity, url);
    }
}
