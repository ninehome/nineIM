package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/15
 *     desc   :
 * </pre>
 */
public class CollectEmotionReq extends BaseReq<String> {
    private String url;
    private int width;
    private int height;

    public CollectEmotionReq(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/collect/addEmotion.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("url", url)
                .append("width", width+"")
                .append("height", height+"")
                ;
    }
}
