package com.tiocloud.chat.mvp.dealapply;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.tiocloud.chat.widget.dialog.tio.DealApplyDialog;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.androidutils.mvp.BaseModel;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class DealApplyPresenter extends DealApplyContract.Presenter {
    public DealApplyPresenter(DealApplyContract.View view) {
        super(new DealApplyModel(), view);
    }

    @Override
    public void start(String applyId, @Nullable String remarkName, DealApplyProxy proxy) {
        showDealApplyDialog(applyId, remarkName, proxy);
    }

    // ====================================================================================
    // 显示处理申请弹窗
    // ====================================================================================

    private void showDealApplyDialog(final String applyId, @Nullable final String remarkName, final DealApplyProxy proxy) {
        new DealApplyDialog(remarkName, new DealApplyDialog.OnBtnListener() {
            @Override
            public void onClickPositive(View view, String submitTxt, DealApplyDialog dialog) {
                submitTxt = TextUtils.isEmpty(submitTxt) ? null : submitTxt;
                dealApply(applyId, submitTxt, proxy, dialog);
            }

            @Override
            public void onClickNegative(View view, DealApplyDialog dialog) {
                dialog.dismiss();
                proxy.onCancel();
                proxy.onFinish();
            }
        }).show_unCancel(getView().getContext());
    }

    // ====================================================================================
    // 调用接口
    // ====================================================================================

    private void dealApply(String applyId, @Nullable String remarkName, final DealApplyProxy proxy, final DealApplyDialog dialog) {
        getModel().dealApply(applyId, remarkName, new BaseModel.DataProxy<DealApplyResp>() {
            @Override
            public void onSuccess(DealApplyResp resp) {
                dialog.dismiss();
                proxy.onSuccess(resp);
                proxy.onFinish();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                proxy.onFailure(msg);
                proxy.onFinish();
            }
        });
    }
}
