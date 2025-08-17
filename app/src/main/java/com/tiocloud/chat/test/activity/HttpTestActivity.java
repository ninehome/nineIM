package com.tiocloud.chat.test.activity;

import android.text.format.Formatter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.watayouxiang.demoshell.ListActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioFileCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.ConfigReq;
import com.watayouxiang.httpclient.model.request.ImServerReq;
import com.watayouxiang.httpclient.model.request.LoginReq;
import com.watayouxiang.httpclient.model.request.LogoutReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.ConfigResp;
import com.watayouxiang.httpclient.model.response.ImServerResp;
import com.watayouxiang.httpclient.model.response.LoginResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.prefernces.HttpCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpTestActivity extends ListActivity {

    @Override
    protected ListData getListData() {
        return new ListData()
                .addClick("GET（服务器地址）", v -> get())
                .addClick("POST（登录）", v -> post())
                .addClick("退出登录", v -> logout())
                .addClick("Cache（用户信息）", v -> cache())
                .addClick("获取配置信息", v -> config())
                .addClick("原版请求（同步）", v -> syncOriginalApi())
                .addClick("文件下载", v -> downloadFile())
                ;
    }

    private void downloadFile() {
        String url = "/wx/upload/file/22/9010/1119563/88097616/74541310984/84/154011/1288378774236307456.txt.zip";
        url = HttpCache.getResUrl(url);

        OkGo.<File>get(url)
                .tag(this)
                .execute(new TioFileCallback() {
                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                        TioLogger.d("正在下载中");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        TioLogger.d("下载完成");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        TioLogger.d("下载出错");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        TioLogger.d("下载进度：" + progress);

                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                        TioLogger.d("DownloadSize：" + downloadLength + "/" + totalLength);

                        String speed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                        TioLogger.d("NetSpeed：" + String.format("%s/s", speed));

                        NumberFormat numberFormat = NumberFormat.getPercentInstance();
                        numberFormat.setMinimumFractionDigits(2);
                        TioLogger.d("Progress：" + numberFormat.format(progress.fraction));
                    }
                });
    }

    private void syncOriginalApi() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ImServerReq req = new ImServerReq();
                req.setCancelTag(HttpTestActivity.this);

                Response<BaseResp<ImServerResp>> response = TioHttpClient.get(req);
                if (response.isSuccessful()) {
                    BaseResp<ImServerResp> body = response.body();
                    boolean ok = body.isOk();
                } else {
                    Throwable exception = response.getException();
                    String message = exception.getMessage();
                }

            }
        }.start();
    }

    private String uploadImage(String url, String imagePath) throws IOException, JSONException {
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(imagePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath, image)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.optString("image");
    }

    private void config() {
        ConfigReq req = new ConfigReq();
        req.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<ConfigResp>>() {
            @Override
            public void onCacheSuccess(Response<BaseResp<ConfigResp>> response) {
                super.onCacheSuccess(response);
                showToast("缓存：" + response.body().toString());
            }

            @Override
            public void onSuccess(Response<BaseResp<ConfigResp>> response) {
                showToast(response.body().toString());
            }

            @Override
            public void onError(Response<BaseResp<ConfigResp>> response) {
                super.onError(response);
                showToast(response.getException().getMessage());
            }
        });
    }

    private void cache() {
        UserCurrReq req = new UserCurrReq();
        req.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        TioHttpClient.get(HttpTestActivity.this, req, new TaoCallback<BaseResp<UserCurrResp>>() {
            @Override
            public void onCacheSuccess(Response<BaseResp<UserCurrResp>> response) {
                BaseResp<UserCurrResp> body = response.body();
                showToast("缓存数据：" + body.toString());
            }

            @Override
            public void onSuccess(Response<BaseResp<UserCurrResp>> response) {
                BaseResp<UserCurrResp> body = response.body();
                showToast(body.toString());
            }
        });
    }

    private void post() {
        LoginReq req = LoginReq.getPwdInstance("wata0709", "watayouxiang@qq.com");
        req.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        TioHttpClient.post(HttpTestActivity.this, req, new TaoCallback<BaseResp<LoginResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<LoginResp>> response) {
                BaseResp<LoginResp> body = response.body();
                showToast(body.toString());
            }
        });
    }

    private void get() {
        ImServerReq req = new ImServerReq();
        req.setCancelTag(HttpTestActivity.this);
        TioHttpClient.get(req, new TaoCallback<BaseResp<ImServerResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<ImServerResp>> response) {
                BaseResp<ImServerResp> body = response.body();
                showToast(body.toString());
            }
        });
    }

    private void logout() {
        TioHttpClient.get(this, new LogoutReq(), new TaoCallback<BaseResp<Void>>() {
            @Override
            public void onSuccess(Response<BaseResp<Void>> response) {
                BaseResp body = response.body();
                showToast(body.toString());
            }
        });
    }

    private void showToast(final String txt) {
        TioToast.show(this, txt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TioHttpClient.cancel(HttpTestActivity.this);
    }
}
