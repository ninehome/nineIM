package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 修改头像
 */
public class UpdateGroupAvatarReq extends BaseReq<Void> {
    private String avatarPath;
    private String groupId;

    public UpdateGroupAvatarReq(String groupId, String avatarPath) {
        this.avatarPath = avatarPath;
        this.groupId = groupId;
    }

    @Override
    public String path() {
        return "/mytio/chat/updateGroupAvatar/"+groupId+".tio_x?";
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
