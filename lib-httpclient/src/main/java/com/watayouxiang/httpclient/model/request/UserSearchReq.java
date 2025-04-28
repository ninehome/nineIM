package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserSearchResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 *
 * @see UserSearchResp
 */
public class UserSearchReq extends BaseReq<UserSearchResp> {
    private String pageNumber;
    private String nick;

    public UserSearchReq(String pageNumber, String nick) {
        this.pageNumber = pageNumber;
        this.nick = nick;
    }

    @Override
    public String path() {
        return "/mytio/user/search2.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("pageNumber", pageNumber)
                .append("searchName", nick)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<UserSearchResp>>() {
        }.getType();
    }
}
