package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc : 成员邀请开关
 * <p>
 * data {@link String}
 */
public class ModifyApplyReq extends BaseReq<String> {
    /**
     * 1 开启；2 关闭
     */
    private String mode;
    private String groupid;

    public ModifyApplyReq(boolean mode, String groupid) {
        this.mode = mode ? "1" : "2";
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/chat/modifyApply.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("mode", mode)
                .append("groupid", groupid)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
