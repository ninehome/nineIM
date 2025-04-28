package com.watayouxiang.httpclient.listener;

import androidx.annotation.NonNull;

import java.util.List;

import okhttp3.Cookie;

/**
 * author : TaoWang
 * date : 2020/6/3
 * desc :
 */
public interface OnCookieListener {
    /**
     * 保存 Response 中的 TioCookies
     *
     * @param tioCookies TioCookies（不为空，个数不为0）
     */
    void onSaveTioCookiesFromResponse(@NonNull List<Cookie> tioCookies);
}
