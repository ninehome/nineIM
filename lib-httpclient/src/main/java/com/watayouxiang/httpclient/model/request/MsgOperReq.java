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
 *     time   : 2020/07/15
 *     desc   :
 * </pre>
 */
public class MsgOperReq extends BaseReq<String> {
    private String chatlinkid;
    private String mids;
    private String oper;

    public MsgOperReq(String chatlinkid, String mids, String oper) {
        this.chatlinkid = chatlinkid;
        this.mids = mids;
        this.oper = oper;
    }

    public MsgOperReq() {
    }

    public static MsgOperReq complaint(String chatLinkId, String mids) {
        MsgOperReq msgOperReq = new MsgOperReq();
        msgOperReq.chatlinkid = chatLinkId;
        msgOperReq.mids = mids;
        msgOperReq.oper = "99";
        return msgOperReq;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/msgOper.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                .append("mids", mids)
                .append("oper", oper)
                ;
    }
}
