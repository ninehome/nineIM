package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 非验证加好友
 */
public class AddFriendReq extends BaseReq<AddFriendResp> {
    private String touid;

    public AddFriendReq(String touid) {
        this.touid = touid;
    }

    @Override
    public String path() {
        return "/mytio/chat/addFriend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("touid", touid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<AddFriendResp>>() {
        }.getType();
    }
}
