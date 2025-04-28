package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 申请列表
 */
public class ApplyListReq extends BaseReq<ApplyListResp> {
    @Override
    public String path() {
        return "/mytio/chat/applyList.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ApplyListResp>>() {
        }.getType();
    }
}
