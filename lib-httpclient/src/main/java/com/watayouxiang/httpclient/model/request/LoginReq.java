package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.LoginResp;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc : 登录请求
 *
 * @see LoginResp
 */
public class LoginReq extends BaseReq<LoginResp> {
    /**
     * 加密密码
     */
    private final String pd5;
    /**
     * 验证码
     */
    private final String authcode;
    /**
     * 登录名
     */
    private final String loginname;


    private LoginReq(String pwd, String authcode, String loginname) {
        if (HttpUrlUtils.newPwdRule){
            this.pd5 = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + pwd);
        }else {
            this.pd5 = MD5Utils.getMd5("${" + loginname + "}" + pwd);
        }
        this.authcode = authcode;
        this.loginname = loginname;
    }

    public static LoginReq getCodeInstance(String authcode, String loginname) {
        return new LoginReq(null, authcode, loginname);
    }

    public static LoginReq getPwdInstance(String pwd, String loginname) {
        return new LoginReq(pwd, null, loginname);
    }

    @Override
    public String path() {
        return "/mytio/login.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("pd5", pd5)
                .append("authcode", authcode)
                .append("loginname", loginname);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<LoginResp>>() {
        }.getType();
    }
}
