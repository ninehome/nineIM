package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-10
 * desc : 找回密码请求
 *
 * @see Void
 */
public class UserBindReq extends BaseReq<String> {
    private String bindType;
    private String loginname;
    private String bindData;
    private String code;

    public UserBindReq(String bindType, String loginname ,String bindData,String code) {
        this.bindType = bindType;
        this.loginname = loginname;
        this.bindData = bindData;
        this.code = code;
    }

    @Override
    public String path() {
        return "/mytio/user/userBind.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("bindType", bindType)
                .append("loginname",loginname)
                .append("bindData",bindData)
                .append("code",code)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
