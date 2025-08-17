package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 *
 * @see String
 */
public class OnFocusReq extends BaseReq<String> {
    private String chatlinkid;

    public OnFocusReq(String chatlinkid) {
        this.chatlinkid = chatlinkid;
    }

    @Override
    public String path() {
        return "/mytio/chat/onFocus.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("chatlinkid", chatlinkid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
