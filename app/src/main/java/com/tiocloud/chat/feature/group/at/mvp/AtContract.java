package com.tiocloud.chat.feature.group.at.mvp;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.httpclient.model.response.AtGroupUserListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

public interface AtContract {
    interface View extends BaseView {
        void bindContentView();

        void initEditText();

        void initRecyclerView();

        Activity getActivity();

        @NonNull
        String getGroupId();

        void onAtGroupUserListResp(AtGroupUserListResp lists, @Nullable String searchkey);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void onEtTextChanged(CharSequence s);
    }
}
