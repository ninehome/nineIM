package com.tiocloud.chat.feature.group.invitemember.fragment.mvp;

import android.app.Activity;
import android.widget.TextView;

import com.tiocloud.chat.feature.group.invitemember.fragment.adapter.InviteMemberAdapter;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface InviteMemberContract {
    interface View extends BaseView {
        void initRecyclerView();

        InviteMemberAdapter getListAdapter();

        Activity getActivity();

        String getGroupId();
    }

    abstract class Model extends BaseModel {
        public abstract void getApplyGroupFdList(String groupId, String searchKey, DataProxy<ApplyGroupFdListResp> proxy);

        public abstract void postJoinGroup(String groupId, String uids, DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void search(String keyWord);

        public abstract void installMenuBtn(TextView tv_menuBtn);
    }
}
