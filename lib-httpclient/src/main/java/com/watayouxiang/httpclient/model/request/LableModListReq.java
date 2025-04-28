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
public class LableModListReq extends BaseReq<String> {
    private String groupId;
    private String userIdListStr;
    public LableModListReq(String groupId, String userIdListStr) {
        this.userIdListStr = userIdListStr;
        this.groupId = groupId;
    }

    @Override
    public String path() {
        return "/mytio/friendGroup/updateGroupUserList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("groupId",groupId)
                .append("userIdListStr", userIdListStr)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
