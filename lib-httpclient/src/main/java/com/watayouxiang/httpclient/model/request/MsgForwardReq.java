package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/23
 *     desc   : 消息转发
 * </pre>
 */
public class MsgForwardReq extends BaseReq<String> {
    private String chatlinkid;
    private String uids;
    private String groupids;
    private String mids;

    public MsgForwardReq(String chatlinkid, String uids, String groupids, String mids) {
        this.chatlinkid = chatlinkid;
        this.uids = uids;
        this.groupids = groupids;
        this.mids = mids;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/msgForward.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                .append("uids", uids)
                .append("groupids", groupids)
                .append("mids", mids)
                ;
    }
}
