package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.response.WxRecentListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-03
 * desc : 请求会话列表
 *
 * @see WxRecentListResp
 */
public class WxRecentListReq extends BaseReq<WxRecentListResp> {

    @Override
    public String path() {
        return "/mytio/wx/recent.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<WxRecentListResp>>() {
        }.getType();
    }
}
