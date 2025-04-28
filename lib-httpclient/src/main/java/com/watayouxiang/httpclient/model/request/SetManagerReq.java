package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   : 禁言操作
 * </pre>
 */
public class SetManagerReq extends BaseReq<String> {
    private Integer toUid;
    private String groupId;
    private Integer flag;

    public SetManagerReq(Integer toUid, String groupId, Integer flag) {
        this.toUid = toUid;
        this.groupId = groupId;
        this.flag = flag;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("toUid", String.valueOf(toUid))
                .append("groupId", String.valueOf(groupId))
                .append("flag", String.valueOf(flag))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/setGroupManager.tio_x";
    }
}
