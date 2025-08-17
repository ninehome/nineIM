package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.BaseResp;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 */
public class UploadVideoReq extends BaseReq<String> {
    private String chatlinkid;
    /**
     * 单文件上传
     */
    private String videoPath;

    public UploadVideoReq(String chatlinkid, String videoPath) {
        this.chatlinkid = chatlinkid;
        this.videoPath = videoPath;
    }

    @Override
    public String path() {
        return "/mytio/chat/video.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                ;
    }

    @Override
    public TioMap<String, File> files() {
        return super.files()
                .append("uploadFile", new File(videoPath))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
