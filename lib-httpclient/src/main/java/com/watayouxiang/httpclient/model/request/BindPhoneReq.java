package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 12/25/20
 *     desc   : 绑定手机号
 * </pre>
 */
public class BindPhoneReq extends BaseReq<BindPhoneResp> {

    private final String code;
    private final String phone;
    /**
     * 手机密码,邮箱绑定时必填手机加密密码
     */
    private final String phonepwd;
    /**
     * 邮箱密码,邮箱绑定时必填邮箱加密密码
     */
    private final String emailpwd;

    public BindPhoneReq(String code, String phone, String email, String pwd) {
        this.code = code;
        this.phone = phone;
        if (HttpUrlUtils.newPwdRule){
            this.phonepwd = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + pwd);
            this.emailpwd = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + pwd);
        }else {
            this.phonepwd = MD5Utils.getMd5("${" + phone + "}" + pwd);
            this.emailpwd = MD5Utils.getMd5("${" + email + "}" + pwd);
        }


    }

    @Override
    public String path() {
        return "/mytio/user/bindphone.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("code", code)
                .append("phone", phone)
                .append("phonepwd", phonepwd)
                .append("emailpwd", emailpwd);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<BindPhoneResp>>() {
        }.getType();
    }
}
