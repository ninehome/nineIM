package com.tiocloud.chat.feature.forbidden;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ForbiddenFlagResp;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

public interface ForbiddenMvpContract {
    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void reqForbiddenFlag(String uid, String groupId, TioCallback<ForbiddenFlagResp> callback);

        public abstract void reqForbidden(String mode, String duration, String uid, String groupid, String oper, TioCallback<ForbiddenResp> callback);

        public abstract void reqForbiddenUserList(String pageNumber, String groupid, String searchkey, TioCallback<ForbiddenUserListResp> callback);
    }

    abstract class Presenter extends BasePresenter<Model, BaseView> {
        public Presenter(Model model, boolean registerEvent) {
            super(model, registerEvent);
        }

        public abstract void longClickAvatar(boolean hasKick, String uid, String groupId, View v, OnAitProxy aitProxy);

        public abstract void showLeftListPopup(View v, boolean forbidden, LeftListPopup.OnPopupListener listener);

        public abstract void showSilentTimeSingleChoiceDialog(Context context, SilentTimeSingleChoiceDialog.OnDialogCallback callback);

        public abstract void forbidden(String mode, String duration, String uid, String groupid, String oper, TioCallback<ForbiddenResp> callback);

        public abstract void forbidden(String mode, String duration, String uid, String groupid, String oper);

        public abstract void forbidden(String mode, String duration, String uid, String groupid, String oper, String successTip);

        public abstract void forbidden_cancelUser(String mode, String uid, String groupId);

        public abstract void forbidden_cancelUser(String mode, String uid, String groupId, String successTip);

        public abstract void forbidden_cancelUser(String mode, String uid, String groupId, TioCallback<ForbiddenResp> callback);

        public abstract void forbidden_forever(String uid, String groupId);

        public abstract void forbidden_forever(String uid, String groupId, String successTip);

        public abstract void forbidden_duration(String duration, String uid, String groupId);

        public abstract void forbidden_duration(String duration, String uid, String groupId, String successTip);

        public abstract void toggleSwitch_ForbiddenGroupMember(String groupId, CompoundButton compoundButton, boolean isChecked);

        public abstract void mgrLongClickGroupMemberListItem(View view, int myRole, GroupUserListResp.GroupMember member, String groupId);

        public abstract void showMgrPopup(View v, int myRole, GroupUserListResp.GroupMember member, boolean forbidden, MgrPopup.OnPopupListener listener);
    }

    interface OnAitProxy {
        void insertAitMemberInner();
    }
}
