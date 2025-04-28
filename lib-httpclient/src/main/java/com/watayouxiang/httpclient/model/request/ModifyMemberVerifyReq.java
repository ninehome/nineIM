package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc : 群审核开关
 * <p>
 * data {@link String}
 */
public class ModifyMemberVerifyReq extends BaseReq<String> {
    /**
     * 1 开启；2 关闭
     */
    private String mode;
    private String groupid;

    public ModifyMemberVerifyReq(boolean mode, String groupid) {
        this.mode = mode ? "1" : "2";
        this.groupid = groupid;
    }

    @Override
    public String path() {
        return "/mytio/group/modifyReview.tio_x";
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
