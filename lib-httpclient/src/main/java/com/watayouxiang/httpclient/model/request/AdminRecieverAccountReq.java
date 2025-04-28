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
public class AdminRecieverAccountReq extends BaseReq<AdminRecieverAccountResp> {
    /**
     * 查询自己的用户信息标识：1：是；2：否
     */
    private final Integer type;

    public AdminRecieverAccountReq(Integer type) {
        this.type = type;
    }

    @Override
    public String path() {
        return "/mytio/walletRecharge/getRecieverAccount.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("type", type.toString())
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<AdminRecieverAccountResp>>() {
        }.getType();
    }
}
