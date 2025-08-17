package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 *
 * @see GroupInfoResp
 */
public class UserRecieverAccountReq extends BaseReq<UserRecieverAccountResp> {

    public UserRecieverAccountReq() {
    }

    @Override
    public String path() {
        return "/mytio/withdrawAccount/findAll.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<UserRecieverAccountResp>>() {
        }.getType();
    }
}
