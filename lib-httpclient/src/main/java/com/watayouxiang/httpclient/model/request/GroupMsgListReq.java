package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.GroupMsgListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-18
 * desc :
 *
 * @see GroupMsgListResp
 */
public class GroupMsgListReq extends BaseReq<GroupMsgListResp> {

    private final String chatlinkid;
    private final String startmid;

    public GroupMsgListReq(String chatlinkid, String startmid) {
        this.chatlinkid = chatlinkid;
        this.startmid = startmid;
    }

    @Override
    public String path() {
        return "/mytio/chat/groupMsgList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("chatlinkid", chatlinkid)
                .append("startmid", startmid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<GroupMsgListResp>>() {
        }.getType();
    }
}
