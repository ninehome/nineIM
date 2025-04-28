package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 * <p>
 * 响应体是int类型
 * {"data":1,"ok":true}
 */
public class ApplyDataReq extends BaseReq<Integer> {
    @Override
    public String path() {
        return "/mytio/chat/applyData.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }
}
