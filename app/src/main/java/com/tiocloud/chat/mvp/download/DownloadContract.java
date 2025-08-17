package com.tiocloud.chat.mvp.download;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

public interface DownloadContract {
    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }
    }

    abstract class Presenter extends BasePresenter<Model, BaseView> {
        public Presenter(Model model, boolean registerEvent) {
            super(model, null, registerEvent);
        }

        public abstract void downloadWithTip(String url, Activity activity);
    }
}
