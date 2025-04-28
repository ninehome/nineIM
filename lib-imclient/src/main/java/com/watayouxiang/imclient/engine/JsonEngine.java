package com.watayouxiang.imclient.engine;

/**
 * author : TaoWang
 * date : 2020/4/27
 * desc : json解析引擎
 */
public interface JsonEngine {
    /**
     * 对象转字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    String object2String(Object obj);

    /**
     * 字符串转对象
     *
     * @param text  对象字符串
     * @param clazz 对象字节码
     * @param <T>   对象
     * @return 对象
     */
    <T> T string2Object(String text, Class<T> clazz);
}
