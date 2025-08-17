package com.tiocloud.account.mvp.logout;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public interface LogoutContract {
    interface View extends BaseView {
    }

    abstract class Model extends BaseModel {
        public abstract void requestLogout(TioCallback<Void> callback);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new LogoutModel(), view);
        }

        public abstract void logout(Activity activity);

        public abstract void showLogoutDialog(Activity activity);
    }
}
