package com.tiocloud.chat.widget.dialog.tio;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.dialog.oper.TioOperDialog;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class DeleteFriendDialog extends TioOperDialog {
    private final OnBtnListener onBtnListener;

    public DeleteFriendDialog(OnBtnListener btnListener) {
        this.onBtnListener = btnListener;
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setText(getContext().getString(R.string.delete));
        tv_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickPositive(v, DeleteFriendDialog.this);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setText(getContext().getString(R.string.cancel));
        tv_negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnListener.onClickNegative(v, DeleteFriendDialog.this);
            }
        });
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setText(getContext().getString(R.string.querenshanchuliaotian));
    }

    public interface OnBtnListener {
        void onClickPositive(View view, DeleteFriendDialog dialog);

        void onClickNegative(View view, DeleteFriendDialog dialog);
    }
}
