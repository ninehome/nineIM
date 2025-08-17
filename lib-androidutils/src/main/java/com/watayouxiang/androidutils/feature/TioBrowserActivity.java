package com.watayouxiang.androidutils.feature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.watayouxiang.androidutils.R;
import com.watayouxiang.demoshell.BaseActivity;
import com.watayouxiang.demoshell.webview.TListener;
import com.watayouxiang.demoshell.webview.TWebView;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/13
 *     desc   : 浏览器页
 * </pre>
 */
public class TioBrowserActivity extends BaseActivity {
    private TWebView mTWebView;
    private ProgressBar mProgressBar;
    public static String EXTRA_URL = "URL";

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, TioBrowserActivity.class);
        starter.putExtra("URL", url);
        context.startActivity(starter);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.tio_activity_browser;
    }

    @Override
    protected CharSequence getPageTitle() {
        return "加载中...";
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mProgressBar = findViewById(R.id.progressBar);
        mTWebView = findViewById(R.id.browser);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //设置字体默认缩放大小为100%
        mTWebView.getSettings().setTextZoom(100);
        //设置监听
        setWebListener(mTWebView, mProgressBar);
        //加载网页
        String url = getIntent().getStringExtra("URL");
        if (url != null) {
            mTWebView.loadUrl(url);
        }
    }

    private void setWebListener(TWebView webBrowser, final ProgressBar progressBar) {
        webBrowser.setListener(new TListener() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mTWebView.canGoBack()) {
                mTWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTWebView != null) {
            mTWebView.releaseRes();
            mTWebView = null;
        }
        if (mProgressBar != null) {
            mProgressBar = null;
        }
    }
}