package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   : 禁言用户列表
 * </pre>
 */
public class ForbiddenUserListReq extends BaseReq<ForbiddenUserListResp> {
    private final String pageNumber;
    private final String groupid;
    private final String searchkey;

    public ForbiddenUserListReq(String pageNumber, String groupid, String searchkey) {
        this.pageNumber = pageNumber;
        this.groupid = groupid;
        this.searchkey = searchkey;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("pageNumber", pageNumber)
                .append("groupid", groupid)
                .append("searchkey", searchkey)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ForbiddenUserListResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/forbiddenUserList.tio_x";
    }
}
