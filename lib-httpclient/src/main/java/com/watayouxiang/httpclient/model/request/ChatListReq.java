package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc :
 */
public class ChatListReq extends BaseReq<ChatListResp> {
    @Override
    public String path() {
        return "/mytio/chat/list.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ChatListResp>>() {
        }.getType();
    }
}
