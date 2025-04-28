package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 修改备注名
 */
public class ModifyRemarkNameReq extends BaseReq<String> {
    private String remarkname;
    private String frienduid;

    public ModifyRemarkNameReq(String remarkname, String frienduid) {
        this.remarkname = remarkname;
        this.frienduid = frienduid;
    }

    @Override
    public String path() {
        return "/mytio/friend/modifyRemarkname.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("remarkname", remarkname)
                .append("frienduid", frienduid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
