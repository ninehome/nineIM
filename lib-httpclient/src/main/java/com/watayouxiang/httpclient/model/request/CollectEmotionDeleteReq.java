package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CollectEmotionListResp;
import com.watayouxiang.httpclient.model.response.CommonResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc :
 */
public class CollectEmotionDeleteReq extends BaseReq<CommonResp> {
    int emotionId;
    public CollectEmotionDeleteReq(int emotionId) {
        this.emotionId = emotionId;
    }

    @Override
    public String path() {
        return "/mytio/collect/deleteEmotion.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("emotionId", String.valueOf(emotionId))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<CommonResp>>() {
        }.getType();
    }
}
