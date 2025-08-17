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
public class MultiMsgForwardReq extends BaseReq<Object> {
    private String chatlinkid;
    private String bizid;
    private String chatMode;
    private String msgIds;

    public MultiMsgForwardReq(String chatlinkid, String bizid, String chatMode, String msgIds) {
        this.chatlinkid = chatlinkid;
        this.bizid = bizid;
        this.chatMode = chatMode;
        this.msgIds = msgIds;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Object>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/transSend.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                .append("bizid", bizid)
                .append("chatMode", chatMode)
                .append("msgIds", msgIds)
                ;
    }
}
