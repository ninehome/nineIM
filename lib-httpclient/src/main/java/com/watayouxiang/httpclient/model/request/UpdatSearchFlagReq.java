package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/27
 * desc : 是否能被别人搜到开关
 * <p>
 * response {@link Void}
 */
public class UpdatSearchFlagReq extends BaseReq<Void> {
    /**
     * 1 打开，2 关闭
     */
    private String searchflag;

    public UpdatSearchFlagReq(boolean open) {
        this.searchflag = open ? "1" : "2";
    }

    @Override
    public String path() {
        return "/mytio/user/updatSearchFlag.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("searchflag", searchflag);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
