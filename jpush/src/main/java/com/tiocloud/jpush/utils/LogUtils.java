package com.tiocloud.jpush.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/23
 *     desc   :
 * </pre>
 */
public class LogUtils {
    private static final int LOG_MAX_LENGTH = 2 * 1024;

    private static String LOG_TAG = "TioPush";
    private static boolean DEBUG = true;
    private static boolean SHOW_THREAD_INFO = false;

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

    /**
     * 是否开启日志输出
     */
    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * 设置标签
     */
    public static void setTag(String logTag) {
        LOG_TAG = logTag;
    }

    /**
     * 是否显示线程信息
     */
    public static void setShowThreadInfo(boolean showThreadInfo) {
        SHOW_THREAD_INFO = showThreadInfo;
    }

    /**
     * 日志打印
     *
     * @param priority 日志类型
     * @param tag      日志标签
     * @param msg      日志信息
     */
    public static void log(@NonNull Priority priority, @NonNull String tag, @NonNull String msg) {
        if (!DEBUG) return;

        if (SHOW_THREAD_INFO) {
            msg = String.format(Locale.getDefault(), "%s || %s", Thread.currentThread().toString(), msg);
        }

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

    /**
     * 错误
     */
    public static void e(String msg) {
        log(Priority.ERROR, LOG_TAG, msg);
    }

    /**
     * 错误
     */
    public static void e(Throwable e) {
        String fullStackTrace = ThrowableUtils.getFullStackTrace(e);
        log(Priority.ERROR, LOG_TAG, fullStackTrace);
    }

    /**
     * 警告
     */
    public static void w(String msg) {
        log(Priority.WARN, LOG_TAG, msg);
    }

    /**
     * 调试
     */
    public static void d(String msg) {
        log(Priority.DEBUG, LOG_TAG, msg);
    }

    /**
     * 信息
     */
    public static void i(String msg) {
        log(Priority.INFO, LOG_TAG, msg);
    }
}
