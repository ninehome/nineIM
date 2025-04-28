package com.tiocloud.chat.feature.home.friend.mvp;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTaskProxy;
import com.tiocloud.chat.feature.main.fragment.MainFriendFragment;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc :
 */
public interface FriendContract {

    interface View extends BaseView {
        Activity getActivity();

        @Nullable
        MainFriendFragment getMainFriendFragment();
    }

    abstract class Model extends BaseModel {
        public abstract void requestMailList(IData data, MailListTaskProxy proxy);

        public abstract void requestApplyData(TioCallback<Integer> proxy);

        public abstract void setFriendNum(@Nullable MainFriendFragment fragment, int friendNum);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new FriendModel(), view, true);
        }

        public abstract void initRefreshView();

        public abstract void initListView();

        public abstract void load();
    }
}
