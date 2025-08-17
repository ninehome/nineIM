package com.watayouxiang.imclient.utils;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/02
 *     desc   :
 * </pre>
 */
public class StringUtils {

    /**
     * 获取对象的值
     *
     * @param obj          对象
     * @param objNullValue 对象为空时的值
     * @return 对象的值
     */
    public static String valueOf(Object obj, @NonNull String objNullValue) {
        String value;
        if (obj != null) {
            value = obj.toString();
        } else {
            value = objNullValue;
        }
        return value;
    }

}
