package com.watayouxiang.httpclient.prefernces;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/25
 *     desc   : 配置文件
 * </pre>
 */
public class HttpPreferences extends PreferencesUtils {

    private static final String KEY_BASE_URL = "base_url";
    private static final String KEY_RES_URL = "res_url";

    private static final String KEY_GETWAY_ID = "gate_id";
    private static final String KEY_SESSION_COOKIE_NAME = "session_cookie_name";

    // ====================================================================================
    // baseUrl
    // ====================================================================================

    public static void saveBaseUrl(String baseUrl) {
        saveString(KEY_BASE_URL, baseUrl);
    }

    public static void saveGatewayId(String id) {
        saveString(KEY_GETWAY_ID, id);
    }

    public static String getGatewayId() {
        return getString(KEY_GETWAY_ID, "0");
    }
    public static String getBaseUrl() {
        return getString(KEY_BASE_URL, null);
    }

    // ====================================================================================
    // resUrl
    // ====================================================================================

    public static void saveResUrl(String resServer) {
        saveString(KEY_RES_URL, resServer);
    }

    public static String getResUrl() {
        return getString(KEY_RES_URL, null);
    }

    // ====================================================================================
    // sessionCookieName
    // ====================================================================================

    public static void saveSessionCookieName(String sessionCookieName) {
        saveString(KEY_SESSION_COOKIE_NAME, sessionCookieName);
    }

    public static String getSessionCookieName() {
        return getString(KEY_SESSION_COOKIE_NAME, null);
    }

}
