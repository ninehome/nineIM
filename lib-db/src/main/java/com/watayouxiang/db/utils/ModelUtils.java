package com.watayouxiang.db.utils;

import com.google.gson.Gson;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/16
 *     desc   :
 * </pre>
 */
public class ModelUtils {

    /**
     * 把 modelA 对象的属性值赋值给 bClass 对象的属性。
     */
    public static <A, B> B modelA2B(A modelA, Class<B> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            return gson.fromJson(gsonA, bClass);
        } catch (Exception e) {
            return null;
        }
    }

}
