package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 * <p>
 * data是Integer类型
 * 验证标识：1:是好友；2:不是
 */
public class IsFriendReq extends BaseReq<Integer> {
    private String touid;

    public IsFriendReq(String touid) {
        this.touid = touid;
    }

    @Override
    public String path() {
        return "/mytio/chat/isFriend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("touid", touid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }
}
