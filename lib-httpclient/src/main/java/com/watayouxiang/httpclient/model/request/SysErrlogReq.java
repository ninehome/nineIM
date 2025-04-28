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
 *     time   : 2020/11/02
 *     desc   : 上传日志
 * </pre>
 */
public class SysErrlogReq extends BaseReq<String> {
    /**
     * 单文件上传
     */
    private final String filePath;

    public SysErrlogReq(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }

    @Override
    public String path() {
        return "/mytio/sys/errlog.tio_x";
    }

    @Override
    public TioMap<String, File> files() {
        return super.files()
                .append("uploadFile", new File(filePath))
                ;
    }
}
