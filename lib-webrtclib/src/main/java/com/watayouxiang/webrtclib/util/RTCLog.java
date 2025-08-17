package com.watayouxiang.webrtclib.util;

import android.util.Log;

/**
 * author : TaoWang
 * date : 2020/4/23
 * desc :
 */
public class RTCLog {
    private static final String LOG_TAG = "TioWebRTC";
    private static final int LOG_MAX_LENGTH = 2 * 1024;
    private static boolean DEBUG = true;

    public enum Priority {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        ASSERT(7);
        int value;

        Priority(int value) {
            this.value = value;
        }
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void w(String msg) {
        log(Priority.WARN, msg);
    }

    public static void w(String tag, String msg) {
        log(Priority.WARN, tag, msg);
    }

    public static void e(String msg) {
        log(Priority.ERROR, msg);
    }

    public static void e(String tag, String msg) {
        log(Priority.ERROR, tag, msg);
    }

    public static void i(String msg) {
        log(Priority.INFO, msg);
    }

    public static void i(String tag, String msg) {
        log(Priority.INFO, tag, msg);
    }

    public static void d(String msg) {
        log(Priority.DEBUG, msg);
    }

    public static void d(String tag, String msg) {
        log(Priority.DEBUG, tag, msg);
    }

    public static void log(Priority priority, String msg) {
        log(priority, LOG_TAG, msg);
    }

    public static void log(Priority priority, String tag, String msg) {
        if (!DEBUG) return;
        if (priority == null) return;
        if (tag == null) return;
        if (msg == null) return;

        int msgLen = msg.length();
        // 一次性显示
        if (msgLen <= LOG_MAX_LENGTH) {
            Log.println(priority.value, tag, msg);
            return;
        }
        // 分段显示
        for (int i = 0; i < msgLen; i += LOG_MAX_LENGTH) {
            String substring;
            if (i + LOG_MAX_LENGTH < msgLen) {
                substring = msg.substring(i, i + LOG_MAX_LENGTH);
            } else {
                substring = msg.substring(i);
            }
            if (i == 0) {
                Log.println(priority.value, tag, substring);
            } else {
                Log.println(priority.value, tag, "\t\t" + substring);
            }
        }
    }
}
