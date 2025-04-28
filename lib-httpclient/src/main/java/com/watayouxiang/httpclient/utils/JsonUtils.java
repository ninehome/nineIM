package com.watayouxiang.httpclient.utils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

public class JsonUtils {
    private static Gson gson = new Gson();

    private static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
//        try {
//            String s = "";
//            while (reader.hasNext()){
//                s = s+reader.nextString();
//            }
//            LogUtils.e("ssss=>"+s);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return getGson().fromJson(reader, typeOfT);
    }
}
