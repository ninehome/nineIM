package com.tiocloud.chat.feature.account.pwd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.CountDownTimerUtils;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.tool.WtTimer;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.CurrUserTable;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.GetCodeReq;
import com.watayouxiang.httpclient.model.request.RetrievePwdReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.UpdatePwdNewReq;

import java.util.Locale;

public class OtherModifyPwdActivity extends TioActivity implements View.OnClickListener {

    private WtTitleBar titleBar;
    private TextView tv_checkcode;
    private TextView tv_tel;
    private TextView tv_type_name;
    private TioEditText et_code;
    private TioEditText et_newPwd;
    private TioEditText et_newPwdConfirm;
    private Button bt_confirm;
    private String updateType;

    private WtTimer wtTimer;


    public static void start(Context context,String updateType) {
        Intent starter = new Intent(context, OtherModifyPwdActivity.class);
        starter.putExtra("updateType",updateType);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_other_modify_pwd_activity);
        initView();
    }

    private void initView(){

        Intent intent = getIntent();
        if(intent != null){
            updateType = getIntent().getStringExtra("updateType");
        }

        titleBar = findViewById(R.id.titleBar);
        tv_type_name = findViewById(R.id.tv_type_name);

        tv_checkcode = findViewById(R.id.tv_checkcode);
        tv_checkcode.setOnClickListener(this);
        tv_tel = findViewById(R.id.tv_tel);
        et_code = findViewById(R.id.et_code);
        et_newPwd = findViewById(R.id.et_newPwd);
        et_newPwdConfirm = findViewById(R.id.et_newPwdConfirm);
        bt_confirm = findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);

        String phone = TioDBPreferences.getPhone();
        String mail = TioDBPreferences.getEmail();
        if(updateType.equals("2")){
            titleBar.setTitle(getString(R.string.phone_find_pwd));
            tv_type_name.setText(getString(R.string.phone));
            if(!StringUtils.isEmpty(phone)){
                tv_tel.setText(TioDBPreferences.getPhone());
            }

        }else if(updateType.equals("3")){
            titleBar.setTitle(getString(R.string.mail_find_pwd));
            tv_type_name.setText(getString(R.string.mail));
            if(!StringUtils.isEmpty(mail)){
                tv_tel.setText(TioDBPreferences.getEmail());
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_checkcode:
//                reqSendSms(OtherModifyPwdActivity.this,tv_tel.getText().toString());
                showBlockPuzzleDialog(OtherModifyPwdActivity.this,tv_tel.getText().toString());
                break;
            case R.id.bt_confirm:
                if(vertify()){
                    updatePwd();
                }

                break;
        }
    }

    public void reqSendSms(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            TioToast.showShort(getString(R.string.phnoe_not_empty));
            return;
        }
        smsBeforeCheck(phone, context);
    }

    // 验证手机号是否可用
    private void smsBeforeCheck(String phone, Context context) {
        SmsBeforeCheckReq smsBeforeCheckReq = new SmsBeforeCheckReq("6",phone);
        TioHttpClient.post(smsBeforeCheckReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                showBlockPuzzleDialog(context, phone);
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }

    // 显示滑块验证弹窗
    private void showBlockPuzzleDialog(Context context, String phone) {
        TioBlockPuzzleDialog blockPuzzleDialog = new TioBlockPuzzleDialog(context);
        blockPuzzleDialog.setOnResultsListener(result -> realSendSms(result, phone));
        blockPuzzleDialog.show();
    }

    // 发送短信验证码
    private void realSendSms(String captchaVerification, String phone) {
        GetCodeReq getCodeReq = new GetCodeReq(captchaVerification,updateType,phone);
        TioHttpClient.post(getCodeReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(getString(R.string.send_success));
//                startCodeTimer(60);
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                        OtherModifyPwdActivity.this,tv_checkcode,
                        60000, 1000);
                mCountDownTimerUtils.start();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private boolean vertify(){
        if(TextUtils.isEmpty(et_code.getText().toString())){
            TioToast.showShort(getString(R.string.hint_input_code));
            return false;
        }
        if(TextUtils.isEmpty(et_newPwd.getText().toString())){
            TioToast.showShort(getString(R.string.hint_input_pwd));
            return false;
        }
        if(TextUtils.isEmpty(et_newPwdConfirm.getText().toString())){
            TioToast.showShort(getString(R.string.hint_reinput_pwd));
            return false;
        }
        return true;
    }

    private void updatePwd(){
        UpdatePwdNewReq updatePwdNewReq = new UpdatePwdNewReq(
                updateType,tv_tel.getText().toString(),et_code.getText().toString(),
                et_newPwd.getText().toString(),et_newPwdConfirm.getText().toString());
        TioHttpClient.post(updatePwdNewReq, new TioCallback<Void>() {

            @Override
            public void onTioSuccess(Void aVoid) {
                TioToast.showShort(getString(R.string.reset_pwd_success));
                ActivityUtils.finishAllActivities();
                LoginActivity.start(OtherModifyPwdActivity.this);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

}
