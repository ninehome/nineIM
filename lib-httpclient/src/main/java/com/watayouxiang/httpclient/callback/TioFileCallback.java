package com.watayouxiang.httpclient.callback;

import com.lzy.okgo.callback.AbsCallback;

import java.io.File;

import okhttp3.Response;

public abstract class TioFileCallback extends AbsCallback<File> {

    private final TioFileConvert convert;    //文件转换类

    public TioFileCallback() {
        this(null);
    }

    public TioFileCallback(String destFileName) {
        this(null, destFileName);
    }

    public TioFileCallback(String destFileDir, String destFileName) {
        convert = new TioFileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }
}
