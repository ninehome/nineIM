package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.RegisterResp;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : 邮箱注册
 *
 * @see RegisterResp
 */
public class RegisterReq extends BaseReq<RegisterResp> {
    /**
     * 账号
     */
    private String loginname;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 是否同意协议
     */
    private String agreement;

    private RegisterReq(String loginname, String nick, String pwd, String agreement) {
        this.loginname = loginname;
        this.nick = nick;
//        this.pwd = MD5Utils.getMd5("${" + loginname + "}" + pwd);
        if (HttpUrlUtils.newPwdRule){
            this.pwd = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + pwd);
        }else {
            this.pwd = MD5Utils.getMd5("${" + loginname + "}" + pwd);
        }
        this.agreement = agreement;
    }

    public RegisterReq(String loginname, String nick, String pwd) {
        this(loginname, nick, pwd, "on");
    }

    @Override
    public String path() {
        return "/mytio/register/1.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("loginname", loginname)
                .append("nick", nick)
                .append("pwd", pwd)
                .append("agreement", agreement);
    }

    public String getLoginname() {
        return loginname;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<RegisterResp>>() {
        }.getType();
    }
}
