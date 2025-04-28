package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.TransDetailResp;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

import java.lang.reflect.Type;

/**
 *zlb
 */
public class TransAmountRecieverReq extends BaseReq<TransDetailResp> {

    private String id;
    private String chatlinkid;

    public TransAmountRecieverReq(String id, String chatlinkid) {
        this.id = id;
        this.chatlinkid = chatlinkid;
    }

    @Override
    public String path() {
        return "/mytio/trans/reciever.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("id", id)
                .append("chatlinkid", chatlinkid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<TransDetailResp>>() {
        }.getType();
    }
}
