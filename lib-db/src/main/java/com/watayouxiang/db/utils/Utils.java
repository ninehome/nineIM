package com.watayouxiang.db.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.db.prefernces.TioDBPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/02
 *     desc   :
 * </pre>
 */
public class Utils {
    /**
     * 时间戳 转 日期字符串
     *
     * @param time 时间戳
     * @return 日期字符串
     */
    @Nullable
    public static String long2DataString(long time) {
        return long2String(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间戳 转 字符串
     *
     * @param time    时间戳
     * @param pattern 模式
     * @return 字符串
     */
    @Nullable
    public static String long2String(long time, @NonNull String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            return format.format(new Date(time));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * large 是否包含 small
     */
    public static boolean contains(@Nullable String large, @Nullable String small) {
        if (large != null && small != null) {
            return large.contains(small);
        }
        return false;
    }

    /**
     * 获取 currUid
     */
    @Nullable
    public static String getCurrUid() {
        long uid = TioDBPreferences.getCurrUid();
        if (uid != -1) {
            return String.valueOf(uid);
        }
        return null;
    }

    /**
     * 快速创建列表
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... array) {
        ArrayList<E> list = new ArrayList<>();
        if (array == null || array.length == 0) return list;
        for (E e : array) {
            list.add(e);
        }
        return list;
    }
}
