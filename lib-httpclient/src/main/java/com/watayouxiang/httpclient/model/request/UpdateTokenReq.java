package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/05
 *     desc   : 内测接口
 * </pre>
 */
@Deprecated
public class UpdateTokenReq extends BaseReq<Void> {
    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/test/updateToken.tio_x";
    }
}
