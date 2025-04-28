package com.tiocloud.chat.feature.search.user.fragment.mvp;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.watayouxiang.httpclient.model.response.UserSearchResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public interface SearchUserFragmentContract {
    interface View extends BaseView {
        Activity getActivity();
    }

    abstract class Model extends BaseModel {
        public abstract void searchUser(String keyWord, int pageNumber, DataProxy<UserSearchResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void initListView(RecyclerView recyclerView, android.view.View emptyView);

        public abstract void search(String keyWord);
    }
}
