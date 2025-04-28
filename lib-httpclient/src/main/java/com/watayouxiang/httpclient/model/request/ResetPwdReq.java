package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ResetPwdResp;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public class ResetPwdReq extends BaseReq<ResetPwdResp> {
    private final String code;
    private final String phone;
    /**
     * 手机加密密码
     */
    private final String phonepwd;
    /**
     * 邮箱加密密码
     */
    private final String emailpwd;

    public ResetPwdReq(String code, String pwd, String phone, String email) {
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
        return "/mytio/user/resetPwd.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("code", code)
                .append("phone", phone)
                .append("phonepwd", phonepwd)
                .append("emailpwd", emailpwd)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ResetPwdResp>>() {
        }.getType();
    }
}
