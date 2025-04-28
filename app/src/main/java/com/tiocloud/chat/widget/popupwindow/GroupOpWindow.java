package com.tiocloud.chat.widget.popupwindow;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.curr.modify.ModifyActivity;
import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.share.group.ShareGroupActivity;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.LeaveGroupReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/22
 *     desc   :
 * </pre>
 */
public class GroupOpWindow extends BaseHomeWindow {

    private TextView tv_shareGroup;
    private TextView tv_btn2;

    public GroupOpWindow(View anchor) {
        super(anchor);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.popup_window_group_operation;
    }

    @Override
    protected void initViews() {
        tv_shareGroup = findViewById(R.id.tv_shareGroup);
        tv_btn2 = findViewById(R.id.tv_btn2);
    }

    public boolean show(@Nullable MailListResp.Group group) {
        if (group == null) return false;

        // data
        String groupOwnerUid = String.valueOf(group.uid);
        boolean isGroupOwner = groupOwnerUid.equals(String.valueOf(TioDBPreferences.getCurrUid()));

        // ui
        tv_shareGroup.setOnClickListener(view -> {
            ShareGroupActivity.start(getActivity(), group.groupid);
            dismiss();
        });
        if (isGroupOwner) {
            tv_btn2.setText("修改群名称");
            tv_btn2.setOnClickListener(view -> {
                ModifyActivity.start_group(view.getContext(), ModifyType.GROUP_NAME, group.groupid, group.name);
                dismiss();
            });
        } else {
            tv_btn2.setText("退出群聊");
            tv_btn2.setOnClickListener(view -> {
                showExitGroupDialog(view, group.groupid);
                dismiss();
            });
        }

        // 显示窗口
        super.show();
        return true;
    }

    private void showExitGroupDialog(View view, String groupId) {
        new EasyOperDialog.Builder(getActivity().getString(R.string.quedingtuichuqunliao))
                .setPositiveBtnTxt(getActivity().getString(R.string.tuichu))
                .setNegativeBtnTxt(getActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        reqExitGroup(dialog, groupId);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(view.getContext());
    }

    private void reqExitGroup(EasyOperDialog dialog, String groupId) {
        LeaveGroupReq leaveGroupReq = new LeaveGroupReq(String.valueOf(TioDBPreferences.getCurrUid()), groupId);
        leaveGroupReq.setCancelTag(this);
        leaveGroupReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                dialog.dismiss();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }
}
