package com.tiocloud.chat.feature.settings.mvp;

import android.content.Context;
import android.content.pm.PackageManager;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UpdatSearchFlagReq;
import com.watayouxiang.httpclient.model.request.UpdateValidReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class SettingsModel extends SettingsContract.Model {
    @Override
    public void requestCurrUserInfo(final DataProxy<UserCurrResp> proxy) {
        UserCurrReq userCurrReq = new UserCurrReq();
        userCurrReq.setCancelTag(this);
        userCurrReq.get(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                proxy.onSuccess(userCurrResp);
            }

            @Override
            public void onTioError(String msg) {
                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public String getOutApkTime(Context context) {
        try {
            return context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("OUT_APK_TIME");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateValidReq(boolean isChecked, final DataProxy<String> proxy) {
        TioHttpClient.post(this, new UpdateValidReq(isChecked), new TioCallbackImpl<Void>() {
            @Override
            public void onSuccess(Response<BaseResp<Void>> response) {
                BaseResp<Void> body = response.body();
                if (body.isOk()) {
                    proxy.onSuccess(body.getMsg());
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<Void>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                proxy.onFinish();
            }
        });
    }

    @Override
    public void updateSearchFlagReq(boolean isChecked, final DataProxy<String> proxy) {
        TioHttpClient.post(this, new UpdatSearchFlagReq(isChecked), new TaoCallback<BaseResp<Void>>() {
            @Override
            public void onSuccess(Response<BaseResp<Void>> response) {
                BaseResp<Void> body = response.body();
                if (body.isOk()) {
                    proxy.onSuccess(body.getMsg());
                } else {
                    proxy.onFailure(body.getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<Void>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                proxy.onFinish();
            }
        });
    }
}
