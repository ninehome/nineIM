package com.watayouxiang.audiorecord.internal;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/06
 *     desc   :
 * </pre>
 */
public class DialogHelper {

    public static void showOkCancelDialog(@NonNull Context context, @Nullable CharSequence message,
                                          @Nullable final OnDialogListener actionListener) {
        showOkCancelDialog(context, null, message, null, null, false, actionListener);
    }

    public static void showOkCancelDialog(@NonNull Context context, @Nullable CharSequence title, @Nullable CharSequence message,
                                          boolean cancelable, @Nullable final OnDialogListener actionListener) {
        showOkCancelDialog(context, title, message, null, null, cancelable, actionListener);
    }

    public static void showOkCancelDialog(@NonNull Context context, @Nullable CharSequence title, @Nullable CharSequence message,
                                          @Nullable CharSequence okStr, @Nullable CharSequence cancelStr,
                                          boolean cancelable, @Nullable final OnDialogListener actionListener) {
        okStr = okStr != null ? okStr : "确定";
        cancelStr = cancelStr != null ? cancelStr : "取消";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);

        builder.setPositiveButton(okStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (actionListener != null) {
                    actionListener.doOkAction();
                }
            }
        });
        builder.setNegativeButton(cancelStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (actionListener != null) {
                    actionListener.doCancelAction();
                }
            }
        });

        builder.show();
    }

    public static class OnSimpleDialogListener implements OnDialogListener {
        @Override
        public void doCancelAction() {

        }

        @Override
        public void doOkAction() {

        }
    }

    public interface OnDialogListener {
        void doOkAction();

        void doCancelAction();
    }
}
