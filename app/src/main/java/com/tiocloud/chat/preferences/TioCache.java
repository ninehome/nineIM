package com.tiocloud.chat.preferences;

import com.tiocloud.chat.constant.TioConfig;

/**
 * author : TaoWang
 * date : 2020/5/8
 * desc :
 */
public class TioCache {

    // ====================================================================================
    // HAND_SHAKE_KEY
    // ====================================================================================

    private static String HAND_SHAKE_KEY = null;

    public static String getHandShakeKey() {
        if (HAND_SHAKE_KEY == null) {
            HAND_SHAKE_KEY = ConfigPreferences.getHandShakeKey();
        }
        if (HAND_SHAKE_KEY == null) {
            HAND_SHAKE_KEY = TioConfig.HAND_SHAKE_KEY_ONLINE;
        }
        return HAND_SHAKE_KEY;
    }

    // ====================================================================================
    // IN_CHAT_LINK_ID
    // ====================================================================================

    private static String IN_CHAT_LINK_ID = null;

    public static void setInChatLinkId(String inChatLinkId) {
        IN_CHAT_LINK_ID = inChatLinkId;
    }

    public static String getInChatLinkId() {
        return IN_CHAT_LINK_ID;
    }
}
