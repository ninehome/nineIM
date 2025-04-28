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
public class PrivacyReq extends BaseReq<String> {
    private String extName;
    private String extValue;
//    private String uidFind;
//    private String phoneFind;
//    private String emailFind;
//    private String loginNameFind;
//    private String groupAdd;
//    private String qrcodeAdd;
//    private String cardAdd;

    public PrivacyReq(String extName,String extValue) {
        this.extName = extName;
        this.extValue = extValue;
    }

    @Override
    public String path() {
        return "/mytio/user/updateUserExt.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap().append("extName", extName).append("extValue", extValue);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
