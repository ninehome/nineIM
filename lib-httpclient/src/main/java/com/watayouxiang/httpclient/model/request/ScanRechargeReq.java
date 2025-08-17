package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.AdminRecieverAccountResp;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 *
 * @see GroupInfoResp
 */
public class ScanRechargeReq extends BaseReq<String> {
    private final Integer type;

    private final Integer money;

    public ScanRechargeReq(Integer type, Integer money) {
        this.type = type;
        this.money = money;
    }

    @Override
    public String path() {
        return "/mytio/walletRecharge/submitRecharge.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("paytype", type.toString())
                .append("amount", money.toString())
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
