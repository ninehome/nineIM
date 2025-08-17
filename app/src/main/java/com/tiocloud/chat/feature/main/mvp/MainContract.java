package com.tiocloud.chat.feature.main.mvp;

import android.app.Activity;

import com.tiocloud.chat.feature.main.MainActivity;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public interface MainContract {
    interface View extends BaseView {
        Activity getActivity();

        MainActivity getMainActivity();

        void initViews();

        void updateRedDot(int pageIndex, int count);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void checkAppUpdate();

        public abstract void clearAllNotifications();
    }
}
