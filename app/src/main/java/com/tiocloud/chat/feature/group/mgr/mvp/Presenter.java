package com.tiocloud.chat.feature.group.mgr.mvp;

import android.widget.CompoundButton;

import com.tiocloud.chat.feature.forbidden.ForbiddenMvpPresenter;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;

public class Presenter extends Contract.Presenter {
    private final ForbiddenMvpPresenter forbiddenPresenter = new ForbiddenMvpPresenter(null);

    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void detachView() {
        super.detachView();
        forbiddenPresenter.detachView();
    }

    @Override
    public void init() {
        getView().resetUI();
    }

    @Override
    public void refresh() {
        // 获取群聊信息
        getModel().getGroupInfo("1", getView().getGroupId(), new BaseModel.DataProxy<GroupInfoResp>() {
            @Override
            public void onSuccess(GroupInfoResp groupInfo) {
                final GroupInfoResp.GroupUser groupUser = groupInfo.groupuser;
                final GroupInfoResp.Group group = groupInfo.group;
                if (groupUser == null || group == null) {
                    TioToast.showShort("groupUser or group is null");
                    return;
                }
                getView().onGroupInfoResp(groupInfo);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
        // 禁言列表
        forbiddenPresenter.getModel().reqForbiddenUserList("1", getView().getGroupId(), null, new TioCallback<ForbiddenUserListResp>() {
            @Override
            public void onTioSuccess(ForbiddenUserListResp resp) {
                getView().onForbiddenUserListResp(resp);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void toggleInviteMemberSwitch(final boolean isChecked, final CompoundButton compoundButton) {
        getModel().reqModifyInviteSwitch(isChecked, getView().getGroupId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                compoundButton.setChecked(!isChecked);
            }
        });
    }

    @Override
    public void inviteMemberVerifySwitch(boolean isChecked, CompoundButton compoundButton) {
        getModel().reqInviteVerifySwitch(isChecked, getView().getGroupId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                compoundButton.setChecked(!isChecked);
            }
        });
    }

    @Override
    public void banP2pChatSwitch(boolean isChecked, CompoundButton compoundButton) {
        getModel().reqBanP2pSwitch(isChecked, getView().getGroupId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                compoundButton.setChecked(!isChecked);
            }
        });
    }

    @Override
    public void banMemberExitSwitch(boolean isChecked, CompoundButton compoundButton) {
        getModel().reqBanExitSwitch(isChecked, getView().getGroupId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                compoundButton.setChecked(!isChecked);
            }
        });
    }
}
