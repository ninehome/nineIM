package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 * 返回是一个 Integer: 验证标识：1:需要申请验证；2:无条件
 */
public class CheckAddFriendReq extends BaseReq<Integer> {
    private String touid;

    public CheckAddFriendReq(String touid) {
        this.touid = touid;
    }

    @Override
    public String path() {
        return "/mytio/chat/checkAddFriend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("touid", touid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }
}
