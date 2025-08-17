package com.tiocloud.chat.widget.dialog.base;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 群聊信息页 - 操作弹窗
 */
public class ShowSaveDialog extends BaseDialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    private TextView tv_save;
    private View tv_cancel;

    public ShowSaveDialog(final Context context) {
        super(context);
        setAnimation(R.style.tio_bottom_dialog_anim);
        setFullScreenWidth();
        setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnCancelListener(this);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_save, null));
        initViews();
    }

    private void initViews() {
        tv_save = findViewById(R.id.tv_save);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_save.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void show() {
         super.show();
    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            cancel();
        }
        onClick(this, v);
    }

    protected void onClick(ShowSaveDialog groupOperDialog, View v) {

    }
}
