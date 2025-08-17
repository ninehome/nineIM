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
public class TransAmountCreateReq extends BaseReq<Object> {

    private Integer toUid;
    private Integer amount;
    private String remark;

    public TransAmountCreateReq(Integer toUid, Integer amount, String remark) {
        this.toUid = toUid;
        this.amount = amount;
        this.remark = remark;
    }

    @Override
    public String path() {
        return "/mytio/trans/transAccount.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("toUid", String.valueOf(toUid))
                .append("amount", String.valueOf(amount))
                .append("remark", remark);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Object>>() {
        }.getType();
    }
}
