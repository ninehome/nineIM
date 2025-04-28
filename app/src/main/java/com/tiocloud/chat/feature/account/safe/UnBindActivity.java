package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.account.data.AccountSP;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.ClipboardUtils;
import com.tiocloud.chat.util.CountDownTimerUtils;
import com.tiocloud.verification.widget.TioBlockPuzzleDialog;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.tool.WtTimer;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.GetCodeBindReq;
import com.watayouxiang.httpclient.model.request.GetCodeReq;
import com.watayouxiang.httpclient.model.request.UserBindReq;
import com.watayouxiang.imclient.utils.DeviceUtils;

import java.util.Locale;

public class UnBindActivity extends TioActivity implements View.OnClickListener {

    private WtTitleBar titleBar;
    private TextView tv_tel;
    private TioEditText et_tel;
    private TioEditText et_code;
    private TextView tv_checkcode;
    private TextView tv_titles;
    private TextView tv_name;
    private Button bt_confirm;
    private String updateType;

    public static void startActivity(Context context,String updateType){
        Intent intent = new Intent(context, UnBindActivity.class);
        intent.putExtra("updateType",updateType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_bind_activity);
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

        if(updateType.equals("2")){
            tv_tel.setText(TioDBPreferences.getPhone());
            tv_titles.setText(getString(R.string.bind_phone_next_login));
            tv_name.setText(getString(R.string.phone));
            titleBar.setTitle(getString(R.string.bind_phone));
            et_tel.setHint(getString(R.string.hint_input_phone));
        }else if(updateType.equals("3")){
            tv_tel.setText(TioDBPreferences.getEmail());
            tv_titles.setText(getString(R.string.bind_mail_next_login));
            tv_name.setText(getString(R.string.mail));
            titleBar.setTitle(getString(R.string.bind_mail));
            et_tel.setHint(getString(R.string.hint_input_mail));
        }
        et_code.setHint(getString(R.string.hint_input_code));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_checkcode:
                if(!TextUtils.isEmpty(et_tel.getText().toString())){
                    showBlockPuzzleDialog(this,et_tel.getText().toString());
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
        if(updateType.equals("2")){
            if(TextUtils.isEmpty(et_tel.getText().toString())){
                TioToast.showShort(getString(R.string.hint_input_phone));
                return false;
            }
        }else if(updateType.equals("3")){
            if(TextUtils.isEmpty(et_tel.getText().toString())){
                TioToast.showShort(getString(R.string.hint_input_mail));
                return false;
            }
        }

        return true;
    }


    private void submit(){
//        String loginName = AccountSP.getLoginName();
        UserBindReq userBindReq = new UserBindReq(updateType,String.valueOf(TioDBPreferences.getCurrUid()),
                et_tel.getText().toString(),et_code.getText().toString());
        TioHttpClient.post(userBindReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                if(updateType.equals("2")){
                    TioDBPreferences.savePhone(et_tel.getText().toString());
                }else if(updateType.equals("3")){
                    TioDBPreferences.saveEmail(et_tel.getText().toString());
                }
                BindActivity.startActivity(UnBindActivity.this,updateType);
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
    private void showBlockPuzzleDialog(Context context, String phone) {
        TioBlockPuzzleDialog blockPuzzleDialog = new TioBlockPuzzleDialog(context);
        blockPuzzleDialog.setOnResultsListener(result -> realSendSms(result, phone));
        blockPuzzleDialog.show();
    }

    // 发送短信验证码
    private void realSendSms(String captchaVerification, String phone) {
        GetCodeBindReq getCodeReq = new GetCodeBindReq(captchaVerification,updateType,phone);
        TioHttpClient.post(getCodeReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(getString(R.string.send_success));
//                startCodeTimer(60);
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                        UnBindActivity.this,tv_checkcode,
                        60000, 1000);
                mCountDownTimerUtils.start();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

}
