package com.tiocloud.account.widget;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.account.R;
import com.watayouxiang.androidutils.widget.dialog.oper.TioOperDialog;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class LogoutDialog extends TioOperDialog {
    private final OnBtnListener onBtnListener;

    public LogoutDialog(OnBtnListener btnListener) {
        this.onBtnListener = btnListener;
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setText(getContext().getString(R.string.tuichu));
        tv_positiveBtn.setTextColor(Color.parseColor("#FFFF4D4D"));
        tv_positiveBtn.setBackgroundResource(com.watayouxiang.androidutils.R.drawable.tio_dialog_positive_btn_bg_red);
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickPositive(v, LogoutDialog.this);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setText(getContext().getString(R.string.exit));
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickNegative(v, LogoutDialog.this);
            }
        });
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setText(getContext().getString(R.string.exit_now_account));
    }

    public interface OnBtnListener {
        void onClickPositive(View view, LogoutDialog dialog);

        void onClickNegative(View view, LogoutDialog dialog);
    }
}
