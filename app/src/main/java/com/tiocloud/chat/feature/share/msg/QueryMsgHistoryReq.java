package com.tiocloud.chat.feature.share.msg;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.MsgHistotyEntity;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 非验证加好友
 */
public class QueryMsgHistoryReq extends BaseReq<List<MsgHistotyEntity>> {
    private String chatMode;
    private String msgIds;

    public QueryMsgHistoryReq(String chatMode, String msgIds) {
        this.chatMode = chatMode;
        this.msgIds = msgIds;
    }

    @Override
    public String path() {
        return "/mytio/chat/msgContent.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatMode", chatMode)
                .append("msgIds", msgIds)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<List<MsgHistotyEntity>>>() {
        }.getType();
    }
}
