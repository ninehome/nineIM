package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc : 通讯录列表
 *
 * @see MailListResp
 */
public class LableModNameReq extends BaseReq<String> {
    private String groupId;
    private String groupName;
    public LableModNameReq(String groupId, String groupname) {
        this.groupName = groupname;
        this.groupId = groupId;
    }

    @Override
    public String path() {
        return "/mytio/friendGroup/update.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("groupId",groupId)
                .append("groupName", groupName)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
