package com.tiocloud.chat.mvp.addfriend;

import android.app.Activity;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public interface AddFriendContract {
    abstract class Model extends BaseModel {
        public abstract void init(final DataProxy<UserCurrResp> proxy);

        public abstract String getCurrUserNick();

        public abstract void checkAddFriend(final int uid, final DataProxy<Integer> proxy);

        public abstract void postFriendApply(final int uid, final String greet, final DataProxy<FriendApplyResp> proxy);

        public abstract void postAddFriend(int uid, DataProxy<AddFriendResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, BaseView> {
        public Presenter(Model model) {
            super(model);
        }

        public abstract void checkStart(int uid, boolean forceAddorDel, AddFriendProxy proxy, Activity activity);

        public abstract void uncheckStart(int uid, AddFriendProxy proxy, Activity activity);

        public abstract static class AddFriendProxy {
            /**
             * 非验证的好友
             *
             * @param data
             */
            public abstract void onAddFriendResp(AddFriendResp data);

            /**
             * 好友申请
             *
             * @param data
             */
            public void onFriendApplyResp(FriendApplyResp data) {

            }

            /**
             * 点击弹窗取消按钮
             */
            public void onClickDialogNegativeBtn() {

            }

            public void onFailure(String msg) {

            }

            public void onFinish() {

            }
        }
    }
}
