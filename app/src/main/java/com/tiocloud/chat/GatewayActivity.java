package com.tiocloud.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.mvp.launcher.LauncherPresenter;
import com.tiocloud.chat.widget.dialog.tio.ProtectGuideDialog;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.GatewayReq;
import com.watayouxiang.httpclient.model.response.GatewayResp;
import com.watayouxiang.httpclient.prefernces.HttpPreferences;

// 修改类名
public class GatewayActivity extends AppCompatActivity {

    private EditText etEnterpriseId;
    private Button btnLogin;
    private TextView tvLink1, tvLink2, tvLink3;
    private static final String PREF_NAME = "privacy_prefs";
    private static final String KEY_PRIVACY_ACCEPTED = "privacy_accepted";

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway1);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isPrivacyAccepted = prefs.getBoolean(KEY_PRIVACY_ACCEPTED, false);

        if (!isPrivacyAccepted) {
//              String url = "https://yszc.wangliantong.com";
//              Intent intent = new Intent(GatewayActivity.this, TioBrowserActivity.class);
//              intent.putExtra(TioBrowserActivity.EXTRA_URL, url);
//              startActivityForResult(intent, 1);

              init();
        }



        etEnterpriseId = findViewById(R.id.et_enterprise_id);





        CheckBox cbAgreement = findViewById(R.id.cb_agreement);
         findViewById(R.id.user_protocol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TioBrowserActivity.start(GatewayActivity.this, "https://xxsj.wangliantong.com");
            }
        });
        findViewById(R.id.user_private).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TioBrowserActivity.start(GatewayActivity.this, "https://yszc.wangliantong.com");
            }
        });
        btnLogin = findViewById(R.id.btn_login);
         tvLink1 = findViewById(R.id.tv_link1);
        tvLink2 = findViewById(R.id.tv_link2);
        tvLink3 = findViewById(R.id.tv_link3);
        checkGateway();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterpriseId = etEnterpriseId.getText().toString().trim();
                if (!cbAgreement.isChecked()) {
                    Toast.makeText(GatewayActivity.this, "请勾选阅读并同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(enterpriseId)) {
                    Toast.makeText(GatewayActivity.this, "请输入企业 ID", Toast.LENGTH_SHORT).show();
                } else {


                    new GatewayReq(enterpriseId).get(new TioCallbackImpl<GatewayResp>(){
                        @Override
                        public void onTioSuccess(GatewayResp data) {
                            HttpPreferences.saveGatewayId(enterpriseId);
                            if(data.expire == 1){
                                HttpPreferences.saveBaseUrl(data.host);
                                HttpPreferences.saveResUrl(data.res);

                                // 登录成功，跳转到主程序界面
                                Intent intent = new Intent(GatewayActivity.this, com.tiocloud.chat.feature.splash.SplashActivity.class);
                                intent.putExtra("enterpriseId", enterpriseId);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(GatewayActivity.this,"企业已过期",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onTioError(String msg) {
                            System.out.println("fighitng__________"+msg);
                        }

                    });


                }
            }
        });


        // 设置超链接点击事件
        tvLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mzxy.wangliantong.com";
                TioBrowserActivity.start(GatewayActivity.this, url);
            }
        });

        tvLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://yszc.wangliantong.com";
                TioBrowserActivity.start(GatewayActivity.this, url);
            }
        });

        tvLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://xxsj.wangliantong.com";
                TioBrowserActivity.start(GatewayActivity.this, url);
            }
        });
    }


    public void init() {
        startTime = System.currentTimeMillis();
        // 显示隐私政策确认弹窗
        new ProtectGuideDialog(GatewayActivity.this, this::reqPermission).checkConfirm();
    }

    private void reqPermission() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_PRIVACY_ACCEPTED, true);
        editor.apply();
//        PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.LOCATION)
//                .rationale((activity, shouldRequest) -> shouldRequest.again(true))
//                .callback((isAllGranted, granted, deniedForever, denied) -> {
//
//                })
//                .request();
    }




    private void checkGateway() {
        String enterpriseId = HttpPreferences.getGatewayId();
        if (!enterpriseId.isEmpty()){
            new GatewayReq(enterpriseId).get(new TioCallbackImpl<GatewayResp>(){
                @Override
                public void onTioSuccess(GatewayResp data) {
                    HttpPreferences.saveGatewayId(enterpriseId);
                    System.out.println("fighting_________________"+data.host);
                    if(data.expire == 1){
                        HttpPreferences.saveBaseUrl(data.host);
                        HttpPreferences.saveResUrl(data.res);

                        // 登录成功，跳转到主程序界面
                        Intent intent = new Intent(GatewayActivity.this, com.tiocloud.chat.feature.splash.SplashActivity.class);
                        intent.putExtra("enterpriseId", enterpriseId);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TioApplication.getInstance(),"企业已过期",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onTioError(String msg) {
                    System.out.println("fighitng__________"+msg);
                }

            });
        }


    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "没有可用的浏览器", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_PRIVACY_ACCEPTED, true);
            editor.apply();
        }
    }

    private void exitApp(String reason) {
        TioToast.showShort(reason);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.exitApp();
            }
        }, 2000);
    }
}
