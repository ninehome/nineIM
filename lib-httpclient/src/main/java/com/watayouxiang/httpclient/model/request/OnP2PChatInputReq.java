package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc :
 *
 * @see String
 */
public class OnP2PChatInputReq extends BaseReq<String> {
    private String chatlinkid;
    private String toUid;

    public OnP2PChatInputReq(String chatlinkid, String toUid) {
        this.chatlinkid = chatlinkid;
        this.toUid = toUid;
    }

    @Override
    public String path() {
        return "/mytio/chat/inP2pChatInput.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("chatlinkid", chatlinkid).append("toUid", toUid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
