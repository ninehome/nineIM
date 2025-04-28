package com.tiocloud.chat.feature.group.info.fragment.mvp;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.curr.modify.ModifyActivity;
import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.group.transfergroup.TransferGroupActivity;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.widget.dialog.base.GroupOperDialog;
import com.watayouxiang.androidutils.widget.CommonTextInputDialog;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ModifyIntroReq;
import com.watayouxiang.httpclient.model.request.ModifyNoticeReq;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.httpclient.model.response.DelGroupResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.page.TioActivity;
import com.tiocloud.chat.util.ScreenUtil;
import com.watayouxiang.androidutils.util.SpanUtils;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.imclient.engine.TioEventEngine;
import com.watayouxiang.imclient.event.ClearChatMsg;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public class FragmentGroupInfoPresenter extends FragmentGroupInfoContract.Presenter {

    public FragmentGroupInfoPresenter(FragmentGroupInfoContract.View view) {
        super(new FragmentGroupInfoModel(), view);
    }

    @Override
    public void init() {
        getView().initPageUI();
    }

    @Override
    public void loadUIData() {
        // 获取群聊信息
        getModel().getGroupInfo("1", getView().getGroupId(), new BaseModel.DataProxy<GroupInfoResp>() {
            @Override
            public void onSuccess(GroupInfoResp groupInfo) {
                final GroupInfoResp.GroupUser groupUser = groupInfo.groupuser;
                final GroupInfoResp.Group group = groupInfo.group;
                if (groupUser == null || group == null) return;

                final boolean groupOwner = groupUser.grouprole == 1;
                final boolean isManager = groupUser.grouprole == 1 || groupUser.grouprole == 3;
                final boolean showInviteMember = group.applyflag == 1;
                final boolean showExit = !(group.exitflag == 2 && groupUser.grouprole == 2);
                // 群信息
                getView().setUIData(groupInfo);
                // 是否显示删除成员按钮
                getView().setMenuBtn(isManager, showExit);
                // 获取群成员列表
                reqMemberListData(isManager, showInviteMember);
            }

            private void reqMemberListData(final boolean groupOwner, final boolean showInviteMember) {
                getModel().getGroupUserList(String.valueOf(1), getView().getGroupId(), new BaseModel.DataProxy<GroupUserListResp>() {
                    @Override
                    public void onSuccess(GroupUserListResp groupUserList) {
                        getView().getMemberListAdapter().setNewData(groupOwner, groupUserList, showInviteMember);
                        if (groupUserList.list.size() > 0) {
                            getView().setGroupOwnerInfo(groupUserList.list.get(0));
                        }
                    }
                });
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
    public void showClearChatRecordDialog() {
        final TioActivity context = getView().getTioActivity();
        new EasyOperDialog.Builder(getView().getTioActivity().getString(R.string.quedingshanchuqunjilu))
                .setPositiveBtnTxt(getView().getTioActivity().getString(R.string.confirm))
                .setNegativeBtnTxt(getView().getTioActivity().getString(R.string.cancel))
                .setPosBtnRedRoundBg()
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, final EasyOperDialog dialog) {
                        reqClearChatRecord(dialog, context);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(context);
    }

    private void reqClearChatRecord(final EasyOperDialog dialog, final TioActivity context) {
        getModel().reqClearChatRecord(getView().getChatLinkId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                TioToast.show(context, s);
                dialog.dismiss();

                ChatListTable table = ChatListTableCrud.query(getView().getChatLinkId());
                if (table != null) {
                    // 如果存在，则更新
                    table.setMsgresume("");
                    table.setAtnotreadcount(0);
                    table.setNotreadcount(0);
                    ChatListTableCrud.update(table);
                }
                new TioEventEngine().post(new ClearChatMsg(getView().getChatLinkId()));
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(context, msg);
            }
        });
    }

    private GroupOperDialog operDialog;

    @Override
    public void showOperDialog(boolean groupOwner, boolean showExit) {
        if (operDialog == null) {
            operDialog = new GroupOperDialog(getView().getTioActivity()) {
                @Override
                protected void onClick(GroupOperDialog dialog, View v) {
                    super.onClick(dialog, v);
                    if (v.getId() == R.id.tv_exitGroup) {// 退群
                        if (!showExit){
                            ToastUtils.showShort(getContext().getString(R.string.dangqianbuzhichituqun));
                        }else {
                            showExitGroupDialog();
                        }
                    } else if (v.getId() == R.id.tv_dissolveGroup) {// 解散群
                        showDissolveGroupDialog();
                    } else if (v.getId() == R.id.tv_transferGroup) {// 转让群
                        TransferGroupActivity.start(getView().getTioActivity(), getView().getGroupId());
                    }
                    dialog.cancel();
                }
            };
        }
        operDialog.show(groupOwner, showExit);
    }

    @Override
    public void onClickGroupIntroItem(boolean groupOwner, @NonNull GroupInfoResp.Group group) {
        if (groupOwner) {
            ModifyActivity.start_group(getView().getTioActivity(), ModifyType.GROUP_INTRO, group.id, group.intro, false, true);
//            new CommonTextInputDialog(getView().getTioActivity())
//                    .setEdittext(group.intro)
//                    .setTopTitle("群简介")
//                    .setSubTitle("一个好的群简介，能让人迅速了解这个群")
//                    .setEditHeight(100)
//                    .setPositiveText("确定")
//                    .setMaxLimit(100)
//                    .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
//                        @Override
//                        public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
//                            dialog.dismiss();
//                            ModifyIntroReq req = new ModifyIntroReq(group.id, submitTxt);
//                            req.setCancelTag(this);
//                            req.post(new TioCallback<Void>() {
//                                @Override
//                                public void onTioSuccess(Void aVoid) {
//                                    loadUIData();
//                                }
//
//                                @Override
//                                public void onTioError(String msg) {
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onClickNegative(View view, CommonTextInputDialog dialog) {
//                            dialog.dismiss();
//                        }
//                    }).show();
        } else {
            ModifyActivity.start_group(getView().getTioActivity(), ModifyType.GROUP_INTRO, group.id, group.intro, false, false);
//            SpannableStringBuilder format = SpanUtils.getBuilder("群简介")
//                    .setTexSize(ScreenUtil.sp2px(18))
//                    .append(String.format(Locale.getDefault(), "\n\n%s", StringUtil.nonNull(group.intro)))
//                    .setTexSize(ScreenUtil.sp2px(16))
//                    .create();
//            new TioConfirmDialog(format, new TioConfirmDialog.OnConfirmListener() {
//                @Override
//                public void onConfirm(View view, TioConfirmDialog dialog) {
//                    dialog.dismiss();
//                }
//            }).show_unCancel(getView().getTioActivity());
        }
    }

    @Override
    public void onClickGroupNoticeItem(boolean groupOwnerOrManager, @NonNull GroupInfoResp.Group group) {
        if (groupOwnerOrManager) {
//            ModifyActivity.start_group(getView().getTioActivity(), ModifyType.GROUP_NOTICE, group.id, group.notice);
            new CommonTextInputDialog(getView().getTioActivity())
                    .setEdittext(group.notice)
                    .setTopTitle(getView().getTioActivity().getString(R.string.group_notice))
                    .hintSubTitle()
                    .setEditHeight(100)
                    .setPositiveText(getView().getTioActivity().getString(R.string.confirm))
                    .setMaxLimit(100)
                    .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                        @Override
                        public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                            dialog.dismiss();
                            ModifyNoticeReq req = new ModifyNoticeReq(group.id, submitTxt);
                            req.setCancelTag(this);
                            req.post(new TioCallback<Void>() {
                                @Override
                                public void onTioSuccess(Void aVoid) {
                                    loadUIData();
                                }

                                @Override
                                public void onTioError(String msg) {
                                }
                            });
                        }

                        @Override
                        public void onClickNegative(View view, CommonTextInputDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            String noticetime = group.noticetime;
            String time = TimeUtil.dateLong2String(TimeUtil.dateString2Long(noticetime), "yyyy-MM-dd");
            String formatTime = TextUtils.isEmpty(time) ? "" : String.format(Locale.getDefault(), "（%s）", time);

            SpannableStringBuilder format = SpanUtils
                    // 标题
                    .getBuilder(getView().getTioActivity().getString(R.string.group_notice))
                    .setTexSize(ScreenUtil.sp2px(18))
                    // 时间
                    .append(formatTime)
                    .setForegroundColor(Color.parseColor("#FF909090"))
                    .setTexSize(ScreenUtil.sp2px(16))
                    // 内容
                    .append(String.format(Locale.getDefault(), "\n\n%s", StringUtil.nonNull(group.notice)))
                    .setTexSize(ScreenUtil.sp2px(16))

                    .create();

            new TioConfirmDialog(format, new TioConfirmDialog.OnConfirmListener() {
                @Override
                public void onConfirm(View view, TioConfirmDialog dialog) {
                    dialog.dismiss();
                }
            }).show_unCancel(getView().getTioActivity());
        }
    }

    @Override
    public void reqComplaint(String chatLinkId) {
        showReportDialog(chatLinkId);
    }

    private void showReportDialog(String chatlinkid) {
        new EasyOperDialog.Builder(getView().getTioActivity().getString(R.string.quedingtousugaiqun))
                .setPositiveBtnTxt(getView().getTioActivity().getString(R.string.confirm))
                .setNegativeBtnTxt(getView().getTioActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        requestReport(chatlinkid);
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getView().getTioActivity());
    }

    private void requestReport(String chatLinkId) {
        OperReq operReq = OperReq.complaint(chatLinkId);
        operReq.setCancelTag(this);
        operReq.get(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(getView().getTioActivity().getString(R.string.tousuchenggong));
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private void showDissolveGroupDialog() {
        new EasyOperDialog.Builder(getView().getTioActivity().getString(R.string.jiesnahou))
                .setPositiveBtnTxt(getView().getTioActivity().getString(R.string.dissolve_group))
                .setNegativeBtnTxt(getView().getTioActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        reqDelGroup(dialog);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getView().getTioActivity());
    }

    private void reqDelGroup(final EasyOperDialog dialog) {
        getModel().requestDelGroup(getView().getGroupId(), new BaseModel.DataProxy<DelGroupResp>() {
            @Override
            public void onSuccess(DelGroupResp resp) {
                MainActivity.start(getView().getTioActivity());
                dialog.dismiss();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(getView().getTioActivity(), msg);
            }
        });
    }

    private void showExitGroupDialog() {
        new EasyOperDialog.Builder(getView().getTioActivity().getString(R.string.quedingtuichuqunliao))
                .setPositiveBtnTxt(getView().getTioActivity().getString(R.string.tuichu))
                .setNegativeBtnTxt(getView().getTioActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        reqExitGroup(dialog);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getView().getTioActivity());
    }

    private void reqExitGroup(final EasyOperDialog dialog) {
        getModel().requestLeaveGroup(getView().getGroupId(), new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String resp) {
                MainActivity.start(getView().getTioActivity());
                dialog.dismiss();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(getView().getTioActivity(), msg);
            }
        });
    }
}
