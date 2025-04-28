package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.account.pwd.ModifyPwdActivity;
import com.tiocloud.chat.util.ScreenUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.CurrUserTable;

public class AccountSafeActivity extends TioActivity implements View.OnClickListener{

    private RelativeLayout rl_modify_pwd;
    private RelativeLayout rl_tel;
    private RelativeLayout rl_mail;
    private TextView tv_tel;
    private TextView tv_mail;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,AccountSafeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        initView();
    }

    private void initView(){
        rl_modify_pwd = findViewById(R.id.rl_modify_pwd);
        rl_modify_pwd.setOnClickListener(this);
        rl_tel = findViewById(R.id.rl_tel);
        rl_tel.setOnClickListener(this);
        rl_mail = findViewById(R.id.rl_mail);
        rl_mail.setOnClickListener(this);
        tv_tel = findViewById(R.id.tv_tel);
        tv_mail = findViewById(R.id.tv_mail);

        initData();

    }

    private void initData(){
        String phone = TioDBPreferences.getPhone();
        String mail = TioDBPreferences.getEmail();
        if(StringUtils.isEmpty(phone)){
            tv_tel.setText(getString(R.string.unbind));
        }else {
            tv_tel.setText(phone);
        }

        if(StringUtils.isEmpty(mail)){
            tv_mail.setText(getString(R.string.unbind));
        }else {
            tv_mail.setText(mail);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_modify_pwd:
                ModifyPwdActivity.start(AccountSafeActivity.this);
                break;
            case R.id.rl_tel:
                if(StringUtils.isEmpty(TioDBPreferences.getPhone())){
                    UnBindActivity.startActivity(this,"2");
                }else {
                    UnBindActivityOther.startActivity(this,"2");
                }

                break;
            case R.id.rl_mail:
                if(StringUtils.isEmpty(TioDBPreferences.getEmail())){
                    UnBindActivity.startActivity(this,"3");
                }else {
                    UnBindActivityOther.startActivity(this,"3");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
