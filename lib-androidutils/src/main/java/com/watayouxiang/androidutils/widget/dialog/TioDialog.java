package com.watayouxiang.androidutils.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.androidutils.R;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : tio弹窗
 */
public abstract class TioDialog {

    private Dialog dialog;
    private View contentView;
    private Context context;

    // ====================================================================================
    // 显隐
    // ====================================================================================

    /**
     * @param context                上下文
     * @param cancelable             是否可以取消
     * @param canceledOnTouchOutside 点击外部是否可以取消
     * @param onCancelListener       取消监听
     */
    public void show(@NonNull Context context, boolean cancelable, boolean canceledOnTouchOutside,
                     @Nullable final DialogInterface.OnCancelListener onCancelListener) {
        this.context = context;
        dialog = new Dialog(context, R.style.tio_dialog);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
                if (onCancelListener != null) {
                    onCancelListener.onCancel(dialog);
                }
            }
        });
        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            // contentView
            contentView = window.getLayoutInflater().inflate(getDialogContentId(), null);
            initDialogContentView();
            window.setContentView(contentView);
            // attributes
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(attributes);
            // gravity
            window.setGravity(Gravity.CENTER);
        }
    }

    public void showBottom(@NonNull Context context, boolean cancelable, boolean canceledOnTouchOutside) {
        this.context = context;
        dialog = new Dialog(context, R.style.tio_dialog);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            // contentView
            contentView = window.getLayoutInflater().inflate(getDialogContentId(), null);
            initDialogContentView();
            window.setContentView(contentView);
            // attributes
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(attributes);
            // gravity
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public void show_unCancel(@NonNull Context context) {
        show(context, false, false, null);
    }

    public void show_canceledOnTouchOutside(@NonNull Context context) {
        show(context, true, true, null);
    }

    public void show_cancelable(@NonNull Context context) {
        show(context, true, false, null);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    // ====================================================================================
    // 构造
    // ====================================================================================

    @LayoutRes
    protected abstract int getDialogContentId();

    protected void initDialogContentView() {

    }

    // ====================================================================================
    // 增强
    // ====================================================================================

    @Nullable
    public Context getContext() {
        return context;
    }

    protected final <T extends View> T findViewById(@IdRes int id) {
        if (contentView == null)
            throw new NullPointerException("rootView is null");
        return contentView.findViewById(id);
    }

}