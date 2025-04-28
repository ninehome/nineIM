package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.WxGroupListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-23
 * desc : 群列表请求
 *
 * @see WxGroupListResp
 */
@Deprecated
public class WxGroupListReq extends BaseReq<WxGroupListResp> {
    private String groupname;

    public WxGroupListReq() {
    }

    public WxGroupListReq(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String path() {
        return "/mytio/group/list.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("groupname", groupname);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxGroupListResp>>() {
        }.getType();
    }
}
