package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ActChatResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class ActChatReq extends BaseReq<ActChatResp> {
    private String touid;
    private String groupid;

    private ActChatReq(String touid, String groupid) {
        this.touid = touid;
        this.groupid = groupid;
    }

    public static ActChatReq getP2P(String touid) {
        return new ActChatReq(touid, null);
    }

    public static ActChatReq getGroup(String groupid) {
        return new ActChatReq(null, groupid);
    }

    @Override
    public String path() {
        return "/mytio/chat/actChat.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("touid", touid)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ActChatResp>>() {
        }.getType();
    }
}
