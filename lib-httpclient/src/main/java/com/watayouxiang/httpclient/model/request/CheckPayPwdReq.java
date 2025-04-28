package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AdminRecieverAccountResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 *
 * @see GroupInfoResp
 */
public class CheckPayPwdReq extends BaseReq<String> {
    /**
     * 查询自己的用户信息标识：1：是；2：否
     */
    private final String pwd;
    private final long timestamp;

    public CheckPayPwdReq(String pwd, long timestamp) {
        this.pwd = pwd;
        this.timestamp = timestamp;
    }

    @Override
    public String path() {
        return "/mytio/wallet/checkPayPwd.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("passwd", pwd)
                .append("timestamp", String.valueOf(timestamp))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
