package com.tiocloud.account.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountCancelConfirmDialogBinding;
import com.watayouxiang.androidutils.widget.dialog2.BaseBindingDialog;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/25
 *     desc   :
 * </pre>
 */
public class CancelConfirmDialog extends BaseBindingDialog<AccountCancelConfirmDialogBinding> {
    public CancelConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.account_cancel_confirm_dialog;
    }
}
