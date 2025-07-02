package com.watayouxiang.httpclient;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.utils.HttpUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioConvert;
import com.watayouxiang.httpclient.cookie.TioCookieJar;
import com.watayouxiang.httpclient.interceptor.HeaderInterceptor;
import com.watayouxiang.httpclient.interceptor.LoggingInterceptor;
import com.watayouxiang.httpclient.interceptor.RespInterceptor;
import com.watayouxiang.httpclient.listener.OnCookieListener;
import com.watayouxiang.httpclient.model.BaseReq;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.prefernces.HttpCache;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * author : TaoWang
 * date : 2020/4/28
 * desc :
 */
public class TioHttpClient {

    public static final String LOG_TAG = "TioHttpClient";

    // OkGo框架
    private final OkGo okGo;
    // 响应拦截器
    private final RespInterceptor respInterceptor;
    // 请求头拦截器
    private HeaderInterceptor headerInterceptor;
    // cookie监听器
    private OnCookieListener onCookieListener;
    // Application
    private static Application application;
    // 日志拦截器
    private final LoggingInterceptor loggingInterceptor;

    public static TioHttpClient getInstance() {
        return HttpClientHolder.holder;
    }

    private static class HttpClientHolder {
        private static final TioHttpClient holder = new TioHttpClient();
    }

