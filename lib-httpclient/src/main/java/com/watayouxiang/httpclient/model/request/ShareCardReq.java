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
 *     time   : 2020/07/21
 *     desc   : 分享名片
 * </pre>
 */
public class ShareCardReq extends BaseReq<String> {
    /**
     * 名片类型：1：好友名片；2：群名片
     */
    private String chatmode;
    /**
     * 分享的好友uid列表
     */
    private String uids;
    /**
     * 分享的群id列表
     */
    private String groupids;
    /**
     * 名片id：好友uid/群id
     */
    private String cardid;

    public ShareCardReq(String chatmode, String uids, String groupids, String cardid) {
        this.chatmode = chatmode;
        this.uids = uids;
        this.groupids = groupids;
        this.cardid = cardid;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatmode", chatmode)
                .append("uids", uids)
                .append("groupids", groupids)
                .append("cardid", cardid)
                ;
    }

    @Override
    public String path() {
        return "/mytio/chat/shareCard.tio_x";
    }
}
