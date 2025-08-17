package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.GroupApplyInfoResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc : 入群申请信息
 * <p>
 */
public class GroupApplyInfoReq extends BaseReq<GroupApplyInfoResp> {
    private String aid;

    public GroupApplyInfoReq(String aid) {
        this.aid = aid;
    }

    @Override
    public String path() {
        return "/mytio/chat/groupApplyInfo.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("aid", aid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<GroupApplyInfoResp>>() {
        }.getType();
    }
}
