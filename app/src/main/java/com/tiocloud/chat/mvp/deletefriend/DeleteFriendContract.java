package com.tiocloud.chat.mvp.deletefriend;

import android.content.Context;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public interface DeleteFriendContract {
    interface View extends BaseView {
        Context getContext();
    }

    abstract class Model extends BaseModel {
        public abstract void delFriend(int uid, boolean forceDel, DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void start(int uid, boolean forceDel, DeleteFriendProxy proxy);

        public abstract static class DeleteFriendProxy {
            public abstract void onSuccess(String data);

            public void onFailure(String msg) {

            }

            public void onCancel() {

            }

            public void onFinish() {

            }
        }
    }
}
