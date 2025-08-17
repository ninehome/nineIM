package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 *
 * @see GroupInfoResp
 */
public class GroupInfoReq extends BaseReq<GroupInfoResp> {
    /**
     * 查询自己的用户信息标识：1：是；2：否
     */
    private final String userflag;
    private final String groupid;

    public GroupInfoReq(String userflag, String groupid) {
        this.userflag = userflag;
        this.groupid = groupid;
    }

    @Override
    public String path() {
            return "/mytio/chat/group.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("userflag", userflag)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<GroupInfoResp>>() {
        }.getType();
    }
}
