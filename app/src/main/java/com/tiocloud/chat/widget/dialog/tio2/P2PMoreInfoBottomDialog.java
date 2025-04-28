package com.tiocloud.chat.widget.dialog.tio2;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioP2pMoreinfoBottomDialogBinding;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/19
 *     desc   :
 * </pre>
 */
public class P2PMoreInfoBottomDialog extends TioDialog2<TioP2pMoreinfoBottomDialogBinding> {

    public P2PMoreInfoBottomDialog(@NonNull Context context) {
        super(context);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setGravity(Gravity.BOTTOM);
        setAnimation(R.style.tio_bottom_dialog_anim);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.tio_p2p_moreinfo_bottom_dialog;
    }

    @Override
    protected void initContentView() {
        getBinding().tvCancel.setOnClickListener(v -> dismiss());
        getBinding().rlChatTop.setVisibility(View.GONE);
        getBinding().rlDND.setVisibility(View.GONE);
        getBinding().rlFriendInfo.setVisibility(View.GONE);
        getBinding().rlReport.setVisibility(View.GONE);
    }

    // ====================================================================================
    // 举报用户
    // ====================================================================================

    public void initReportInfo(String chatlinkid) {
        getBinding().rlReport.setVisibility(View.VISIBLE);
        getBinding().rlReport.setOnClickListener(v -> showReportDialog(chatlinkid));
    }

    private void showReportDialog(String chatlinkid) {
        dismiss();
        new EasyOperDialog.Builder("确定举报该用户吗？")
                .setPositiveBtnTxt("确定")
                .setNegativeBtnTxt("取消")
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
                .show_unCancel(getContext());
    }

    // 举报用户
    private void requestReport(String chatlinkid) {
        OperReq complaint = OperReq.complaint(chatlinkid);
        complaint.setCancelTag(this);
        complaint.get(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort("举报用户成功，等待后台审核");
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    // ====================================================================================
    // 查看好友信息
    // ====================================================================================

    public void initFriendInfo(String uid) {
        getBinding().rlFriendInfo.setVisibility(View.VISIBLE);
        getBinding().rlFriendInfo.setOnClickListener(v -> {
            UserDetailActivity.start(getContext(), uid);
            dismiss();
        });
    }

    // ====================================================================================
    // 置顶聊天
    // ====================================================================================

    private OnUpdateTopFlagListener onUpdateTopFlagListener;

    /**
     * @param topflag    1 置顶聊天，2 非置顶聊天
     * @param chatlinkid 会话id
     */
    public void initTopChat(int topflag, String chatlinkid, OnUpdateTopFlagListener onUpdateTopFlagListener) {
        this.onUpdateTopFlagListener = onUpdateTopFlagListener;
        String tipTxt = topflag == 1 ? "取消置顶" : "会话置顶";
        getBinding().rlChatTop.setVisibility(View.VISIBLE);
        getBinding().rlChatTop.setOnClickListener(v -> postTopChatOper(topflag, chatlinkid));
        getBinding().tvChatTop.setText(tipTxt);
    }

    // 置顶会话请求
    private void postTopChatOper(int topflag, String chatlinkid) {
        OperReq operReq = OperReq.topChat(chatlinkid, topflag != 1);
        operReq.setCancelTag(this);
        operReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                if (topflag == 1) {
                    if (onUpdateTopFlagListener != null)
                        onUpdateTopFlagListener.onUpdateTopFlag(2);
                } else {
                    if (onUpdateTopFlagListener != null)
                        onUpdateTopFlagListener.onUpdateTopFlag(1);
                }
                TioToast.showShort(s);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismiss();
            }
        });
    }

    public interface OnUpdateTopFlagListener {
        void onUpdateTopFlag(int topFlag);
    }

}
