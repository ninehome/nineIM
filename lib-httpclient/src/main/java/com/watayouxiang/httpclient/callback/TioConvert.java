package com.watayouxiang.httpclient.callback;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.httpclient.utils.JsonUtils;

import java.io.Reader;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class TioConvert<Data> implements Converter<BaseResp<Data>> {

    private final Type type;

    /**
     * 避免泛型擦除，使用示例：
     * Type type = new TypeToken<TioResp<ImServerResp>>() {}.getType();
     */
    public TioConvert(Type type) {
        this.type = type;
    }

    @Override
    public BaseResp<Data> convertResponse(Response response) throws Throwable {
        if (response == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
//        LogUtils.e("body->"+body.string());
        //com.watayouxiang.httpclient.model.BaseResp<com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp>
//        if (true || type.getClass().getName() == BaseResp.class.getName()){
//            Reader reader = body.charStream();
//            Reader newReader = reader;
//            int temp = 0;
//            int len = 0 ;
//            char c[] = new char[1024] ;
//            while ((temp=newReader.read())!=-1){
//                c[len] = (char)temp ;
//                len++ ;
//            }
//            System.out.println("内容为：" + new String(c,0,len)) ;    // 把字符数组变为字符串输出
//        }
        JsonReader jsonReader = new JsonReader(body.charStream());
        BaseResp<Data> tioResp = JsonUtils.fromJson(jsonReader, type);
        response.close();
        System.out.println("fighting:resp:"+tioResp);
        return tioResp;
    }

}