package com.tiocloud.chat.feature.settings.mvp;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.tiocloud.chat.feature.settings.SettingsActivity;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public interface SettingsContract {
    interface View extends BaseView {
        FragmentActivity getActivity();

        SettingsActivity.ViewHolder getViewHolder();
    }

    abstract class Model extends BaseModel {
        public abstract void requestCurrUserInfo(DataProxy<UserCurrResp> proxy);

        public abstract String getOutApkTime(Context context);

        public abstract void updateValidReq(boolean isChecked, DataProxy<String> proxy);

        public abstract void updateSearchFlagReq(boolean isChecked, DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();
    }
}
