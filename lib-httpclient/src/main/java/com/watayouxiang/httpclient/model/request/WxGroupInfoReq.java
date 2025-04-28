package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.WxGroupInfoResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc : 群信息
 *
 * @see WxGroupInfoResp
 */
@Deprecated
public class WxGroupInfoReq extends BaseReq<WxGroupInfoResp> {
    private String groupid;

    public WxGroupInfoReq(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/group/info.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("groupid", groupid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxGroupInfoResp>>() {
        }.getType();
    }
}
