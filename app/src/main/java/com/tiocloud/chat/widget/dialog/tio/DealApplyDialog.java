package com.tiocloud.chat.widget.dialog.tio;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.util.StringUtil;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc : 处理好友申请
 */
public class DealApplyDialog extends BaseFormDialog {
    private final @Nullable
    String etText;
    private final OnBtnListener onBtnListener;

    private TextView et_input;

    public DealApplyDialog(@Nullable String etText, OnBtnListener onBtnListener) {
        this.etText = etText;
        this.onBtnListener = onBtnListener;
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setText(getContext().getString(R.string.agree));
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickPositive(v, et_input.getText().toString(), DealApplyDialog.this);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setText(getContext().getString(R.string.cancel));
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickNegative(v, DealApplyDialog.this);
            }
        });
    }

    @Override
    protected void initInputView(TextView et_input) {
        this.et_input = et_input;
        et_input.setMinLines(1);
        et_input.setHint("");
        et_input.setText(StringUtil.nonNull(etText));
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setText(getContext().getString(R.string.haoyoubeizhu));
    }

    public interface OnBtnListener {
        void onClickPositive(View view, String submitTxt, DealApplyDialog dialog);

        void onClickNegative(View view, DealApplyDialog dialog);
    }

}
