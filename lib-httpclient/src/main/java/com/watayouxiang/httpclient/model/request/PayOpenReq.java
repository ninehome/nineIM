package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.PayOpenResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class PayOpenReq extends BaseReq<PayOpenResp> {
    /**
     * 姓名-Y
     */
    private final String name;
    /**
     * 证件号码-Y
     */
    private final String cardno;
    /**
     * 手机号-Y
     */
    private final String mobile;

    public PayOpenReq(String name, String cardno, String mobile) {
        this.name = name;
        this.cardno = cardno;
        this.mobile = mobile;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("name", name)
                .append("cardno", cardno)
                .append("mobile", mobile)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<PayOpenResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/pay/open.tio_x";
    }
}
