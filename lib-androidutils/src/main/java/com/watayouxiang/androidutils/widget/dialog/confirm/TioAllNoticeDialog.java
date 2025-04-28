package com.watayouxiang.androidutils.widget.dialog.confirm;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.util.UrlUtil;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;
import com.watayouxiang.httpclient.prefernces.HttpPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : 确认弹窗
 */
public class TioAllNoticeDialog extends TioDialog {
    private final String message;
    private final OnConfirmListener onConfirmListener;
    private final int messageGravity;

    public TioAllNoticeDialog(String message, int messageGravity, OnConfirmListener onConfirmListener) {
        this.message = message;
        this.messageGravity = messageGravity;
        this.onConfirmListener = onConfirmListener;
    }

    public TioAllNoticeDialog(String message, OnConfirmListener onConfirmListener) {
        this(message, Gravity.START, onConfirmListener);
    }

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_notice_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();

        WebView mWebView = findViewById(R.id.confirm_message);

        WebSettings mWs = mWebView.getSettings();
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                return true;
            }
        });
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                String endCookie = cookieManager.getCookie(url);
                Log.i("XWEBVIEW", "onPageFinished: endCookie : " + endCookie);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.getInstance().sync();//同步cookie
                } else {
                    CookieManager.getInstance().flush();
                }


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，
                // 为false调用系统浏览器或第三方浏览器
                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        try {
            String decode = URLDecoder.decode(message, "utf-8");
            String content = "<p><font color='red'>hello baidu!</font></p>";
//            mWebView.loadData(decode, "text/html", "UTF-8");
            mWebView.loadDataWithBaseURL(HttpPreferences.getResUrl(), decode, "text/html", "UTF-8", "");
//            mWebView.loadUrl("http://www.baidu.com");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

//        findViewById(R.id.confirm_button).setOnClickListener(v -> {
//            if (onConfirmListener != null) {
//                onConfirmListener.onConfirm(v, TioAllNoticeDialog.this);
//            }
//        });
    }

    public interface OnConfirmListener {
        void onConfirm(View view, TioAllNoticeDialog dialog);
    }
}
