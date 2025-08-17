package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ForbiddenFlagResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   : 用户禁言状态
 * </pre>
 */
public class ForbiddenFlagReq extends BaseReq<ForbiddenFlagResp> {
    private final String uid;
    private final String groupid;

    public ForbiddenFlagReq(String uid, String groupid) {
        this.uid = uid;
        this.groupid = groupid;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("uid", uid)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ForbiddenFlagResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/forbiddenFlag.tio_x";
    }
}
