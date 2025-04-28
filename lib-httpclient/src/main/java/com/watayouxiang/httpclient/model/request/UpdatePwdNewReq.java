package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.httpclient.utils.MD5Utils;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/13
 * desc : 修改密码
 * <p>
 * 兼容老版本的"修改密码"
 */
public class UpdatePwdNewReq extends BaseReq<Void> {
    /**
     * 原始密码-明文
     */
    private final String updateType;

    private final String loginname;
    private final String code;
    /**
     * 新的手机号密码-加密
     */
    private final String oldPwd;
    private final String newPwd;
    /**
     * 新的邮箱密码-又邮箱必传-加密
     */

    public UpdatePwdNewReq(String updateType,String loginname,String code, String oldPwd,String newPwd) {
        if (HttpUrlUtils.newPwdRule){
            this.updateType = updateType;
            this.loginname = loginname;
            this.code = code;
            this.newPwd = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + newPwd);
            this.oldPwd = MD5Utils.getMd5("${" + HttpUrlUtils.pwdkey + "}" + oldPwd);
        }else {
            this.newPwd = MD5Utils.getMd5("${" + newPwd + "}" + newPwd);
            this.oldPwd = MD5Utils.getMd5("${" + oldPwd + "}" + oldPwd);
            this.updateType = updateType;
            this.loginname = loginname;
            this.code = code;
        }
    }

    @Override
    public String path() {
        return "/mytio/user/resetPwd2.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("updateType", updateType)
                .append("loginname", loginname)
                .append("code", code)
                .append("oldPwd", oldPwd)
                .append("newPwd", newPwd)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
