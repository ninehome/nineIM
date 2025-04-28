package com.watayouxiang.httpclient.prefernces;

import android.text.TextUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/25
 *     desc   :
 * </pre>
 */
public class HttpCache {

    // ====================================================================================
    // BASE_URL
    // ====================================================================================

    private static final String TIO_BASE_URL = "http://47.114.183.215:6060";
    private static String BASE_URL = null;

    public synchronized static String getBaseUrl() {
        if (BASE_URL == null) {
            BASE_URL = HttpPreferences.getBaseUrl();
        }
        if (BASE_URL == null) {
            BASE_URL = TIO_BASE_URL;
        }
        return BASE_URL;
    }

    // ====================================================================================
    // RES_SERVER
    // ====================================================================================

    private static final String TIO_RES_URL = "https://res.tiocloud.com";
    //资源服务器地址
    private static String RES_URL = null;

    public synchronized static String getResUrl() {
        if (RES_URL == null) {
            RES_URL = HttpPreferences.getResUrl();
        }
        if (RES_URL == null) {
            RES_URL = TIO_RES_URL;
        }
        return RES_URL;
    }

    public synchronized static String getResUrl(String relativeUrl) {
        if (TextUtils.isEmpty(relativeUrl)) {
            return "";
        } else if (relativeUrl.startsWith("http")) {
            return relativeUrl;
        } else {
            return getResUrl() + relativeUrl;
        }
    }
}
