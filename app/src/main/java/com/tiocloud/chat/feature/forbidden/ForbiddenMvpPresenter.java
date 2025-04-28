package com.tiocloud.chat.feature.forbidden;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.member.GroupMemberActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.KickGroupReq;
import com.watayouxiang.httpclient.model.request.SetManagerReq;
import com.watayouxiang.httpclient.model.response.ForbiddenFlagResp;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.Locale;

public class ForbiddenMvpPresenter extends ForbiddenMvpContract.Presenter {
    private GroupMemberActivity groupMemberActivity;

    public ForbiddenMvpPresenter(GroupMemberActivity groupMemberActivity) {
        super(new ForbiddenMvpModel(), false);
        this.groupMemberActivity = groupMemberActivity;
    }

    // ====================================================================================
    // 群会话页 - 长按头像
    // ====================================================================================

    @Override
    public void longClickAvatar(boolean hasKick, String uid, String groupId, View v, ForbiddenMvpContract.OnAitProxy aitProxy) {
        // 查询禁言权限
        getModel().reqForbiddenFlag(uid, groupId, new TioCallback<ForbiddenFlagResp>() {
            @Override
            public void onTioSuccess(ForbiddenFlagResp resp) {
                boolean haveForbiddenPermission = resp.haveForbiddenPermission();
                boolean forbidden = resp.isForbidden();
                String forbiddenMode = resp.getForbiddenMode();

                if (haveForbiddenPermission) {
                    // 显示 popup
                    showLeftListPopup(v, forbidden, new LeftListPopup.OnSimplePopupListener() {
                        @Override
                        public void onClickAitItem(LeftListPopup popup) {
                            aitProxy.insertAitMemberInner();
                            popup.dismiss();
                        }

                        @Override
                        public void onClickSilentItem(LeftListPopup popup) {
                            if (forbidden) {
                                // 禁言了，解禁
                                forbidden_cancelUser(forbiddenMode, uid, groupId);
                            } else {
                                // 没禁言，选择禁言时间
                                showSilentTimeSingleChoiceDialog(v.getContext(), (secondTime, dialog) -> {
                                    if (secondTime == -1) {
                                        TioToast.showShort(groupMemberActivity.getString(R.string.unknown_time_length));
                                    } else if (secondTime == Long.MAX_VALUE) {
                                        // 长久禁言
                                        forbidden_forever(uid, groupId);
                                    } else {
                                        // 时常禁言
                                        forbidden_duration(secondTime + "", uid, groupId);
                                    }
                                    dialog.dismiss();
                                });
                            }
                            popup.dismiss();
                        }

                        @Override
                        public void onClickKickItem(LeftListPopup popup) {
                            popup.dismiss();
                            new EasyOperDialog.Builder(ConstantUtils.context.getString(R.string.confirm_kickout_remember))
                                    .setPositiveBtnTxt(ConstantUtils.context.getString(R.string.kickout))
                                    .setNegativeBtnTxt(ConstantUtils.context.getString(R.string.exit))
                                    .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                                        @Override
                                        public void onClickPositive(View view, EasyOperDialog dialog) {
                                            toKick(groupId, String.valueOf(uid));
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onClickNegative(View view, EasyOperDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .build()
                                    .show_unCancel(v.getContext());
                            popup.dismiss();
                        }
                    });
                } else {
                    aitProxy.insertAitMemberInner();
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void showLeftListPopup(View v, boolean forbidden, LeftListPopup.OnPopupListener listener) {
        new LeftListPopup(v.getContext(), forbidden)
                .setOnPopupListener(listener)
                .show(v);
    }

    @Override
    public void showSilentTimeSingleChoiceDialog(Context context, SilentTimeSingleChoiceDialog.OnDialogCallback callback) {
        new SilentTimeSingleChoiceDialog(context)
                .setOnDialogCallback(callback)
                .show();
    }

    // ====================================================================================
    // 禁言操作
    // ====================================================================================

    @Override
    public void forbidden(String mode, String duration, String uid, String groupid, String oper, TioCallback<ForbiddenResp> callback) {
        getModel().reqForbidden(mode, duration, uid, groupid, oper, callback);
    }

    @Override
    public void forbidden(String mode, String duration, String uid, String groupid, String oper) {
        forbidden(mode, duration, uid, groupid, oper, new TioCallback<ForbiddenResp>() {
            @Override
            public void onTioSuccess(ForbiddenResp forbiddenResp) {
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void forbidden(String mode, String duration, String uid, String groupid, String oper, String successTip) {
        forbidden(mode, duration, uid, groupid, oper, new TioCallback<ForbiddenResp>() {
            @Override
            public void onTioSuccess(ForbiddenResp forbiddenResp) {
                TioToast.showShort(successTip);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void forbidden_cancelUser(String mode, String uid, String groupId) {
        forbidden(mode, null, uid, groupId, "2");
    }

    @Override
    public void forbidden_cancelUser(String mode, String uid, String groupId, String successTip) {
        forbidden(mode, null, uid, groupId, "2", successTip);
    }

    @Override
    public void forbidden_cancelUser(String mode, String uid, String groupId, TioCallback<ForbiddenResp> callback) {
        forbidden(mode, null, uid, groupId, "2", callback);
    }

    @Override
    public void forbidden_forever(String uid, String groupId) {
        forbidden("3", null, uid, groupId, "1");
    }

    @Override
    public void forbidden_forever(String uid, String groupId, String successTip) {
        forbidden("3", null, uid, groupId, "1", successTip);
    }

    @Override
    public void forbidden_duration(String duration, String uid, String groupId) {
        forbidden("1", duration, uid, groupId, "1");
    }

    @Override
    public void forbidden_duration(String duration, String uid, String groupId, String successTip) {
        forbidden("1", duration, uid, groupId, "1", successTip);
    }

    // ====================================================================================
    // 群禁言开关
    // ====================================================================================

    @Override
    public void toggleSwitch_ForbiddenGroupMember(String groupId, CompoundButton compoundButton, boolean isChecked) {
        getModel().reqForbidden("4", null, null, groupId, isChecked ? "1" : "2", new TioCallback<ForbiddenResp>() {
            @Override
            public void onTioSuccess(ForbiddenResp forbiddenResp) {
            }

            @Override
            public void onTioError(String msg) {
                compoundButton.setChecked(!isChecked);
            }
        });
    }

    // ====================================================================================
    // 群成员列表页 - 长按 "列表项"
    // ====================================================================================

    @Override
    public void mgrLongClickGroupMemberListItem(View v, int myRole, GroupUserListResp.GroupMember member, String groupId) {
        // 查询禁言权限
        getModel().reqForbiddenFlag(member.uid+"", groupId, new TioCallback<ForbiddenFlagResp>() {
            @Override
            public void onTioSuccess(ForbiddenFlagResp resp) {
                boolean haveForbiddenPermission = resp.haveForbiddenPermission();
                boolean forbidden = resp.isForbidden();
                String forbiddenMode = resp.getForbiddenMode();
                if (haveForbiddenPermission) {

                    // 显示禁言弹窗
                    showMgrPopup(v, myRole, member, forbidden, new MgrPopup.OnSimplePopupListener() {
                        @Override
                        public void onClickSilentItem(MgrPopup popup) {
                            if (forbidden) {
                                // 取消禁言
                                forbidden_cancelUser(forbiddenMode, member.uid+"", groupId,
                                        groupMemberActivity.getString(R.string.exit_forbiden_language_success));
                            } else {
                                // 显示禁言时长选择弹窗
                                showSilentTimeSingleChoiceDialog(v.getContext(), (secondTime, dialog) -> {
                                    if (secondTime == -1) {
                                        TioToast.showShort(groupMemberActivity.getString(R.string.unknown_time_length));
                                    } else if (secondTime == Long.MAX_VALUE) {
                                        // 长久禁言
                                        forbidden_forever(member.uid+"", groupId, groupMemberActivity.getString(R.string.forbiden_language_success));
                                    } else {
                                        // 时常禁言
                                        forbidden_duration(secondTime + "", member.uid+"", groupId, groupMemberActivity.getString(R.string.forbiden_language_success));
                                    }
                                    dialog.dismiss();
                                });
                            }
                            popup.dismiss();
                        }

                        @Override
                        public void onClickSetManager(MgrPopup popup) {
                            int setRole = member.grouprole == 2 ? 3: 2;
                            SetManagerReq setManagerReq = new SetManagerReq(member.uid, groupId, setRole);
                            setManagerReq.post(new TioCallback<String>() {
                                @Override
                                public void onTioSuccess(String s) {
                                    ToastUtils.showShort(setRole == 3?groupMemberActivity.getString(R.string.set_success)
                                            :groupMemberActivity.getString(R.string.exit_success));
                                    member.grouprole = setRole;
                                    if (groupMemberActivity != null){
                                        groupMemberActivity.refresh();
                                    }
                                }

                                @Override
                                public void onTioError(String msg) {
                                    ToastUtils.showShort(msg);
                                }
                            });
                            popup.dismiss();
                        }

                        @Override
                        public void onClickDeleteMember(MgrPopup popup) {
                            new EasyOperDialog.Builder(groupMemberActivity.getString(R.string.confirm_delete_remember))
                                    .setPositiveBtnTxt(groupMemberActivity.getString(R.string.delete))
                                    .setNegativeBtnTxt(groupMemberActivity.getString(R.string.exit))
                                    .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                                        @Override
                                        public void onClickPositive(View view, EasyOperDialog dialog) {
                                            toKick(member.groupid, String.valueOf(member.uid));
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onClickNegative(View view, EasyOperDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .build()
                                    .show_unCancel(groupMemberActivity);
                            popup.dismiss();
                        }
                    });

                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void showMgrPopup(View v, int myRole, GroupUserListResp.GroupMember member, boolean forbidden, MgrPopup.OnPopupListener listener) {
        new MgrPopup(v.getContext(), myRole, member, forbidden)
                .setOnPopupListener(listener)
                .show(v);
    }

    public void toKick(String groupId, String uid){
        KickGroupReq kickGroupReq = new KickGroupReq(uid, groupId);
        TioHttpClient.post(this, kickGroupReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {
                BaseResp<String> body = response.body();
                String data = body.getData();
                ToastUtils.showShort(data);
                if (groupMemberActivity != null){
                    groupMemberActivity.refreshData();
                }
            }

            @Override
            public void onError(Response<BaseResp<String>> response) {
                super.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
