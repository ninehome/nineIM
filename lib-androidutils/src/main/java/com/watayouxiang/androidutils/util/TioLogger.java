package com.watayouxiang.androidutils.util;

import android.util.Log;

import com.watayouxiang.androidutils.BuildConfig;

/**
 * author : TaoWang
 * date : 2019-12-31
 * desc : 日志类
 */
public class TioLogger {
    private static boolean isLogEnable = BuildConfig.DEBUG;

    private static final String tag = "TioApp";

    public static void setIsLogEnable(boolean isLogEnable) {
        TioLogger.isLogEnable = isLogEnable;
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isLogEnable) Log.v(tag, msg);
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isLogEnable) Log.d(tag, msg);
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isLogEnable) Log.i(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isLogEnable) Log.w(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isLogEnable) Log.e(tag, msg);
    }

    public static void printStackTrace(Throwable t) {
        if (isLogEnable && t != null) t.printStackTrace();
    }
}
