package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;
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
public class UserRecieverAccountAddReq extends BaseReq<String> {
    private UserRecieverAccountResp.UserWithdrawAccount account;

    public UserRecieverAccountAddReq(UserRecieverAccountResp.UserWithdrawAccount account) {
        this.account = account;
    }

    @Override
    public String path() {
        return "/mytio/withdrawAccount/save.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        TioMap<String, String> paramMap = TioMap.getParamMap();
        paramMap = paramMap.append("accounttype", account.getAccounttype().toString());
        paramMap = paramMap.append("accountname", account.getAccountname());
        paramMap = paramMap.append("accountno", account.getAccountno());
        if (account.getAccounttype().intValue() == 3){
            paramMap = paramMap.append("bankname", account.getBankname());
            paramMap = paramMap.append("brandname", account.getBrandname());
        }
        paramMap.append("remark", account.getRemark());
        return paramMap
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
