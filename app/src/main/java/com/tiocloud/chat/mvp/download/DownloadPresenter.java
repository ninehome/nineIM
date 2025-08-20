package com.tiocloud.chat.mvp.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.Formatter;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.chat.BuildConfig;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.callback.TioFileCallback;
import com.watayouxiang.httpclient.prefernces.HttpCache;

import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/14
 *     desc   :
 * </pre>
 */
public class DownloadPresenter extends DownloadContract.Presenter implements AutoCloseable {
    public DownloadPresenter() {
        super(new DownloadModel(), false);
    }

    @Override
    public void downloadWithTip(String url, Activity activity) {
        OkGo.<File>get(HttpCache.getResUrl(url))
                .execute(new TioFileCallback() {
                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        if (activity != null) {
                            SingletonProgressDialog.show_unCancel(activity, activity.getString(R.string.downloading));
                        }
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        TioToast.showShort(activity.getString(R.string.download_finnish));
                        File file = response.body();
                        try {
                            openFile(activity, file);
                        }catch (Exception e){
                            e.printStackTrace();
                            ToastUtils.showShort(activity.getString(R.string.cannot_open_file));
                        }

                    }

                    @Override
                    public void onError(Response<File> response) {
                        TioToast.showShort(activity.getString(R.string.download_fail));
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        TioLogger.d("下载进度：" + progress);

                        String downloadLength = Formatter.formatFileSize(Utils.getApp(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(Utils.getApp(), progress.totalSize);
                        TioLogger.d("DownloadSize：" + downloadLength + "/" + totalLength);

                        String speed = Formatter.formatFileSize(Utils.getApp(), progress.speed);
                        TioLogger.d("NetSpeed：" + String.format("%s/s", speed));

                        NumberFormat numberFormat = NumberFormat.getPercentInstance();
                        numberFormat.setMinimumFractionDigits(2);
                        TioLogger.d("Progress：" + numberFormat.format(progress.fraction));
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        SingletonProgressDialog.dismiss();
                    }
                });
    }

    @Override
    public void close() throws Exception {
        detachView();
    }

    private void openFile(Context context, File docFile){
        Intent in = new Intent("android.intent.action.VIEW");
        in.addCategory("android.intent.category.DEFAULT");
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(context,  "com.tiocloud.chat.fileprovider", docFile);
            // 给目标应用一个临时授权
            in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(docFile);
        }
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.setDataAndType(data, getMimeTypeFromFile(docFile));
        context.startActivity(in);
    }

    private static String getMimeTypeFromFile(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex > 0) {
            //获取文件的后缀名
            String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
            //在MIME和文件类型的匹配表中找到对应的MIME类型。
            HashMap<String, String> map = MyMimeMap.getMimeMap();
            if (!TextUtils.isEmpty(end) && map.keySet().contains(end)) {
                type = map.get(end);
            }
        }
        return type;
    }

    static class MyMimeMap {
        private static final HashMap<String, String> mapSimple = new HashMap<>();
        /**
         *  常用"文件扩展名—MIME类型"匹配表。
         *  注意，此表并不全，也并不是唯一的，就像有人喜欢用浏览器打开TXT一样，你可以根据自己的爱好自定义。
         */
        public static HashMap<String, String> getMimeMap() {
            if (mapSimple.size() == 0) {
                mapSimple.put(".3gp", "video/3gpp");
                mapSimple.put(".apk", "application/vnd.android.package-archive");
                mapSimple.put(".asf", "video/x-ms-asf");
                mapSimple.put(".avi", "video/x-msvideo");
                mapSimple.put(".bin", "application/octet-stream");
                mapSimple.put(".bmp", "image/bmp");
                mapSimple.put(".c", "text/plain");
                mapSimple.put(".chm", "application/x-chm");
                mapSimple.put(".class", "application/octet-stream");
                mapSimple.put(".conf", "text/plain");
                mapSimple.put(".cpp", "text/plain");
                mapSimple.put(".doc", "application/msword");
                mapSimple.put(".docx", "application/msword");
                mapSimple.put(".exe", "application/octet-stream");
                mapSimple.put(".gif", "image/gif");
                mapSimple.put(".gtar", "application/x-gtar");
                mapSimple.put(".gz", "application/x-gzip");
                mapSimple.put(".h", "text/plain");
                mapSimple.put(".htm", "text/html");
                mapSimple.put(".html", "text/html");
                mapSimple.put(".jar", "application/java-archive");
                mapSimple.put(".java", "text/plain");
                mapSimple.put(".jpeg", "image/jpeg");
                mapSimple.put(".jpg", "image/jpeg");
                mapSimple.put(".js", "application/x-javascript");
                mapSimple.put(".log", "text/plain");
                mapSimple.put(".m3u", "audio/x-mpegurl");
                mapSimple.put(".m4a", "audio/mp4a-latm");
                mapSimple.put(".m4b", "audio/mp4a-latm");
                mapSimple.put(".m4p", "audio/mp4a-latm");
                mapSimple.put(".m4u", "video/vnd.mpegurl");
                mapSimple.put(".m4v", "video/x-m4v");
                mapSimple.put(".mov", "video/quicktime");
                mapSimple.put(".mp2", "audio/x-mpeg");
                mapSimple.put(".mp3", "audio/x-mpeg");
                mapSimple.put(".mp4", "video/mp4");
                mapSimple.put(".mpc", "application/vnd.mpohun.certificate");
                mapSimple.put(".mpe", "video/mpeg");
                mapSimple.put(".mpeg", "video/mpeg");
                mapSimple.put(".mpg", "video/mpeg");
                mapSimple.put(".mpg4", "video/mp4");
                mapSimple.put(".mpga", "audio/mpeg");
                mapSimple.put(".msg", "application/vnd.ms-outlook");
                mapSimple.put(".ogg", "audio/ogg");
                mapSimple.put(".pdf", "application/pdf");
                mapSimple.put(".png", "image/png");
                mapSimple.put(".pps", "application/vnd.ms-powerpoint");
                mapSimple.put(".ppt", "application/vnd.ms-powerpoint");
                mapSimple.put(".pptx", "application/vnd.ms-powerpoint");
                mapSimple.put(".prop", "text/plain");
                mapSimple.put(".rar", "application/x-rar-compressed");
                mapSimple.put(".rc", "text/plain");
                mapSimple.put(".rmvb", "audio/x-pn-realaudio");
                mapSimple.put(".rtf", "application/rtf");
                mapSimple.put(".sh", "text/plain");
                mapSimple.put(".tar", "application/x-tar");
                mapSimple.put(".tgz", "application/x-compressed");
                mapSimple.put(".txt", "text/plain");
                mapSimple.put(".wav", "audio/x-wav");
                mapSimple.put(".wma", "audio/x-ms-wma");
                mapSimple.put(".wmv", "audio/x-ms-wmv");
                mapSimple.put(".wps", "application/vnd.ms-works");
                mapSimple.put(".xml", "text/plain");
                mapSimple.put(".xls", "application/vnd.ms-excel");
                mapSimple.put(".xlsx", "application/vnd.ms-excel");
                mapSimple.put(".z", "application/x-compress");
                mapSimple.put(".zip", "application/zip");
                mapSimple.put("", "*/*");
            }
            return mapSimple;
        }
    }
}
