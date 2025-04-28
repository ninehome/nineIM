package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.WxMyFriendsResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-16
 * desc :
 *
 * @see WxMyFriendsResp
 */
@Deprecated
public class WxMyFriendsReq extends BaseReq<WxMyFriendsResp> {
    private String pageNumber;

    public WxMyFriendsReq(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String path() {
        return "/mytio/friend/myFriends.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("pageNumber", pageNumber);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxMyFriendsResp>>() {
        }.getType();
    }
}
