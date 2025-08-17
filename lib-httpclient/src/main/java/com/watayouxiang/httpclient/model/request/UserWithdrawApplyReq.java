package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.BindPhoneResp;
import com.watayouxiang.httpclient.model.response.CommonResp;
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
public class UserWithdrawApplyReq extends BaseReq<String> {
    private Integer withdrawacountid;
    private Integer amountreal;
    private String passwd;
    private long timestamp;

    public UserWithdrawApplyReq(Integer withdrawacountid, Integer amountreal, String passwd, long timestamp) {
        this.withdrawacountid = withdrawacountid;
        this.amountreal = amountreal;
        this.passwd = passwd;
        this.timestamp = timestamp;
    }

    @Override
    public String path() {
        return "/mytio/walletWithdraw/submitWithdraw.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("withdrawacountid", withdrawacountid.toString())
                .append("amountreal", amountreal.toString())
                .append("passwd", passwd)
                .append("timestamp", String.valueOf(timestamp))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
