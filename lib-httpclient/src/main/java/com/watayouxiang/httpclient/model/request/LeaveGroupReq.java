package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/28
 * desc : 主动退群
 *
 * @see String
 */
public class LeaveGroupReq extends BaseReq<String> {
    private String uid;
    private String groupid;

    public LeaveGroupReq(String uid, String groupid) {
        this.uid = uid;
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/chat/leaveGroup.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("uid", uid)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
