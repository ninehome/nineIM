package com.tiocloud.chat.feature.group.silent;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

import java.util.List;

interface MvpContract {
    interface View extends BaseView {
        void resetUI();

        String getGroupId();

        void onLoadSuccess(int pageNumber, List<ListModel> models, ForbiddenUserListResp resp);

        void onLoadError(int pageNumber, String msg);

        void onRemoveListItem(int position);

        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract List<ListModel> forbiddenUserList2Models(List<ForbiddenUserListResp.ListBean> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void load(String keyWord, int pageNumber);

        public abstract void search(String keyWord);

        public abstract void loadMore();

        public abstract void forbidden_cancelUser_confirmDialog(ListNormalItem normalItem, int position);
    }
}
