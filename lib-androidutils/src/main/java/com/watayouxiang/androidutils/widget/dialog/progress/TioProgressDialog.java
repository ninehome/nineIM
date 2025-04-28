package com.watayouxiang.androidutils.widget.dialog.progress;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;

/**
 * author : TaoWang
 * date : 2020-01-06
 * desc : tio全屏进度弹窗
 */
public class TioProgressDialog extends TioDialog {

    private String message;

    public TioProgressDialog(String message) {
        this.message = message;
    }

    public TioProgressDialog() {
    }

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_progress_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();

        TextView tvMessage = findViewById(R.id.progress_text);

        if (!TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        } else {
            tvMessage.setVisibility(View.GONE);
        }
    }
}