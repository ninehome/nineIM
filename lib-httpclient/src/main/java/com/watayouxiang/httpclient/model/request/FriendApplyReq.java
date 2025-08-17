package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 *
 * @see FriendApplyResp
 */
public class FriendApplyReq extends BaseReq<FriendApplyResp> {
    private String touid;
    private String greet;

    public FriendApplyReq(String touid, String greet) {
        this.touid = touid;
        this.greet = greet;
    }

    @Override
    public String path() {
        return "/mytio/chat/friendApply.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("touid", touid)
                .append("greet", greet)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<FriendApplyResp>>() {
        }.getType();
    }
}
