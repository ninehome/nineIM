package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.ClipboardUtils;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.imclient.utils.DeviceUtils;

import java.util.Locale;

public class BindActivity extends TioActivity implements View.OnClickListener {

    private WtTitleBar titleBar;
    private ImageView iv_bind;
    private TextView tv_account;
    private Button bt_confirm;
    String updateType;

    public static void startActivity(Context context,String updateType){
        Intent intent = new Intent(context, BindActivity.class);
        intent.putExtra("updateType",updateType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_activity);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        if(intent != null){
            updateType = getIntent().getStringExtra("updateType");
        }

        titleBar = findViewById(R.id.titleBar);
        iv_bind = findViewById(R.id.iv_bind);
        tv_account = findViewById(R.id.tv_account);
        bt_confirm = findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this::onClick);

        String phone = TioDBPreferences.getPhone();
        String mail = TioDBPreferences.getEmail();
        if(updateType.equals("2")){
            iv_bind.setImageResource(R.mipmap.icon_phone_bind);
            tv_account.setText(getString(R.string.your_phone)+phone);
            titleBar.setTitle(getString(R.string.bind_phone));

        }else if(updateType.equals("3")){
            iv_bind.setImageResource(R.mipmap.icon_email_bind);
            tv_account.setText(getString(R.string.your_mail)+mail);
            titleBar.setTitle(getString(R.string.bind_mail));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_confirm:
                finish();
                break;
        }
    }

}
