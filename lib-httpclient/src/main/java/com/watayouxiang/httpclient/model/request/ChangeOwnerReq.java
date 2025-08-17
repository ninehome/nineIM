package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ChangeOwnerResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/2
 * desc :
 */
public class ChangeOwnerReq extends BaseReq<ChangeOwnerResp> {
    private String otheruid;
    private String groupid;

    public ChangeOwnerReq(String otheruid, String groupid) {
        this.otheruid = otheruid;
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/chat/changeOwner.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("otheruid", otheruid)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ChangeOwnerResp>>() {
        }.getType();
    }
}
