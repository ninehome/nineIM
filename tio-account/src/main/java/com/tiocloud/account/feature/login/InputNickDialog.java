package com.tiocloud.account.feature.login;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.account.R;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class InputNickDialog extends BaseFormDialog {
    private final OnBtnListener onBtnListener;
    private TextView et_input;

    public InputNickDialog(OnBtnListener onBtnListener) {
        this.onBtnListener = onBtnListener;
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickPositive(v, et_input.getText().toString(), InputNickDialog.this);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickNegative(v, InputNickDialog.this);
            }
        });
    }

    @Override
    protected void initInputView(TextView et_input) {
        this.et_input = et_input;
        et_input.setHint(getContext().getString(R.string.set_nickname));
        et_input.setMinLines(1);
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setText(getContext().getString(R.string.nickname));
    }

    public abstract static class OnBtnListener {
        public abstract void onClickPositive(View view, String submitTxt, InputNickDialog dialog);

        public void onClickNegative(View view, InputNickDialog dialog) {
            dialog.dismiss();
        }
    }
}
