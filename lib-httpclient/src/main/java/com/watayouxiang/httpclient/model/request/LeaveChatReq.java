package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 *
 * @see String
 */
public class LeaveChatReq extends BaseReq<String> {

    @Override
    public String path() {
        return "/mytio/chat/leaveChat.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
