package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.WxChatListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc :
 *
 * @see WxChatListResp
 */
@Deprecated
public class WxChatListReq extends BaseReq<WxChatListResp> {
    private String groupid;
    private String startMid;

    public WxChatListReq(String groupid) {
        this(groupid, null);
    }

    public WxChatListReq(String groupid, String startMid) {
        this.groupid = groupid;
        this.startMid = startMid;
    }

    @Override
    public String path() {
        return "/mytio/group/queryChatList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("groupid", groupid)
                .append("startMid", startMid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxChatListResp>>() {
        }.getType();
    }
}
