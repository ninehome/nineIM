package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/25/20
 *     desc   : 绑定手机号
 * </pre>
 */
public class UserRecieverAccountDeleteReq extends BaseReq<String> {
    private Integer id;

    public UserRecieverAccountDeleteReq(Integer id) {
        this.id = id;
    }

    @Override
    public String path() {
        return "/mytio/withdrawAccount/del.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        TioMap<String, String> paramMap = TioMap.getParamMap();
        paramMap.append("id", String.valueOf(id));
        return paramMap
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
