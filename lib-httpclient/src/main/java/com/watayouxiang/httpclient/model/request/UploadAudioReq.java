package com.watayouxiang.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;

import java.io.File;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/8/4
 *     desc   : 上传音频
 * </pre>
 */
public class UploadAudioReq extends BaseReq<String> {
    /**
     * 聊天会话id
     */
    private String chatlinkid;
    /**
     * 单文件上传
     */
    private String uploadFile;

    public UploadAudioReq(String chatlinkid, String uploadFile) {
        this.chatlinkid = chatlinkid;
        this.uploadFile = uploadFile;
    }

    @Override
    public String path() {
        return "/mytio/chat/audio.tio_x";
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
                .append("uploadFile", new File(uploadFile))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
