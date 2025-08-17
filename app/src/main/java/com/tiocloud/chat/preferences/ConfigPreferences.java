package com.tiocloud.chat.preferences;

import com.tiocloud.chat.util.PreferencesUtil;

/**
 * author : TaoWang
 * date : 2020/2/28
 * desc :
 */
public class ConfigPreferences {

    private static final String KEY_IM_HEARTBEAT_TIMEOUT = "im_heartbeat_timeout";
    private static final String KEY_HAND_SHAKE_KEY = "hand_shake_key";
    private static final String KEY_AGREE_PROTECT_GUIDE = "agree_protect_guide";



    // ====================================================================================
    // sessionCookieName
    // ====================================================================================

    public static void saveImHeartbeatTimeout(long im_heartbeat_timeout) {
        PreferencesUtil.saveLong(KEY_IM_HEARTBEAT_TIMEOUT, im_heartbeat_timeout);
    }

    public static long getImHeartbeatTimeout() {
        return PreferencesUtil.getLong(KEY_IM_HEARTBEAT_TIMEOUT, -1);
    }

    // ====================================================================================
    // handShakeKey
    // ====================================================================================

    public static void saveHandShakeKey(String handShakeKey) {
        PreferencesUtil.saveString(KEY_HAND_SHAKE_KEY, handShakeKey);
    }

    public static String getHandShakeKey() {
        return PreferencesUtil.getString(KEY_HAND_SHAKE_KEY, null);
    }

    // ====================================================================================
    // AgreeProtectGuide
    // ====================================================================================

    public static void saveAgreeProtectGuide(boolean agreeProtectGuide) {
        PreferencesUtil.saveBoolean(KEY_AGREE_PROTECT_GUIDE, agreeProtectGuide);
    }

    public static boolean getAgreeProtectGuide() {
        return PreferencesUtil.getBoolean(KEY_AGREE_PROTECT_GUIDE, false);
    }
}
