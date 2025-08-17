package com.watayouxiang.androidutils.widget.dialog.progress;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ThreadUtils;

import java.lang.ref.WeakReference;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc : 单例tio全屏进度弹窗
 */
public class SingletonProgressDialog {
    private static WeakReference<TioProgressDialog> mDialogRef;

    private static TioProgressDialog getDialog() {
        return mDialogRef == null ? null : mDialogRef.get();
    }

    public static void dismiss() {
        TioProgressDialog dialog = getDialog();
        if (dialog != null) {
            dialog.dismiss();
            mDialogRef.clear();
        }
    }

    public static void show(@NonNull Context context, @Nullable String message,
                            boolean cancelable, boolean canceledOnTouchOutside,
                            @Nullable final DialogInterface.OnCancelListener onCancelListener) {

        if (!ThreadUtils.isMainThread()) {
            ThreadUtils.runOnUiThread(() -> show(context, message, cancelable, canceledOnTouchOutside, onCancelListener));
            return;
        }

        TioProgressDialog dialog = getDialog();

        if (dialog != null && dialog.getContext() != context) {
            dismiss();
            dialog = null;
        }

        if (dialog == null) {
            dialog = new TioProgressDialog(message);
            mDialogRef = new WeakReference<>(dialog);
            dialog.show(context, cancelable, canceledOnTouchOutside, onCancelListener);
        }
    }

    public static void show_unCancel(Context context, String message) {
        show(context, message, false, false, null);
    }

    public static void show_cancelable(Context context, String message) {
        show(context, message, true, false, null);
    }

    public static void show_canceledOnTouchOutside(Context context, String message) {
        show(context, message, true, true, null);
    }
}
