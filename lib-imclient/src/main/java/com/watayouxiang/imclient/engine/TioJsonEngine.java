package com.watayouxiang.imclient.engine;

import com.google.gson.Gson;

public class TioJsonEngine implements JsonEngine {
    private static Gson gson = new Gson();

    private static Gson getGson() {
        return gson;
    }

    /**
     * 对象转字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    @Override
    public String object2String(Object obj) {
        if (obj != null) {
            return getGson().toJson(obj);
        }
        return null;
    }

    /**
     * 字符串转对象
     *
     * @param text  对象字符串
     * @param clazz 对象字节码
     * @param <T>   对象
     * @return 对象
     */
    @Override
    public <T> T string2Object(String text, Class<T> clazz) {
        if (text != null && clazz != null) {
            return getGson().fromJson(text, clazz);
        }
        return null;
    }
}
