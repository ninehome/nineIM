package com.tiocloud.chat.feature.account.safe;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.util.AppUpdateTool;
import com.tiocloud.chat.util.ClipboardUtils;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.SysVersionResp;
import com.watayouxiang.imclient.utils.DeviceUtils;

import java.util.Locale;

public class AboutActivity extends TioActivity implements View.OnClickListener {

    private ImageView iv_logo;
    private TextView tv_app_name;
    private TextView tv_version;
    private TextView tv_update;
    private TextView tv_contact;
    private TextView tv_tel;
    private TextView tv_dial;
    private TextView tv_qq;
    private TextView tv_copy;
    private TextView tv_location;
    private RelativeLayout rl_location;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView(){
        iv_logo = findViewById(R.id.iv_logo);
        tv_app_name = findViewById(R.id.tv_app_name);
        tv_version = findViewById(R.id.tv_version);
        tv_update = findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        tv_contact = findViewById(R.id.tv_contact);
        tv_tel = findViewById(R.id.tv_tel);
        tv_dial = findViewById(R.id.tv_dial);
        tv_dial.setOnClickListener(this);
        tv_qq = findViewById(R.id.tv_qq);
        tv_copy = findViewById(R.id.tv_copy);
        tv_location = findViewById(R.id.tv_location);
        rl_location = findViewById(R.id.rl_location);
        tv_copy.setOnClickListener(this);
        rl_location.setOnClickListener(this);

        TextView tvIntro = findViewById(R.id.tvIntro);
        tvIntro.setText(TioConfig.OpenCloseConfig.getCompanyIntro());

        tv_tel.setText(StringUtil.nonNull(TioConfig.OpenCloseConfig.getCompanyTel()));
        tv_qq.setText(StringUtil.nonNull(TioConfig.OpenCloseConfig.getCompanyQQ()));
        tv_location.setText(StringUtil.nonNull(TioConfig.OpenCloseConfig.getCompanyAddr()));

        // 版本号
        tv_version.setText("version"+String.format(Locale.getDefault(), "%s",
                DeviceUtils.getAppVersion(this)));

        TextView tvcompanyName = findViewById(R.id.companyName);
        tvcompanyName.setText(TioConfig.OpenCloseConfig.getCompanyName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update:
                checkAppUpdate();
                break;
            case R.id.tv_dial:
                callPhone(tv_tel.getText().toString());
                break;
            case R.id.tv_copy:
                ClipboardUtils.copy(this,"qq",tv_qq.getText().toString());
                break;
            case R.id.rl_location:
                break;
        }
    }

    private AppUpdateTool appUpdateTool;
    private void checkAppUpdate() {
        if (appUpdateTool == null) {
            appUpdateTool = new AppUpdateTool(this) {
                @Override
                public void onCheckUpdateSuccess(SysVersionResp sysVersionResp) {
                    super.onCheckUpdateSuccess(sysVersionResp);
                    if (sysVersionResp.getUpdateflag() == 2) {
                        TioToast.showShort(getString(R.string.current_new_version));
                    }
                }
            };
        }
        appUpdateTool.checkUpdate();
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

}
