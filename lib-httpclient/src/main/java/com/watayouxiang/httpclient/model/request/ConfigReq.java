package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.response.ConfigResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc : 获取tio配置
 *
 * @see ConfigResp
 */
public class ConfigReq extends BaseReq<ConfigResp> {
    @Override
    public String path() {
        return "/mytio/config/base.tio_x";
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ConfigResp>>() {
        }.getType();
    }
}
