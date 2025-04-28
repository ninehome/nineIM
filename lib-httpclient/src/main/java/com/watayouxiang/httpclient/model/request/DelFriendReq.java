package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 * <p>
 * "data" : "操作成功"
 *
 * @see String
 */
public class DelFriendReq extends BaseReq<String> {
    private String touid;

    private boolean forceDel = false;

    public void setForceDel(boolean forceDel) {
        this.forceDel = forceDel;
    }

    public DelFriendReq(String touid) {
        this.touid = touid;
    }

    @Override
    public String path() {
        return "/mytio/chat/delFriend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("touid", touid)
                .append("isManagerDel", forceDel ? "1":"0")
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
