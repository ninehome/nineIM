package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/8/27
 *     desc   : 修改-消息提醒-开关
 * </pre>
 */
public class UpdateRemindReq extends BaseReq<Void> {
    /**
     * 1：开启；2：不开启
     */
    private String remindflag;

    public UpdateRemindReq(String remindflag) {
        this.remindflag = remindflag;
    }

    public String getRemindflag() {
        return remindflag;
    }

    @Override
    public String path() {
        return "/mytio/user/updatRemind.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("remindflag", remindflag);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
