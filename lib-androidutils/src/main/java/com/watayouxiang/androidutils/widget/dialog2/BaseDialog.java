package com.watayouxiang.androidutils.widget.dialog2;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.watayouxiang.androidutils.R;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/11
 *     desc   :
 * </pre>
 */
public class BaseDialog extends Dialog {
    // 返回键可以取消
    private static final boolean cancelable = true;
    // 点击弹窗外部可取消
    private static final boolean canceledOnTouchOutside = false;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.tio_dialog);

        setCancelable(cancelable);
        setCanceledOnTouchOutside(canceledOnTouchOutside);

        setGravity(Gravity.CENTER);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // setAnimation(R.style.tio_dialog_anim);
    }

    public void setAnimation(@StyleRes int resId) {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(resId);
        }
    }

    /**
     * {@link Gravity#BOTTOM}
     * {@link Gravity#TOP}
     * {@link Gravity#CENTER}
     */
    public void setGravity(int gravity) {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
    }

    /**
     * {@link WindowManager.LayoutParams#WRAP_CONTENT}
     * {@link WindowManager.LayoutParams#MATCH_PARENT}
     */
    public void setWidth(int width) {
        Window window = getWindow();
        if (window != null) {
            // attributes
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = width;
            window.setAttributes(attributes);
        }
    }

    /**
     * {@link WindowManager.LayoutParams#WRAP_CONTENT}
     * {@link WindowManager.LayoutParams#MATCH_PARENT}
     */
    public void setHeight(int height) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.height = height;
            window.setAttributes(attributes);
        }
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
        dismiss();
    }

}
