package com.watayouxiang.androidutils.widget.dialog.confirm;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ThreadUtils;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/07
 *     desc   :
 * </pre>
 */
public class SingletonConfirmDialog {

    private static WeakReference<TioConfirmDialog> mDialogRef = null;

    private static TioConfirmDialog getDialog() {
        return mDialogRef == null ? null : mDialogRef.get();
    }

    public static void dismiss() {
        TioConfirmDialog dialog = getDialog();
        if (dialog != null) {
            dialog.dismiss();
            mDialogRef.clear();
        }
    }

    public static void show(@NonNull Context context, @Nullable CharSequence message, int messageGravity,
                            boolean cancelable, boolean canceledOnTouchOutside,
                            @Nullable TioConfirmDialog.OnConfirmListener onConfirmListener,
                            @Nullable DialogInterface.OnCancelListener onCancelListener) {

        if (!ThreadUtils.isMainThread()) {
            ThreadUtils.runOnUiThread(() -> show(context, message, messageGravity, cancelable, canceledOnTouchOutside, onConfirmListener, onCancelListener));
            return;
        }

        TioConfirmDialog dialog = getDialog();

        if (dialog != null && dialog.getContext() != context) {
            dismiss();
            dialog = null;
        }

        if (dialog == null) {
            dialog = new TioConfirmDialog(message, messageGravity, onConfirmListener);
            mDialogRef = new WeakReference<>(dialog);
            dialog.show(context, cancelable, canceledOnTouchOutside, onCancelListener);
        }
    }

    // ====================================================================================
    // Builder
    // ====================================================================================

    public static class ShowHelper {
        private final Context context;
        private CharSequence message;
        private int messageGravity = Gravity.START;
        private boolean cancelable = true;
        private boolean canceledOnTouchOutside = false;
        private TioConfirmDialog.OnConfirmListener onConfirmListener = null;
        private DialogInterface.OnCancelListener onCancelListener = null;

        public ShowHelper(Context context) {
            this.context = context;
        }

        public ShowHelper setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public ShowHelper setMessageGravity(int messageGravity) {
            this.messageGravity = messageGravity;
            return this;
        }

        public ShowHelper setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public ShowHelper setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public ShowHelper setOnConfirmListener(TioConfirmDialog.OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public ShowHelper setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public void show() {
            SingletonConfirmDialog.show(
                    context,
                    message,
                    messageGravity,
                    cancelable,
                    canceledOnTouchOutside,
                    onConfirmListener,
                    onCancelListener
            );
        }
    }
}
