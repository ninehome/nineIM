package com.tiocloud.chat.widget.dialog.base;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.tiocloud.chat.R;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 群聊信息页 - 操作弹窗
 */
public class GroupOperDialog extends BaseDialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    private View tv_exitGroup;
    private View v_line01;
    private View tv_dissolveGroup;
    private View v_line02;
    private View tv_transferGroup;
    private View tv_cancel;

    public GroupOperDialog(final Context context) {
        super(context);
        setAnimation(R.style.tio_bottom_dialog_anim);
        setFullScreenWidth();
        setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setOnCancelListener(this);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_group_oper, null));
        initViews();
    }

    private void initViews() {
        tv_exitGroup = findViewById(R.id.tv_exitGroup);
        v_line01 = findViewById(R.id.v_line01);
        tv_dissolveGroup = findViewById(R.id.tv_dissolveGroup);
        v_line02 = findViewById(R.id.v_line02);
        tv_transferGroup = findViewById(R.id.tv_transferGroup);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_exitGroup.setOnClickListener(this);
        tv_dissolveGroup.setOnClickListener(this);
        tv_transferGroup.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initUI(boolean isMgr, boolean showExit) {
        v_line01.setVisibility(isMgr ? View.VISIBLE : View.GONE);
        tv_dissolveGroup.setVisibility(isMgr ? View.VISIBLE : View.GONE);

        v_line02.setVisibility(isMgr ? View.VISIBLE : View.GONE);
        tv_transferGroup.setVisibility(isMgr ? View.VISIBLE : View.GONE);

//        tv_exitGroup.setVisibility(showExit?View.VISIBLE:View.GONE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    public void show(boolean isMgr, boolean showExit) {
        initUI(isMgr, showExit);
        super.show();
    }

    @Deprecated
    @Override
    public void show() {
        // super.show();
    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            cancel();
        }
        onClick(this, v);
    }

    protected void onClick(GroupOperDialog groupOperDialog, View v) {

    }
}
