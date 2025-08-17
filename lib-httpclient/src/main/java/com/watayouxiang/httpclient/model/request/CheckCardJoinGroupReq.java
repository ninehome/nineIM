package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/3
 * desc : 验证是否加入了群聊
 */
public class CheckCardJoinGroupReq extends BaseReq<Integer> {
    private final String groupid;
    private final String applyuid;

    public CheckCardJoinGroupReq(String groupid, String applyuid) {
        this.groupid = groupid;
        this.applyuid = applyuid;
    }

    @Override
    public String path() {
        return "/mytio/chat/checkCardJoinGroup.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("applyuid", applyuid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }
}
