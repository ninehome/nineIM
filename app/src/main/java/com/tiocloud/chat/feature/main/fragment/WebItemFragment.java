package com.tiocloud.chat.feature.main.fragment;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.yanxun.base.UiUtils;
import com.tiocloud.chat.yanxun.lable.EasyFragment;
import com.watayouxiang.androidutils.widget.WtTitleBar;


/**
 * 导航1
 */
public class WebItemFragment extends EasyFragment implements View.OnClickListener {

    private WebView mWebView;
    private ProgressBar mLoadBar;
    private WebSettings mWs;
    public String homeUrl;
    public String title;
    public boolean showHView = false;

    public View.OnClickListener onBackListener;

    private WtTitleBar titleBar;

    private ValueCallback<Uri[]> mUploadCallbackAboveFive;

    public WebItemFragment
            (View.OnClickListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_web_item;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        if (createView) {
            initView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        if (showHView){
//            BarUtils.setStatusBarCustom(findViewById(R.id.h_view));
        }
        findViewById(R.id.h_view).setVisibility(showHView?View.VISIBLE:View.GONE);
        mWebView = findViewById(R.id.wv_web);
        mLoadBar = findViewById(R.id.pb_load_bar);
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle(title);
        if (TioConfig.OpenCloseConfig.showWebViewIfOneSite() && Nav1Fragment.findItems != null && Nav1Fragment.findItems.size() < 2){
            titleBar.getIvBack().setVisibility(View.GONE);
        }else {
            titleBar.getIvBack().setOnClickListener(this.onBackListener);
        }

        ImageView ivRight1 = titleBar.getIvRight();
        ImageView ivRight2 = titleBar.getIvRight2();

        ivRight1.setVisibility(View.VISIBLE);
        ivRight2.setVisibility(View.VISIBLE);

        ivRight1.setImageResource(R.drawable.icon_refresh);
        ivRight2.setImageResource(R.drawable.icon_home);

        ivRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.reload();
            }
        });

        ivRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadUrl(homeUrl);
            }
        });

        mWs = mWebView.getSettings();
        // 设置可以支持缩放
        mWs.setSupportZoom(true);
        // 设置出现缩放工具
        mWs.setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        mWs.setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        mWs.setLoadWithOverviewMode(true);
        //自适应屏幕
        mWs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //支持js
        mWs.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {
                mLoadBar.setProgress(progress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                mUploadCallbackAboveFive = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), 11);
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
                mLoadBar.setVisibility(View.GONE);

                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                String endCookie = cookieManager.getCookie(url);
                Log.i("XWEBVIEW", "onPageFinished: endCookie : " + endCookie);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.getInstance().sync();//同步cookie
                } else {
                    CookieManager.getInstance().flush();
                }
//                if (homeUrl.equalsIgnoreCase(url)) {
//                    LogUtil.d("回到首页");
//                    ivHome.setVisibility(View.GONE);
//                } else {
//                    if (ivHome.getVisibility() == View.GONE) {
//                        ivHome.setVisibility(View.VISIBLE);
//                    }
//                }

//                if (TioConfig.OpenCloseConfig.showWebViewIfOneSite() && Nav1Fragment.findItems != null && Nav1Fragment.findItems.size() < 2){
//                    if (webView.canGoBack()){
//                        titleBar.getIvBack().setVisibility(View.VISIBLE);
//                        titleBar.getIvBack().setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (webView.canGoBack()){
//                                    webView.goBack();
//                                }else {
//                                    getActivity().onBackPressed();
//                                }
//                            }
//                        });
//                    }else {
//                        titleBar.getIvBack().setVisibility(View.GONE);
//                    }
//                }


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，
                // 为false调用系统浏览器或第三方浏览器
                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
                    return false;
                } else {
//                    try {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        view.getContext().startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(view.getContext(), "手机还没有安装支持打开此网页的应用！", Toast.LENGTH_SHORT).show();
//                    }
                    return true;
                }
            }
        });


        mWebView.loadUrl(homeUrl);

    }


    @Override
    public void onClick(View v) {
        if (!UiUtils.isNormalClick(v)) {
            return;
        }
        int id = v.getId();
        switch (id) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == mUploadCallbackAboveFive) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    int itemCount = clipData.getItemCount();
                    results = new Uri[itemCount];
                    for (int i = 0; i < itemCount; i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        mUploadCallbackAboveFive.onReceiveValue(results);
        mUploadCallbackAboveFive = null;
        return;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

}
