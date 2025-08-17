package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/14
 * desc :
 */
public class UpdateNickReq extends BaseReq<Void> {
    private String nick;

    public UpdateNickReq(String nick) {
        this.nick = nick;
    }

    @Override
    public String path() {
        return "/mytio/user/updateNick.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("nick", nick);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
