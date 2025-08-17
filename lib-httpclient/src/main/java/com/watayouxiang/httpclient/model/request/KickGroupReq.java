package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc :
 *
 * @see String
 */
public class KickGroupReq extends BaseReq<String> {
    /**
     * 将哪些用户加踢出群，userid用逗号隔开
     * 34343,43434,3434,34
     */
    private String uids;
    /**
     * 群id
     */
    private String groupid;

    public KickGroupReq(String uids, String groupid) {
        this.uids = uids;
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/chat/kickGroup.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("uids", uids)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
