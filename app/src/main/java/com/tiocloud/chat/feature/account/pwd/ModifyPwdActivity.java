package com.tiocloud.chat.feature.account.pwd;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.account.mvp.logout.LogoutContract;
import com.tiocloud.account.mvp.logout.LogoutPresenter;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

/**
 * author : TaoWang
 * date : 2020/3/13
 * desc :
 */
public class ModifyPwdActivity extends TioActivity implements LogoutContract.View {

    private TioEditText et_oldPwd;
    private TioEditText et_newPwd;
    private TioEditText et_newPwdConfirm;
    private View bt_save;
    private TextView tv_other_type;
    private TextView tv_uid;

    private final ModifyPwdModel modifyPwdModel = new ModifyPwdModel();
    private final LogoutPresenter logoutPresenter = new LogoutPresenter(this);
    private WtTitleBar titleBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModifyPwdActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xFFF8F8F8));
        setContentView(R.layout.tio_modify_pwd_activity);
        findViews(getWindow().getDecorView());
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modifyPwdModel.detachModel();
        logoutPresenter.detachView();
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void initViews() {
        titleBar.setTitle(getString(R.string.modify_pwd));
        bt_save.setOnClickListener(view -> {

            hintKeyBoard();
            String oldPwd = et_oldPwd.getSubmitText();
            String newPwd = et_newPwd.getSubmitText();
            String newPwdConfirm = et_newPwdConfirm.getSubmitText();

            if (newPwd == null) {
                TioToast.showShort(getString(R.string.pwd_not_empty));
                return;
            }
            if (!newPwd.equals(newPwdConfirm)) {
                TioToast.showShort(getString(R.string.double_input_pwd_unsame));
                return;
            }

            updatePwd(oldPwd, newPwd);
        });
        tv_other_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBotomDialog();
            }
        });
        String uid = String.valueOf(TioDBPreferences.getCurrUid());
        tv_uid.setText(uid);
    }

    private void updatePwd(String oldPwd, String newPwd) {
        modifyPwdModel.updatePwd(oldPwd, newPwd, new BaseModel.DataProxy<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 密码修改成功
                TioToast.showShort(getString(R.string.pwd_reset_success_relogin));
                // ModifyPwdActivity.this.finish();
                logoutPresenter.logout(getActivity());
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(ModifyPwdActivity.this, msg);
            }
        });
    }

    private void findViews(View decorView) {
        titleBar = decorView.findViewById(R.id.titleBar);
        et_oldPwd = decorView.findViewById(R.id.et_oldPwd);
        et_newPwd = decorView.findViewById(R.id.et_newPwd);
        et_newPwdConfirm = decorView.findViewById(R.id.et_newPwdConfirm);
        bt_save = decorView.findViewById(R.id.bt_save);
        tv_uid = decorView.findViewById(R.id.tv_uid);
        tv_other_type = decorView.findViewById(R.id.tv_other_type);
        tv_other_type.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_other_type.getPaint().setAntiAlias(true);//抗锯齿
    }

    public void showBotomDialog(){
        Dialog bottomDialog = new Dialog(ModifyPwdActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ModifyPwdActivity.this).inflate(
                R.layout.tio_pwd_type_dialog, null);
        TextView tv_tel = contentView.findViewById(R.id.tv_tel);
        tv_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                if (StringUtils.isEmpty(TioDBPreferences.getPhone())){
                    ToastUtils.showShort(getString(R.string.unbind_phone_choose_other));
                    return;
                }
                OtherModifyPwdActivity.start(ModifyPwdActivity.this,"2");
            }
        });
        TextView tv_mail = contentView.findViewById(R.id.tv_mail);
        tv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                if (StringUtils.isEmpty(TioDBPreferences.getPhone())){
                    ToastUtils.showShort(getString(R.string.unbind_mail_choose_other));
                    return;
                }
                OtherModifyPwdActivity.start(ModifyPwdActivity.this,"3");
            }
        });
        TextView tv_cancel = contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(ModifyPwdActivity.this);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialogAnimation);
        bottomDialog.getWindow().setDimAmount(0);
        bottomDialog.show();
    }
}
