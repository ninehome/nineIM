package com.watayouxiang.httpclient.callback;

import android.os.Environment;
import android.text.TextUtils;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okgo.utils.IOUtils;
import com.watayouxiang.httpclient.TioHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;


public class TioFileConvert implements Converter<File> {

    private static final String FOLDER_DEFAULT = TioHttpClient.getApplication().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator;

    private String folder;                  //目标文件存储的文件夹路径
    private String fileName;                //目标文件存储的文件名
    private Callback<File> callback;        //下载回调

    public TioFileConvert() {
        this(null);
    }

    public TioFileConvert(String fileName) {
        this(FOLDER_DEFAULT, fileName);
    }

    public TioFileConvert(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public void setCallback(Callback<File> callback) {
        this.callback = callback;
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        String url = response.request().url().toString();
        if (TextUtils.isEmpty(folder))
            folder = FOLDER_DEFAULT;
        if (TextUtils.isEmpty(fileName)) fileName = HttpUtils.getNetFileName(response, url);

        File dir = new File(folder);
        IOUtils.createFolder(dir);
        File file = new File(dir, fileName);
        // 如果存在，那么加时间戳前缀
        if (file.exists()) {
            file = new File(dir, System.currentTimeMillis() + "_" + fileName);
        }
        // 删除重复的文件
        IOUtils.delFileOrFolder(file);

        InputStream bodyStream = null;
        byte[] buffer = new byte[8192];
        FileOutputStream fileOutputStream = null;
        try {
            ResponseBody body = response.body();
            if (body == null) return null;

            bodyStream = body.byteStream();
            Progress progress = new Progress();
            progress.totalSize = body.contentLength();
            progress.fileName = fileName;
            progress.filePath = file.getAbsolutePath();
            progress.status = Progress.LOADING;
            progress.url = url;
            progress.tag = url;

            int len;
            fileOutputStream = new FileOutputStream(file);
            while ((len = bodyStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);

                if (callback == null) continue;
                Progress.changeProgress(progress, len, new Progress.Action() {
                    @Override
                    public void call(Progress progress) {
                        onProgress(progress);
                    }
                });
            }
            fileOutputStream.flush();
            return file;
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    private void onProgress(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.downloadProgress(progress);   //进度回调的方法
            }
        });
    }
}
