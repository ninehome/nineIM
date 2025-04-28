package com.tiocloud.chat.widget.dialog.tio;

import android.widget.CheckBox;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/17
 *     desc   :
 * </pre>
 */
public abstract class BaseOperCheckDialog extends TioDialog {
    @Override
    protected int getDialogContentId() {
        return R.layout.tio_oper_check_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_negativeBtn = findViewById(R.id.tv_negativeBtn);
        TextView tv_positiveBtn = findViewById(R.id.tv_positiveBtn);
        CheckBox checkBox = findViewById(R.id.checkBox);

        initTitleView(tv_title);
        initNegativeBtn(tv_negativeBtn);
        initPositiveBtn(tv_positiveBtn);
        initCheckBox(checkBox);
    }

    protected abstract void initTitleView(TextView tv_title);

    protected abstract void initCheckBox(CheckBox checkBox);

    protected abstract void initPositiveBtn(TextView tv_positiveBtn);

    protected abstract void initNegativeBtn(TextView tv_negativeBtn);
}

