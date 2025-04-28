package com.watayouxiang.androidutils.util;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/17
 *     desc   :
 * </pre>
 */
public class HtmlUtils {
    /**
     * html转义
     *
     * @param input HTML
     * @return String
     */
    public static String escapeHtml(final String input) {
//        return StringEscapeUtils.escapeHtml4(input);
        return input;
    }

    /**
     * html反转义
     *
     * @param input String
     * @return HTML
     */
    public static String unescapeHtml(final String input) {
        return StringEscapeUtils.unescapeHtml4(input);
    }
}
