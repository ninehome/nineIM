package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 * <p>
 * {@link String}
 */
public class ModifyNameReq extends BaseReq<String> {
    private String groupid;
    private String name;

    @Override
    public String path() {
        return "/mytio/group/modifyName.tio_x";
    }

    public ModifyNameReq(String groupid, String name) {
        this.groupid = groupid;
        this.name = name;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("name", name)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
