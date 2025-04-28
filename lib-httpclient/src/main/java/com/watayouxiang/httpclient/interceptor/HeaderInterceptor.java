package com.watayouxiang.httpclient.interceptor;

import android.content.Context;

import com.watayouxiang.httpclient.utils.DeviceUtils;
import com.watayouxiang.httpclient.utils.ObjectUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : TaoWang
 * date : 2020-02-05
 * desc : 添加 tio 统一请求头
 */
public class HeaderInterceptor implements Interceptor {
    private final Context context;

    private final String deviceInfo;
    private final String appVersion;
    private final String resolution;
    private final String size;
    private String cid = "official";

    public HeaderInterceptor(Context context) {
        this.context = context;
        deviceInfo = encodeHeader(DeviceUtils.getDeviceInfo());
        appVersion = encodeHeader(DeviceUtils.getAppVersion(context));
        resolution = encodeHeader(DeviceUtils.getResolution(context));
        size = encodeHeader(DeviceUtils.getSize(context));
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder()
                // 手机型号
                .addHeader("tio-deviceinfo", ObjectUtils.nonNull(deviceInfo))
                // IMEI
                .addHeader("tio-imei", ObjectUtils.nonNull(encodeHeader(DeviceUtils.getImei(context))))
                // App版本
                .addHeader("tio-appversion", ObjectUtils.nonNull(appVersion))
                // 渠道号
                .addHeader("tio-cid", ObjectUtils.nonNull(cid))
                // 手机分辨率
                .addHeader("tio-resolution", ObjectUtils.nonNull(resolution))
                // 运营商
                .addHeader("tio-operator", ObjectUtils.nonNull(encodeHeader(DeviceUtils.getOperator(context))))
                // 手机尺寸
                .addHeader("tio-size", ObjectUtils.nonNull(size));

        return chain.proceed(builder.build());
    }

    /**
     * java.lang.IllegalArgumentException: Unexpected char 错误解决方法
     * 因为 okhttp 对 header 的编码做了验证，所以不合要求的字符需要单独转码
     *
     * @param headInfo 不合要求的字符
     * @return 符合要求的字符
     */
    private String encodeHeader(String headInfo) {
        if (headInfo == null) return null;

        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                builder.append(String.format("\\u%04x", (int) c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 设置渠道号
     */
    public void setChannelId(String cid) {
        this.cid = encodeHeader(cid);
    }
}
