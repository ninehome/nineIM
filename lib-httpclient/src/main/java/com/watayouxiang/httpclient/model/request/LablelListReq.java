package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.LableListResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc : 通讯录列表
 *
 * @see MailListResp
 */
public class LablelListReq extends BaseReq<LableListResp> {
    private String uid;
    public LablelListReq(String uid) {
        this.uid = uid;
//        this.mode = mode;
//        this.searchkey = searchkey;
    }

    @Override
    public String path() {
        return "/mytio/friendGroup/list.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("uid", uid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<LableListResp>>() {
        }.getType();
    }
}
