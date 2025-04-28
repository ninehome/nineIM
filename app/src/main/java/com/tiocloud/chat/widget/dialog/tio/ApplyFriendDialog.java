package com.tiocloud.chat.widget.dialog.tio;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class ApplyFriendDialog extends BaseFormDialog {
    private final String etText;
    private final OnBtnListener onBtnListener;

    private TextView et_input;

    public ApplyFriendDialog(String etText, OnBtnListener onBtnListener) {
        this.etText = etText;
        this.onBtnListener = onBtnListener;
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setText("申请");
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickPositive(v, et_input.getText().toString(), ApplyFriendDialog.this);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setText("取消");
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickNegative(v, ApplyFriendDialog.this);
            }
        });
    }

    @Override
    protected void initInputView(TextView et_input) {
        this.et_input = et_input;
        et_input.setHint("申请理由（30字内）");
        et_input.setText(etText);
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setText("添加好友");
    }

    public interface OnBtnListener {
        void onClickPositive(View view, String submitTxt, ApplyFriendDialog dialog);

        void onClickNegative(View view, ApplyFriendDialog dialog);
    }
}
