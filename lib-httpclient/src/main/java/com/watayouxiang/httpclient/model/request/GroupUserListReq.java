package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 群人员列表
 */
public class GroupUserListReq extends BaseReq<GroupUserListResp> {
    /**
     * 群id
     */
    private String groupid;
    /**
     * 分页页数
     */
    private String pageNumber;
    /**
     * 搜索字段
     */
    private String searchkey;

    public GroupUserListReq(String groupid, String pageNumber, String searchkey) {
        this.groupid = groupid;
        this.pageNumber = pageNumber;
        this.searchkey = searchkey;
    }

    @Override
    public String path() {
        return "/mytio/chat/groupUserList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("pageNumber", pageNumber)
                .append("groupid", groupid)
                .append("searchkey", searchkey)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<GroupUserListResp>>() {
        }.getType();
    }
}
