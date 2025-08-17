package com.tiocloud.chat.feature.group.removemember.fragment.mvp;

import android.app.Activity;
import android.widget.TextView;

import com.tiocloud.chat.feature.group.removemember.fragment.adapter.RemoveMemberAdapter;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface FragmentRemoveMemberContract {
    interface View extends BaseView {
        Activity getActivity();

        String getGroupId();

        void initRecyclerView();

        RemoveMemberAdapter getListAdapter();
    }

    abstract class Model extends BaseModel {
        public abstract void getGroupUserList(String pageNumber, String groupid, String searchKey, DataProxy<GroupUserListResp> proxy);

        public abstract void postKickGroup(String uids, String groupId, DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void installMenuBtn(TextView tv_menuBtn);

        public abstract void loadMore();

        public abstract void searchKey(String keyWord);
    }
}
