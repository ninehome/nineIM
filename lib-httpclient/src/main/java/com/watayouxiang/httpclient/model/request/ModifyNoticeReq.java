package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 * <p>
 * {@link Void}
 */
public class ModifyNoticeReq extends BaseReq<Void> {
    private String groupid;
    private String notice;

    @Override
    public String path() {
        return "/mytio/group/modifyNotice.tio_x";
    }

    public ModifyNoticeReq(String groupid, String notice) {
        this.groupid = groupid;
        this.notice = notice;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("notice", notice)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
