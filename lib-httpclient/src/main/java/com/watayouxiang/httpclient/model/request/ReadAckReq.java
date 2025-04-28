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
 *     time   : 2021/01/07
 *     desc   : 会话已读 ack
 * </pre>
 */
public class ReadAckReq extends BaseReq<Integer> {

    private final String chatlinkid;

    public ReadAckReq(String chatlinkid) {
        this.chatlinkid = chatlinkid;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("chatlinkid", chatlinkid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Integer>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/readAck.tio_x";
    }
}
