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
public class QueryOnlineReq extends BaseReq<Object> {
    private String uids;

    public QueryOnlineReq(String uids) {
        this.uids = uids;
    }

    @Override
    public String path() {
        return "/mytio/chat/queryOnline.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("uids", uids);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Object>>() {
        }.getType();
    }
}
