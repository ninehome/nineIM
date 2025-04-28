package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc :
 *
 * @see String
 */
public class IsBlackReq extends BaseReq<Integer> {
    private String uid;

    public IsBlackReq(String uid) {
        this.uid = uid;
    }

    @Override
    public String path() {
        return "/mytio/user/block.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("uid", uid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }
}
