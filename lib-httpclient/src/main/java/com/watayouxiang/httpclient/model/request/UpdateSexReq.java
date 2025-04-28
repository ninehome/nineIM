package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 修改性别
 */
public class UpdateSexReq extends BaseReq<Void> {
    private String sex;

    public UpdateSexReq(String sex) {
        this.sex = sex;
    }

    @Override
    public String path() {
        return "/mytio/user/updatSex.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params().append("sex", sex);
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
