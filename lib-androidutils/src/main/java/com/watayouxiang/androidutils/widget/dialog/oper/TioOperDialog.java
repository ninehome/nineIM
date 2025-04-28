package com.watayouxiang.androidutils.widget.dialog.oper;

import android.widget.TextView;

import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc : 操作弹窗
 */
public abstract class TioOperDialog extends TioDialog {

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_oper_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_negativeBtn = findViewById(R.id.tv_negativeBtn);
        TextView tv_positiveBtn = findViewById(R.id.tv_positiveBtn);

        initTitleView(tv_title);
        initNegativeBtn(tv_negativeBtn);
        initPositiveBtn(tv_positiveBtn);
    }

    protected abstract void initPositiveBtn(TextView tv_positiveBtn);

    protected abstract void initNegativeBtn(TextView tv_negativeBtn);

    protected abstract void initTitleView(TextView tv_title);
}
