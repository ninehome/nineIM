package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/4
 * desc : 加入群聊
 *
 * @see String
 */
public class DealGroupApplyReq extends BaseReq<String> {
    private final String aid;
    private final String mid;

    public DealGroupApplyReq(String aid, String mid) {
        this.aid = aid;
        this.mid = mid;
    }


    @Override
    public String path() {
        return "/mytio/chat/dealGroupApply.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("aid", aid)
                .append("mid", mid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
