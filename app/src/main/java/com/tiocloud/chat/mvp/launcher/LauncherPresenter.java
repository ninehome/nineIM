package com.tiocloud.chat.mvp.launcher;

import android.os.Handler;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc : 应用启动表现层
 */
public class LauncherPresenter extends LauncherContract.Presenter {

    public LauncherPresenter(LauncherContract.View view) {
        super(view);
    }

    @Override
    public void init() {
        reqConfig();
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
                SingletonProgressDialog.dismiss();
                openNextPage();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                SingletonProgressDialog.dismiss();
                relaunchApp(getView().getActivity().getString(R.string.get_locationinfo_fail) + msg);
            }
        });
    }

    private void openNextPage() {
        if (!getModel().isLogin()) {
            getView().openLoginPage();
        } else {
            getView().openMainPage();
        }
        getView().finish();
    }

    private void relaunchApp(String reason) {
        TioToast.showShort(reason);

        new Handler().postDelayed(() -> AppUtils.relaunchApp(), 2000);
    }
}