package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/14
 * desc :
 */
public class CheckSendCardReq extends BaseReq<Void> {
    private String groupid;

    public CheckSendCardReq(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("groupid", groupid);
    }

    @Override
    public String path() {
        return "/mytio/chat/checkSendCard.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
