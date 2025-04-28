package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.account.data.AccountSP;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.CountDownTimerUtils;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.GetCodeBindReq;
import com.watayouxiang.httpclient.model.request.UserBindReq;
import com.watayouxiang.httpclient.model.request.UserBindReqOther;

public class UnBindActivityOther extends TioActivity implements View.OnClickListener {

    private WtTitleBar titleBar;
    private TextView tv_tel;
    private TioEditText et_tel;
    private TioEditText et_code;
    private TioEditText et_code_new;
    private TextView tv_checkcode;
    private TextView tv_checkcode_new;
    private TextView tv_titles;
    private TextView tv_name;
    private TextView tv_name_new;
    private TextView tv_old_account;
    private Button bt_confirm;
    private String updateType;

    public static void startActivity(Context context,String updateType){
        Intent intent = new Intent(context, UnBindActivityOther.class);
        intent.putExtra("updateType",updateType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_bind_other_activity);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        if(intent != null){
            updateType = getIntent().getStringExtra("updateType");
        }

        titleBar = findViewById(R.id.titleBar);
        et_tel = findViewById(R.id.et_tel);
        tv_titles = findViewById(R.id.tv_titles);
        tv_name = findViewById(R.id.tv_name);
        et_code = findViewById(R.id.et_code);
        tv_checkcode = findViewById(R.id.tv_checkcode);
        tv_checkcode.setOnClickListener(this);
        bt_confirm = findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this::onClick);
        tv_tel = findViewById(R.id.tv_tel);
        et_code_new = findViewById(R.id.et_code_new);
        tv_checkcode_new = findViewById(R.id.tv_checkcode_new);
        tv_checkcode_new.setOnClickListener(this);
        tv_name_new = findViewById(R.id.tv_name_new);
        tv_old_account = findViewById(R.id.tv_old_account);

        if(updateType.equals("2")){
            tv_tel.setText(TioDBPreferences.getPhone());
            tv_old_account.setText(TioDBPreferences.getPhone());
            tv_titles.setText(getString(R.string.change_phone_login));
            tv_name.setText(getString(R.string.yuan_phone));
            titleBar.setTitle(getString(R.string.change_phone));
            et_tel.setHint(getString(R.string.hint_input_phone));
            tv_name_new.setText(getString(R.string.new_phone));
        }else if(updateType.equals("3")){
            tv_tel.setText(TioDBPreferences.getEmail());
            tv_old_account.setText(TioDBPreferences.getEmail());
            tv_titles.setText(getString(R.string.change_mail_login));
            tv_name.setText(getString(R.string.yuan_mail));
            tv_name_new.setText(getString(R.string.new_mail));
            titleBar.setTitle(getString(R.string.change_mail));
            et_tel.setHint(getString(R.string.hint_input_mail));
        }
        et_code.setHint(getString(R.string.hint_input_code));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_checkcode:
                showBlockPuzzleDialog(this,tv_old_account.getText().toString(),"0");
                break;
            case R.id.tv_checkcode_new:
                if(!TextUtils.isEmpty(et_tel.getText().toString())){
                    showBlockPuzzleDialog(this,et_tel.getText().toString(),"1");
                }else {
                    TioToast.showShort(getString(R.string.hint_input_phone));
                }

                break;
            case R.id.bt_confirm:
                if(vertify()){
                    submit();
                }

                break;

        }
    }

    private boolean vertify(){
        if(TextUtils.isEmpty(et_code.getText().toString())){
            TioToast.showShort(getString(R.string.hint_input_code));
            return false;
        }
        if(TextUtils.isEmpty(et_tel.getText().toString())){
            TioToast.showShort(getString(R.string.hint_input_phone));
            return false;
        }
        if(TextUtils.isEmpty(et_code_new.getText().toString())){
            TioToast.showShort(getString(R.string.hint_input_new_code));
            return false;
        }
        return true;
    }


    private void submit(){
        UserBindReqOther userBindReq = null;
        String loginName = AccountSP.getLoginName();
        if(updateType.equals("2")){
            userBindReq = new UserBindReqOther(updateType,loginName,TioDBPreferences.getPhone(),
                    et_code.getText().toString(),et_tel.getText().toString(),et_code_new.getText().toString());
        }else if(updateType.equals("3")){
            userBindReq = new UserBindReqOther(updateType,loginName,TioDBPreferences.getEmail(),
                    et_code.getText().toString(),et_tel.getText().toString(),et_code_new.getText().toString());
        }


        TioHttpClient.post(userBindReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                if(updateType.equals("2")){
                    TioDBPreferences.savePhone(et_tel.getText().toString());
                }else if(updateType.equals("3")){
                    TioDBPreferences.saveEmail(et_tel.getText().toString());
                }
                BindActivity.startActivity(UnBindActivityOther.this,updateType);
                TioToast.showShort(getString(R.string.bind_success));
                finish();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    // 显示滑块验证弹窗
    private void showBlockPuzzleDialog(Context context, String phone,String type) {
        TioBlockPuzzleDialog blockPuzzleDialog = new TioBlockPuzzleDialog(context);
        blockPuzzleDialog.setOnResultsListener(result -> realSendSms(result, phone,type));
        blockPuzzleDialog.show();
    }

    // 发送短信验证码
    private void realSendSms(String captchaVerification, String phone,String type) {
        GetCodeBindReq getCodeReq = new GetCodeBindReq(captchaVerification,updateType,phone);
        TioHttpClient.post(getCodeReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(getString(R.string.send_success));
//                startCodeTimer(60);
                if(type.equals("0")){
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                            UnBindActivityOther.this,tv_checkcode,
                            60000, 1000);
                    mCountDownTimerUtils.start();
                }else if(type.equals("1")){
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                            UnBindActivityOther.this,tv_checkcode_new,
                            60000, 1000);
                    mCountDownTimerUtils.start();
                }

            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

}
