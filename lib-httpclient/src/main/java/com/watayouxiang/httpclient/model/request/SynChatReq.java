package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.SynChatResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/28
 *     desc   :
 * </pre>
 */
public class SynChatReq extends BaseReq<SynChatResp> {
    /**
     * 同步时间
     */
    private String syntime;

    public SynChatReq(String syntime) {
        this.syntime = syntime;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("syntime", syntime);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<SynChatResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/syn/chat.tio_x";
    }
}
