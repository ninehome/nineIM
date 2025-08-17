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
public class FriendOperDialog extends BaseDialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    private TextView tv_black;
    private View v_line01;
    private TextView tv_delete;
    private View v_line02;
    private View tv_cancel;

    public FriendOperDialog(final Context context, boolean isBlack) {
        super(context);
        setAnimation(R.style.tio_bottom_dialog_anim);
        setFullScreenWidth();
        setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnCancelListener(this);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_friend_oper, null));
        initViews(isBlack);
    }

    private void initViews(boolean isBlack) {
        tv_black = findViewById(R.id.tv_black);
        v_line01 = findViewById(R.id.v_line01);
        tv_black.setText(isBlack?getContext().getString(R.string.rv_black):getContext().getString(R.string.add_black));
        tv_delete = findViewById(R.id.tv_delete);
        v_line02 = findViewById(R.id.v_line02);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_black.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initUI(boolean isMgr, boolean showExit) {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

//    public void show(boolean isMgr, boolean showExit) {
//        initUI(isMgr, showExit);
//        super.show();
//    }

//    @Deprecated
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

    protected void onClick(FriendOperDialog groupOperDialog, View v) {

    }
}
