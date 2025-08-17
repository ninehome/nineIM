package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 *
 * @see String
 */
public class QueryAllNoticeReq extends BaseReq<Object> {

    public QueryAllNoticeReq() {
    }

    @Override
    public String path() {
        return "/mytio/chat/getLastAllNotice.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params();
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Object>>() {
        }.getType();
    }
}
