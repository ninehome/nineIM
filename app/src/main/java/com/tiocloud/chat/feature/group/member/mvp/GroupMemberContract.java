package com.tiocloud.chat.feature.group.member.mvp;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ChangeOwnerResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-27
 * desc :
 */
public interface GroupMemberContract {
    interface View extends BaseView {
        String getGroupId();

        void resetUI();

        void onMemberCount(int memberCount);

        void onLoadListSuccess(GroupUserListResp users);

        void onLoadListError(String msg, int pageNumber);

        void setListLongClickEnable(boolean enable);
    }

    abstract class Model extends BaseModel {
        public abstract void getGroupUserList(String pageNumber, String groupid, String searchkey, TioCallback<GroupUserListResp> callback);

        public abstract boolean currUserIsGroupOwner(List<GroupUserListResp.GroupMember> userList);

        public abstract void changeGroupOwner(String groupId, int uid, DataProxy<ChangeOwnerResp> proxy);

        public abstract void postKickGroup(String uids, String groupId, DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void loadMore();

        public abstract void refresh();

        public abstract void search(String keyWord);
    }
}
