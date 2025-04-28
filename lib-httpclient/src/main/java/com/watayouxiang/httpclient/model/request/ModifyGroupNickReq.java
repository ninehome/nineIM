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
 *     time   : 2020/8/26
 *     desc   :
 * </pre>
 */
public class ModifyGroupNickReq extends BaseReq<String> {
    private String groupid;
    private String nick;

    @Override
    public String path() {
        return "/mytio/chat/modifyGroupNick.tio_x";
    }

    public ModifyGroupNickReq(String groupid, String nick) {
        this.groupid = groupid;
        this.nick = nick;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("nick", nick)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
