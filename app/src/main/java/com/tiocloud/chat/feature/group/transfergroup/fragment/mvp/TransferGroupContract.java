package com.tiocloud.chat.feature.group.transfergroup.fragment.mvp;

import android.app.Activity;

import com.tiocloud.chat.feature.group.transfergroup.fragment.adapter.TransferGroupAdapter;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.watayouxiang.httpclient.model.response.ChangeOwnerResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface TransferGroupContract {
    interface View extends BaseView {
        void initRecyclerView();

        TransferGroupAdapter getListAdapter();

        Activity getActivity();

        String getGroupId();
    }

    abstract class Model extends BaseModel {
        public abstract void getApplyGroupFdList(String groupId, String searchKey, DataProxy<ApplyGroupFdListResp> proxy);

        public abstract void postJoinGroup(String groupId, String uids, DataProxy<String> proxy);

        public abstract void changeGroupOwner(String groupId, int uid, final DataProxy<ChangeOwnerResp> proxy);

        public abstract void getGroupUserList(String pageNumber, String groupid, String searchkey, TioCallback<GroupUserListResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void load(String keyWord, int pageNumber);

        public abstract void showTransferGroupConfirmDialog(int uid, String nick);

        public abstract void loadMoreList();

        public abstract void search(String keyWord);
    }
}
