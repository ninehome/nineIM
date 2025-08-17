package com.tiocloud.chat.widget.dialog.tio;

import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc : 表单弹窗
 */
public abstract class BaseFormDialog extends TioDialog {

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_form_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();
        TextView tv_title = findViewById(R.id.tv_title);
        TextView et_input = findViewById(R.id.et_input);
        TextView tv_negativeBtn = findViewById(R.id.tv_negativeBtn);
        TextView tv_positiveBtn = findViewById(R.id.tv_positiveBtn);

        initTitleView(tv_title);
        initInputView(et_input);
        initNegativeBtn(tv_negativeBtn);
        initPositiveBtn(tv_positiveBtn);
    }

    protected abstract void initPositiveBtn(TextView tv_positiveBtn);

    protected abstract void initNegativeBtn(TextView tv_negativeBtn);

    protected abstract void initInputView(TextView et_input);

    protected abstract void initTitleView(TextView tv_title);

}
