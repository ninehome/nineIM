package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UploadCommonFileResp;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/4/16
 * desc : 上传文件
 */
public class UploadCommonFileReq extends BaseReq<UploadCommonFileResp> {
    private String filePath;

    public UploadCommonFileReq(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String path() {
        return "/mytio/upload/file.tio_x";
    }

    @Override
    public TioMap<String, File> files() {
        return super.files().append("uploadFile", new File(filePath));
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<UploadCommonFileResp>>() {
        }.getType();
    }
}
