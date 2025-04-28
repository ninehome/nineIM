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
 *     time   : 2020/08/28
 *     desc   :
 * </pre>
 */
public class SynAckReq extends BaseReq<String> {
    /**
     * 同步对象的id
     */
    private String synid;

    public SynAckReq(String synid) {
        this.synid = synid;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("synid", synid);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/syn/ack.tio_x";
    }
}
