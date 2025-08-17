package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/27
 * desc : 好友验证开关
 * <p>
 * response {@link Void}
 */
public class UpdateValidReq extends BaseReq<Void> {
    /**
     * 1 打开，2 关闭
     */
    private String fdvalidtype;

    public UpdateValidReq(boolean open) {
        this.fdvalidtype = open ? "1" : "2";
    }

    @Override
    public String path() {
        return "/mytio/user/updatValid.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("fdvalidtype", fdvalidtype);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
