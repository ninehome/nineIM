package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   : 禁言操作
 * </pre>
 */
public class ForbiddenReq extends BaseReq<ForbiddenResp> {
    /**
     * 禁言类型：--------必填
     * 1：用户时长禁言；
     * 3：用户长久禁言；
     * 4：群禁言
     */
    private final String mode;
    /**
     * 时长禁言时间:
     * 秒-----------用户时长禁言操作必填
     */
    private final String duration;
    /**
     * 用户id-------用户禁言必填
     */
    private final String uid;
    /**
     * 群id--------必填
     */
    private final String groupid;
    /**
     * 操作：1：禁言；2：取消禁言--------必填
     */
    private final String oper;

    public ForbiddenReq(String mode, String duration, String uid, String groupid, String oper) {
        this.mode = mode;
        this.duration = duration;
        this.uid = uid;
        this.groupid = groupid;
        this.oper = oper;
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("mode", mode)
                .append("duration", duration)
                .append("uid", uid)
                .append("groupid", groupid)
                .append("oper", oper)
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<ForbiddenResp>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/chat/forbidden.tio_x";
    }
}
