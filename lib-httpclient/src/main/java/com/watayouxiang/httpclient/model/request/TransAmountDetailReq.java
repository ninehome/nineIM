package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.TransDetailResp;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc : 用户信息请求
 *
 * @see UserInfoResp
 */
public class TransAmountDetailReq extends BaseReq<TransDetailResp> {

    private Long id;

    public TransAmountDetailReq(Long id) {
        this.id = id;
    }

    @Override
    public String path() {
        return "/mytio/trans/detail.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("id", String.valueOf(id));
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<TransDetailResp>>() {
        }.getType();
    }
}
