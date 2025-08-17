package com.tiocloud.chat.widget.dialog.tio2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.TioHttpClient;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/19
 *     desc   : 新版弹窗
 * </pre>
 */
public abstract class TioDialog2<B extends ViewDataBinding> {

    @NonNull
    private final Dialog dialog;
    @Nullable
    private DialogInterface.OnCancelListener onCancelListener;
    @NonNull
    private final B binding;

    public TioDialog2(@NonNull Context context) {
        dialog = new Dialog(context, R.style.tio_dialog);

        // 返回键取消
        dialog.setCancelable(false);
        // 返回键取消 && 点击弹窗外部取消
        dialog.setCanceledOnTouchOutside(false);
        // 取消监听
        dialog.setOnCancelListener(dialog -> {
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialog);
            }
            dismiss();
        });

        // 设置布局
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutResID(), null, false);
        dialog.setContentView(binding.getRoot());
        // 初始化布局
        initContentView();

        // 设置宽高
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置位置
        setGravity(Gravity.CENTER);
    }

    @LayoutRes
    protected abstract int getLayoutResID();

    protected void initContentView() {

    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
        TioHttpClient.cancel(this);
    }

    @NonNull
    public Context getContext() {
        return dialog.getContext();
    }

    protected final <T extends View> T findViewById(@IdRes int id) {
        return dialog.findViewById(id);
    }

    /**
     * {@link WindowManager.LayoutParams#MATCH_PARENT}
     * {@link WindowManager.LayoutParams#WRAP_CONTENT}
     */
    public void setHeight(int height) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.height = height;
            window.setAttributes(attributes);
        }
    }

    /**
     * {@link WindowManager.LayoutParams#MATCH_PARENT}
     * {@link WindowManager.LayoutParams#WRAP_CONTENT}
     */
    public void setWidth(int width) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = width;
            window.setAttributes(attributes);
        }
    }

    /**
     * {@link Gravity}
     */
    public void setGravity(int gravity) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
    }

    public void setAnimation(@StyleRes int resId) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(resId);
        }
    }

    public void setOnCancelListener(@Nullable DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @NonNull
    public B getBinding() {
        return binding;
    }

    @Nullable
    public Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    @NonNull
    public Dialog getDialog() {
        return dialog;
    }
}