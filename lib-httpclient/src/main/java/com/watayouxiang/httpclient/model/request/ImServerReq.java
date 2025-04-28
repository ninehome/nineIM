package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ImServerResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc : IM 服务器地址
 *
 * @see ImServerResp
 */
public class ImServerReq extends BaseReq<ImServerResp> {
    @Override
    public String path() {
        return "/mytio/im/imserver.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("tio_site_from_android", "1");
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ImServerResp>>() {
        }.getType();
    }
}
