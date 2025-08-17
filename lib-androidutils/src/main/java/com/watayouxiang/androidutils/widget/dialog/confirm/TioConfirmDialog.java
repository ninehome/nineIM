package com.watayouxiang.androidutils.widget.dialog.confirm;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc : 确认弹窗
 */
public class TioConfirmDialog extends TioDialog {
    private final CharSequence message;
    private final OnConfirmListener onConfirmListener;
    private final int messageGravity;

    public TioConfirmDialog(CharSequence message, int messageGravity, OnConfirmListener onConfirmListener) {
        this.message = message;
        this.messageGravity = messageGravity;
        this.onConfirmListener = onConfirmListener;
    }

    public TioConfirmDialog(CharSequence message, OnConfirmListener onConfirmListener) {
        this(message, Gravity.START, onConfirmListener);
    }

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_confirm_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();

        TextView tvMessage = findViewById(R.id.confirm_message);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
            tvMessage.setText(message);
            tvMessage.setGravity(messageGravity);
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }

        findViewById(R.id.confirm_button).setOnClickListener(v -> {
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm(v, TioConfirmDialog.this);
            }
        });
    }

    public interface OnConfirmListener {
        void onConfirm(View view, TioConfirmDialog dialog);
    }
}
