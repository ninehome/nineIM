package com.tiocloud.chat.widget.dialog.tio;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.listener.SimpleOnCheckedChangeListener;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/17
 *     desc   :
 * </pre>
 */
public class DeleteSessionDialog extends BaseOperCheckDialog {
    private final OnClickListener onClickListener;
    private boolean isCheck = false;

    public DeleteSessionDialog(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void initTitleView(TextView tv_title) {
        tv_title.setText(getContext().getString(R.string.sure_delete_session));
    }

    @Override
    protected void initCheckBox(CheckBox checkBox) {
        checkBox.setText(getContext().getString(R.string.now_delete_chat_history));
        checkBox.setClickable(true);
        checkBox.setChecked(isCheck);
        checkBox.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                DeleteSessionDialog.this.isCheck = isChecked;
            }
        });
    }

    @Override
    protected void initPositiveBtn(TextView tv_positiveBtn) {
        tv_positiveBtn.setText(getContext().getString(R.string.sure));
        tv_positiveBtn.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClickPositiveBtn(v, DeleteSessionDialog.this, isCheck);
            }
        });
    }

    @Override
    protected void initNegativeBtn(TextView tv_negativeBtn) {
        tv_negativeBtn.setText(getContext().getString(R.string.cancel));
        tv_negativeBtn.setOnClickListener(v -> dismiss());
    }

    public interface OnClickListener {
        void onClickPositiveBtn(View view, DeleteSessionDialog dialog, boolean isCheck);
    }
}
