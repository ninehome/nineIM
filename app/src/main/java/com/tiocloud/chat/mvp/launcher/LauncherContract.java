package com.tiocloud.chat.mvp.launcher;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public interface LauncherContract {
    interface View extends BaseView {
        void openLoginPage();

        void finish();

        void openMainPage();

        TioActivity getActivity();
    }

    abstract class Model extends BaseModel {
        public abstract void requestConfig(BaseModel.DataProxy<Void> proxy);

        public abstract boolean isLogin();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new LauncherModel(), view);
        }

        public abstract void init();
    }
}
