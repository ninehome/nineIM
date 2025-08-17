package com.tiocloud.chat.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;

import java.util.List;

public class StringUtil {

    /**
     * 删除字符串中的空白符
     *
     * @param content 文本
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 计算字符串长度
     *
     * @param str 字符串
     * @return 长度
     */
    public static int counterLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        return str.length();
    }

    /**
     * 获取非空的字符串
     *
     * @param txt
     * @return
     */
    public static String nonNull(CharSequence txt) {
        return txt != null ? String.valueOf(txt) : "";
    }

    public static String nonNull(Object txt) {
        return txt != null ? String.valueOf(txt) : "";
    }

    /**
     * 如果 txt 为空，则显示 emptyShow
     *
     * @param txt
     * @param emptyShow
     * @return
     */
    public static String emptyShow(String txt, @NonNull String emptyShow) {
        return !TextUtils.isEmpty(txt) ? txt : emptyShow;
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param word1
     * @param word2
     * @return
     */
    public static boolean equals(@Nullable String word1, @Nullable String word2) {
        if (word1 == null) {
            return word2 == null;
        }
        return word1.equals(word2);
    }

    /**
     * "" 转 null
     *
     * @param str
     * @return
     */
    public static String emptyString2null(String str) {
        return TextUtils.isEmpty(str) ? null : str;
    }

    @Nullable
    public static String list2String(@Nullable List<String> list) {
        return list2String(list, ",");
    }

    /**
     * 列表转字符串，中间用 splitTag 分割
     *
     * @param list
     * @param splitTag
     * @return
     */
    @Nullable
    public static String list2String(@Nullable List<String> list, String splitTag) {
        if (list == null || list.size() == 0)
            return null;

        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            builder.append(list.get(i));
            if (i < size - 1) builder.append(splitTag);
        }
        return builder.toString();
    }

    /**
     * 复制文本到粘贴板
     *
     * @param text
     */
    public static void copyText(CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
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
}
