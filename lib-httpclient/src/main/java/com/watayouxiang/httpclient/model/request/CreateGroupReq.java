package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CreateGroupResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 *
 * @see CreateGroupResp
 */
public class CreateGroupReq extends BaseReq<CreateGroupResp> {
    private final String name;
    private final String uidList;

    public CreateGroupReq(String name, String uidList) {
        this.name = name;
        this.uidList = uidList;
    }

    @Override
    public String path() {
        return "/mytio/chat/createGroup.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("name", name)
                .append("uidList", uidList)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<CreateGroupResp>>() {
        }.getType();
    }
}
