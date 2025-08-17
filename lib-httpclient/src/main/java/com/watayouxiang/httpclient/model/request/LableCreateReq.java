package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.LableListResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc : 通讯录列表
 *
 * @see MailListResp
 */
public class LableCreateReq extends BaseReq<String> {
    private String groupname;
    private String friendidlist;
    private String uid;
    public LableCreateReq(String uid, String groupname, String friendidlist) {
        this.groupname = groupname;
        this.friendidlist = friendidlist;
        this.uid = uid;
    }

    @Override
    public String path() {
        return "/mytio/friendGroup/add.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("uid",uid)
                .append("groupname", groupname)
                .append("friendidlist", friendidlist)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
