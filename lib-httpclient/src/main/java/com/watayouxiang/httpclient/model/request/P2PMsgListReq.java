package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.P2PMsgListResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-11
 * desc : 私聊历史消息
 *
 * @see P2PMsgListResp
 */
public class P2PMsgListReq extends BaseReq<P2PMsgListResp> {
    /**
     * 用户id
     */
    private String uid;
    /**
     * 聊天会话的id
     */
    private String chatlinkid;
    /**
     * 开始的消息id，此字段传递是，一般代表第一页（不含）以后的数据
     */
    private String startmid;

    public P2PMsgListReq(String uid, String chatlinkid, String startmid) {
        this.uid = uid;
        this.chatlinkid = chatlinkid;
        this.startmid = startmid;
    }

    public P2PMsgListReq(String chatlinkid, String startmid) {
        this.chatlinkid = chatlinkid;
        this.startmid = startmid;
    }

    @Override
    public String path() {
        return "/mytio/chat/p2pMsgList.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("uid", uid)
                .append("chatlinkid", chatlinkid)
                .append("startmid", startmid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<P2PMsgListResp>>() {
        }.getType();
    }
}
