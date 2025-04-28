package com.watayouxiang.db.prefernces;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author : TaoWang
 * date : 2019-12-31
 * desc : SharedPreferences 工具类
 */
class PreferencesUtils {

    private static String NAME = "tio_db_preferences";
    private static SharedPreferences.Editor editor;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void init(Context context, String name) {
        mContext = context.getApplicationContext();
        NAME = name;
    }

    private static SharedPreferences getSharedPreferences() {
        if (mContext == null) {
            throw new NullPointerException("context is null");
        }
        return mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static Boolean getBoolean(String key, Boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    public static void saveBoolean(String key, Boolean value) {
        editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, String value) {
        return getSharedPreferences().getString(key, value);
    }

    public static void saveString(String key, String value) {
        editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    public static void saveInt(String key, int value) {
        editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static long getLong(String key, long value) {
        return getSharedPreferences().getLong(key, value);
    }

    public static void saveLong(String key, long value) {
        editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void saveFloat(String key, float value) {
        editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getFloat(String key, float value) {
        return getSharedPreferences().getFloat(key, value);
    }


}
