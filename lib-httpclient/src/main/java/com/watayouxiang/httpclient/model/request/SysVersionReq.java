package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.SysVersionResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/18
 *     desc   :
 * </pre>
 */
public class SysVersionReq extends BaseReq<SysVersionResp> {
    private String version;

    public SysVersionReq(String version) {
        this.version = version;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<SysVersionResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/sys/version.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("version", version);
    }
}