    private TioHttpClient() {
        okGo = OkGo.getInstance();

        // 清空OkGo 拦截器
        OkHttpClient.Builder builder = okGo.getOkHttpClient().newBuilder();
        builder.interceptors().clear();
        OkHttpClient okHttpClient = builder.build();
        okGo.setOkHttpClient(okHttpClient);

        // 设置OkGo userAgent
        HttpHeaders.setUserAgent("tiohttp/watayouxiang");

        // 日志拦截器
        loggingInterceptor = new LoggingInterceptor(LOG_TAG);
        loggingInterceptor.setPrintLevel(LoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        addInterceptor(loggingInterceptor);

        // 响应拦截器
        addInterceptor(respInterceptor = new RespInterceptor());

        // Stetho 拦截器
        addNetworkInterceptor(new StethoInterceptor());

        // debug
        setDebug(BuildConfig.DEBUG);

        // 初始化
        init(Utils.getApp());
    }

    // ====================================================================================
    // config
    // ====================================================================================

    /**
     * 必须在全局Application中初始化
     */
    private TioHttpClient init(Application app) {
        application = app;
        // Stetho 初始化
        Stetho.initializeWithDefaults(app);
        // 初始化OkGo
        okGo.init(app);
        // cookie持久化
        setCookieJar(new TioCookieJar(app) {
            @Override
            public void onSaveTioCookiesFromResponse(@NonNull List<Cookie> tioCookies) {
                super.onSaveTioCookiesFromResponse(tioCookies);
                if (onCookieListener != null) {
                    onCookieListener.onSaveTioCookiesFromResponse(tioCookies);
                }
            }
        });
        // 请求头拦截器
        addInterceptor(headerInterceptor = new HeaderInterceptor(app));
        return this;
    }

    /**
     * 设置cookie
     */
    public TioHttpClient setCookieJar(CookieJar cookieJar) {
        HttpUtils.checkNotNull(cookieJar, "cookieJar == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .build());
        return this;
    }

    /**
     * 添加拦截器
     */
    public TioHttpClient addInterceptor(Interceptor interceptor) {
        HttpUtils.checkNotNull(interceptor, "interceptor == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build());
        return this;
    }

    /**
     * 添加拦截器
     */
    public TioHttpClient addNetworkInterceptor(Interceptor interceptor) {
        HttpUtils.checkNotNull(interceptor, "interceptor == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .addNetworkInterceptor(interceptor)
                .build());
        return this;
    }

    /**
     * 获取响应拦截器
     */
    public RespInterceptor getRespInterceptor() {
        return respInterceptor;
    }

    /**
     * 获取头部拦截器
     */
    public HeaderInterceptor getHeaderInterceptor() {
        return headerInterceptor;
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        CacheManager.getInstance().clear();
    }

    /**
     * 保存cookie的回调
     */
    public TioHttpClient setOnCookieListener(OnCookieListener listener) {
        onCookieListener = listener;
        return this;
    }

    /**
     * 设置debug模式开关
     */
    public void setDebug(boolean isDebug) {
        if (loggingInterceptor != null) {
            LoggingInterceptor.Level level = isDebug ? LoggingInterceptor.Level.BODY : LoggingInterceptor.Level.NONE;
            loggingInterceptor.setPrintLevel(level);
        }
    }

    public static String getBaseUrl() {
        System.out.println("fighting:"+TioHttpClient.class+" baseUrl:"+HttpCache.getBaseUrl());
        return HttpCache.getBaseUrl();
    }

    public static Application getApplication() {
        return application;
    }

    // ====================================================================================
    // 请求
    // ====================================================================================

    /**
     * 同步 get
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> get(Req req) {
        try {
            return OkGo.<BaseResp<Data>>get(req.url())
                    .tag(req.getCancelTag())
                    .cacheMode(req.getCacheMode())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()))
                    .adapt()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 异步 get
     */
    public static <Req extends BaseReq<Data>, Data> void get(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        OkGo.<BaseResp<Data>>get(req.url())
                .tag(req.getCancelTag())
                .cacheMode(req.getCacheMode())
                .params(req.params())
                .execute(callback);
    }

    @Deprecated
    public static <Req extends BaseReq<Data>, Data> void get(Object CancelTag, Req req, Callback<BaseResp<Data>> callback) {
        req.setCancelTag(CancelTag);
        get(req, callback);
    }

    /**
     * 同步 post
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> post(Req req) {
        try {
            return OkGo.<BaseResp<Data>>post(req.url())
                    .tag(req.getCancelTag())
                    .cacheMode(req.getCacheMode())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()))
                    .adapt()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 异步 post
     */
    public static <Req extends BaseReq<Data>, Data> void post(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        OkGo.<BaseResp<Data>>post(req.url())
                .tag(req.getCancelTag())
                .cacheMode(req.getCacheMode())
                .params(req.params())
                .execute(callback);
    }

    @Deprecated
    public static <Req extends BaseReq<Data>, Data> void post(Object CancelTag, Req req, Callback<BaseResp<Data>> callback) {
        req.setCancelTag(CancelTag);
        post(req, callback);
    }

    /**
     * 同步 upload
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> upload(Req req) {
        try {
            PostRequest<BaseResp<Data>> postReq = OkGo.<BaseResp<Data>>post(req.url())
                    .tag(req.getCancelTag())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()));

            TioMap<String, File> fileMap = req.files();
            Set<Map.Entry<String, File>> entries = fileMap.entrySet();
            for (Map.Entry<String, File> entry : entries) {
                String key = entry.getKey();
                File value = entry.getValue();
                postReq.params(key, value);
            }

            return postReq.adapt().execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 异步 upload
     */
    public static <Req extends BaseReq<Data>, Data> void upload(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        PostRequest<BaseResp<Data>> postReq = OkGo.<BaseResp<Data>>post(req.url())
                .tag(req.getCancelTag())
                .params(req.params());

        TioMap<String, File> fileMap = req.files();
        Set<Map.Entry<String, File>> entries = fileMap.entrySet();
        for (Map.Entry<String, File> entry : entries) {
            String key = entry.getKey();
            File value = entry.getValue();
            postReq.params(key, value);
        }

        postReq.execute(callback);
    }

    /**
     * 取消请求
     */
    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    private static <Data> void setBodyType(Callback<BaseResp<Data>> callback, Type bodyType) {
        if (callback instanceof TioCallback) {
            TioCallback tioCallback = (TioCallback) callback;
            tioCallback.setType(bodyType);
        }
    }
}
