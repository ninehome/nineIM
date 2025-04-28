package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 修改头像
 */
public class UpdateAvatarReq extends BaseReq<Void> {
    private String avatarPath;

    public UpdateAvatarReq(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @Override
    public String path() {
        return "/mytio/user/updateAvatar.tio_x";
    }

    @Override
    public TioMap<String, File> files() {
        return super.files().append("uploadFile", new File(avatarPath));
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<Void>>() {
        }.getType();
    }
}
