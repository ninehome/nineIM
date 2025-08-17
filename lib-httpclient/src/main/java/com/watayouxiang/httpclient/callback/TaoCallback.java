package com.watayouxiang.httpclient.callback;

import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TaoCallback<T> extends AbsCallback<T> {
    private Type type;
    private Class<T> clazz;

    public TaoCallback() {
    }

    /**
     * 避免泛型擦除，使用示例：
     * Type type = new TypeToken<TioResp<ImServerResp>>() {}.getType();
     */
    public TaoCallback(Type type) {
        this.type = type;
    }

    public TaoCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        if (clazz != null) {
            TaoConvert<T> convert = new TaoConvert<>(clazz);
            return convert.convertResponse(response);
        }

        if (type == null) {
            // 返回直接继承的父类（包含泛型参数）
            // 有局限性，继承后就无法解析到
            // TioCallback<TioResp<Data>>
            Type genType = TaoCallback.this.getClass().getGenericSuperclass();
            // 获取第一个泛型参数
            // TioResp<Data>
            type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        }

        TaoConvert<T> convert = new TaoConvert<>(type);
        return convert.convertResponse(response);
    }
}
