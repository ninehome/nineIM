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
 *
 * @see String
 */
public class UploadImgReq extends BaseReq<String> {
    /**
     * 会话id
     */
    private final String chatlinkid;
    /**
     * 单文件上传
     */
    private final String imgPath;

    public UploadImgReq(String chatlinkid, String imgPath) {
        this.chatlinkid = chatlinkid;
        this.imgPath = imgPath;
    }

    @Override
    public String path() {
        return "/mytio/chat/img.tio_x";
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
                .append("uploadFile", new File(imgPath))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
