package com.tiocloud.chat.feature.user.applylist.mvp;

import android.app.Activity;

import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface ApplyListContract {
    interface View extends BaseView {
        Activity getActivity();

        void initTitleBar();

        void initRecyclerView();

        void onApplyListResp(ApplyListResp data);

        void onAgreeAddFriend(DealApplyResp data, int position);
    }

    abstract class Model extends BaseModel {
        public abstract void getApplyList(DataProxy<ApplyListResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void requestApplyList();

        public abstract void doAgreeAddFriend(String applyId, String remarkName, int position, android.view.View view);

        public abstract void openUserDetailActivity(ApplyListResp.Data item);
    }
}
