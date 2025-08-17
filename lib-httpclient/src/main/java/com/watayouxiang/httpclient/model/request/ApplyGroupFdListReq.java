package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc :
 */
public class ApplyGroupFdListReq extends BaseReq<ApplyGroupFdListResp> {
    private String groupid;
    private String searchkey;

    public ApplyGroupFdListReq(String groupid, String searchkey) {
        this.groupid = groupid;
        this.searchkey = searchkey;
    }

    @Override
    public String path() {
        return "/mytio/chat/applyGroupFdList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("searchkey", searchkey)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ApplyGroupFdListResp>>() {
        }.getType();
    }
}
