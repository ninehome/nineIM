package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AtGroupUserListResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/24
 *     desc   : @ 群成员列表
 * </pre>
 */
public class AtGroupUserListReq extends BaseReq<AtGroupUserListResp> {
    private String groupid;
    private String searchkey;

    public AtGroupUserListReq(String groupid, String searchkey) {
        this.groupid = groupid;
        this.searchkey = searchkey;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<AtGroupUserListResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/atGroupUserList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("searchkey", searchkey)
                ;
    }
}
