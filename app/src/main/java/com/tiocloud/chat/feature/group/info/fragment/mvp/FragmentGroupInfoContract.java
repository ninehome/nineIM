package com.tiocloud.chat.feature.group.info.fragment.mvp;

import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.tiocloud.chat.feature.group.info.fragment.adapter.MemberListAdapter;
import com.watayouxiang.httpclient.model.response.DelGroupResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public interface FragmentGroupInfoContract {
    interface View extends BaseView {
        String getGroupId();

        String getChatLinkId();

        TioActivity getTioActivity();

        void initPageUI();

        void setMenuBtn(boolean groupOwner, boolean showExit);

        void setUIData(GroupInfoResp groupInfo);

        MemberListAdapter getMemberListAdapter();

        void setGroupOwnerInfo(GroupUserListResp.GroupMember user);
    }

    abstract class Model extends BaseModel {
        public abstract void getGroupInfo(String userFlag, String groupId, final DataProxy<GroupInfoResp> proxy);

        public abstract void getGroupUserList(String pageNumber, String groupid, DataProxy<GroupUserListResp> proxy);

        public abstract void requestLeaveGroup(String groupId, DataProxy<String> proxy);

        public abstract void requestDelGroup(String groupId, DataProxy<DelGroupResp> proxy);

        public abstract void reqModifyInviteSwitch(final boolean isChecked, String groupId, final DataProxy<String> proxy);

        public abstract void reqClearChatRecord(final String chatLinkId, final DataProxy<String> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void init();

        public abstract void loadUIData();

        public abstract void toggleInviteMemberSwitch(boolean isChecked, CompoundButton compoundButton);

        public abstract void showClearChatRecordDialog();

        public abstract void showOperDialog(boolean groupOwner, boolean showExit);

        public abstract void onClickGroupIntroItem(boolean groupOwner, @NonNull GroupInfoResp.Group group);

        public abstract void onClickGroupNoticeItem(boolean groupOwner, @NonNull GroupInfoResp.Group group);

        public abstract void reqComplaint(String chatLinkId);
    }
}
