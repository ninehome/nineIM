package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CollectEmotionListResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc :
 */
public class CollectEmotionListReq extends BaseReq<CollectEmotionListResp> {

    public CollectEmotionListReq() {
    }

    @Override
    public String path() {
        return "/mytio/collect/emotionList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<CollectEmotionListResp>>() {
        }.getType();
    }
}
