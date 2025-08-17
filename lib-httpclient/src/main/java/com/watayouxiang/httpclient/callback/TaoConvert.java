package com.watayouxiang.httpclient.callback;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class TaoConvert<T> implements Converter<T> {
    private Type type;
    private Class<T> clazz;

    public TaoConvert() {
    }

    /**
     * 避免泛型擦除，使用示例：
     * Type type = new TypeToken<TioResp<ImServerResp>>() {}.getType();
     */
    public TaoConvert(Type type) {
        this.type = type;
    }

    public TaoConvert(Class<T> aClass) {
        this.clazz = aClass;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        if (clazz != null) {
            return parseClass(response, clazz);
        }

        if (type == null) {
            // 返回直接继承的父类（包含泛型参数）
            // 有局限性，继承后就无法解析到
            Type genType = TaoConvert.this.getClass().getGenericSuperclass();
            // 获取第一个泛型参数
            type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<T>) type);
        } else {
            return parseType(response, type);
        }
    }

    /**
     * TioChat 定制
     *
     * @param response
     * @param type     TioResp<Data>
     * @return
     */
    private T parseParameterizedType(Response response, ParameterizedType type) {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;

//        try {
//            LogUtils.e("body==>"+body.string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        JsonReader jsonReader = new JsonReader(body.charStream());
        // 泛型的实际类型 TioResp
        Type rawType = type.getRawType();
        // 泛型的参数 Data
        Type typeArgument = type.getActualTypeArguments()[0];

        if (rawType == BaseResp.class) {
            if (typeArgument == Void.class) {
                // JsonCallback<TioResp<Void>>
                BaseResp<Void> tioResp = JsonUtils.fromJson(jsonReader, type);
                response.close();
                return (T) tioResp;
            } else {
                // JsonCallback<TioResp<内层JavaBean>>
                BaseResp<?> tioResp = JsonUtils.fromJson(jsonReader, type);
                response.close();
                return (T) tioResp;
            }
        } else {
            // JsonCallback<外层JavaBean<内层JavaBean>>
            T t = JsonUtils.fromJson(jsonReader, type);
            response.close();
            return t;
        }
    }

    private T parseClass(Response response, Class<T> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;

        if (rawType == String.class) {
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            return (T) new JSONArray(body.string());
        } else {
            JsonReader jsonReader = new JsonReader(body.charStream());
            T t = JsonUtils.fromJson(jsonReader, rawType);
            response.close();
            return t;
        }
    }

    private T parseType(Response response, Type type) {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;

        JsonReader jsonReader = new JsonReader(body.charStream());

        // JsonCallback<任意JavaBean>
        T t = JsonUtils.fromJson(jsonReader, type);
        response.close();
        return t;
    }
}
