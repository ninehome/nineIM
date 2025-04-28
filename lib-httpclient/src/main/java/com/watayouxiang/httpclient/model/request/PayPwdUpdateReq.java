package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/17
 *     desc   :
 * </pre>
 */
public class PayPwdUpdateReq extends BaseReq<String> {

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("oldPassword", oldPassword)
                .append("oldpwdTimestamp", oldpwdTimestamp)
                .append("newPassword", newPassword)
                ;
    }

    private String oldPassword;
    private String oldpwdTimestamp;
    private String newPassword;

    public PayPwdUpdateReq(String oldPassword, String oldpwdTimestamp, String newPassword) {
        this.oldPassword = oldPassword;
        this.oldpwdTimestamp = oldpwdTimestamp;
        this.newPassword = newPassword;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/wallet/updatePayPassword.tio_x";
    }
}
