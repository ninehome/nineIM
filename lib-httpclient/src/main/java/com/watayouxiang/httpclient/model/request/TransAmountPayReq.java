package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc : 用户信息请求
 *
 * @see UserInfoResp
 */
public class TransAmountPayReq extends BaseReq<Object> {

    private String serial;
    private String passwd;
    private String timestamp;

    public TransAmountPayReq(String serial, String passwd, String timestamp) {
        this.serial = serial;
        this.passwd = passwd;
        this.timestamp = timestamp;
    }

    @Override
    public String path() {
        return "/mytio/trans/payPwd.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("serial", String.valueOf(serial))
                .append("passwd", String.valueOf(passwd))
                .append("timestamp", timestamp);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Object>>() {
        }.getType();
    }
}
