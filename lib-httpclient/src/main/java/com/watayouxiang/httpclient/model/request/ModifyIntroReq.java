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
public class ModifyIntroReq extends BaseReq<Void> {
    private String groupid;
    private String intro;

    @Override
    public String path() {
        return "/mytio/group/modifyIntro.tio_x";
    }

    public ModifyIntroReq(String groupid, String intro) {
        this.groupid = groupid;
        this.intro = intro;
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("groupid", groupid)
                .append("intro", intro)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
