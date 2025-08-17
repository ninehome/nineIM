package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 处理申请
 */
public class DealApplyReq extends BaseReq<DealApplyResp> {
    private String applyid;
    private String remarkname;

    public DealApplyReq(String applyid, String remarkname) {
        this.applyid = applyid;
        this.remarkname = remarkname;
    }

    @Override
    public String path() {
        return "/mytio/chat/dealApply.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("applyid", applyid)
                .append("remarkname", remarkname)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<DealApplyResp>>() {
        }.getType();
    }
}
