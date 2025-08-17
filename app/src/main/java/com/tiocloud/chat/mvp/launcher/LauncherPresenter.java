package com.tiocloud.chat.mvp.launcher;

import android.os.Handler;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.widget.dialog.tio.ProtectGuideDialog;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc : 应用启动表现层
 */
public class LauncherPresenter extends LauncherContract.Presenter {
    private long startTime;

    public LauncherPresenter(LauncherContract.View view) {
        super(view);
    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
        // 显示隐私政策确认弹窗
        new ProtectGuideDialog(getView().getActivity(), () -> {
            // 移除启动时的权限请求
            // reqPermission();
            // 直接进入配置请求
            reqConfig();
        }).checkConfirm();
    }

    private void reqPermission() {
        PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.LOCATION)
                .rationale((activity, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> {
                    reqConfig();
                })
                .request();
    }

    private void reqConfig() {
        getModel().requestConfig(new BaseModel.DataProxy<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (TioConfig.OpenCloseConfig.isOnkeyLoginEnable()){
                    TioAccount.setUseOnkeyLogin(true);
                    TioAccount.initUmVerify(getView().getActivity().getApplicationContext(), TioConfig.um_appkey, TioConfig.um_secry);
                }
                TioAccount.isWxLoginEnable = TioConfig.OpenCloseConfig.isShowWxLogin();
                openNextPage();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                openNextPage();
                if (TioConfig.OpenCloseConfig.enterGroupChatCheck()){
                    exitApp(getView().getActivity().getString(R.string.get_locationinfo_fail) + msg);
                }else {
                    ToastUtils.showShort(getView().getActivity().getString(R.string.get_locationinfo_fail) + msg);
                }
            }
        });
    }

    public void toNext(){
        if (!getConfig){
            LogUtils.e("未获取到配置不跳转");
            return;
        }
        if (!getModel().isLogin()) {
            getView().openLoginPage();
        } else {
            getView().openMainPage();
        }
        getView().finish();
    }
    boolean getConfig = false;
    private void openNextPage() {
        getConfig = true;
        long d = System.currentTimeMillis() - startTime;
        if (d < TioConfig.OpenCloseConfig.getSplashTime()){
            LogUtils.e("不够3秒不跳转:"+d);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getView().getActivity().isFinishing()){
                        return;
                    }
                    toNext();
                }
            }, 3000);
            return;
        }
        toNext();
    }

    private void exitApp(String reason) {
        TioToast.showShort(reason);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.exitApp();
            }
        }, 2000);
    }
}