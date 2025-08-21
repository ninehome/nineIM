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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.feature.login.LoginActivity;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.mvp.launcher.LauncherPresenter;
import com.tiocloud.chat.widget.dialog.tio.ProtectGuideDialog;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway1);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isPrivacyAccepted = prefs.getBoolean(KEY_PRIVACY_ACCEPTED, false);

        if (isPrivacyAccepted) { //同意了弹窗
            String localEnterpriseId = HttpPreferences.getGatewayId();
            if (!StringUtils.isEmpty(localEnterpriseId)) {
                Intent intent = new Intent(GatewayActivity.this, com.tiocloud.chat.feature.splash.SplashActivity.class);
                startActivity(intent);
                finish();
            }
        } else { //未同意
            new ProtectGuideDialog(GatewayActivity.this, () -> {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_PRIVACY_ACCEPTED, true);
                editor.apply();
            }).checkConfirm();
        }

        etEnterpriseId = findViewById(R.id.et_enterprise_id);

        CheckBox cbAgreement = findViewById(R.id.cb_agreement);
        findViewById(R.id.user_protocol).setOnClickListener(view -> TioBrowserActivity.start(GatewayActivity.this, "https://xxsj.wangliantong.com"));
        findViewById(R.id.user_private).setOnClickListener(view -> TioBrowserActivity.start(GatewayActivity.this, "https://yszc.wangliantong.com"));
        btnLogin = findViewById(R.id.btn_login);
        tvLink1 = findViewById(R.id.tv_link1);
        tvLink2 = findViewById(R.id.tv_link2);
        tvLink3 = findViewById(R.id.tv_link3);

        btnLogin.setOnClickListener(v -> {
            String enterpriseId = etEnterpriseId.getText().toString().trim();
            if (!cbAgreement.isChecked()) {
                Toast.makeText(GatewayActivity.this, "请勾选阅读并同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(enterpriseId)) {
                Toast.makeText(GatewayActivity.this, "请输入企业 ID", Toast.LENGTH_SHORT).show();
            } else {
                gatewayReq(enterpriseId);
            }
        });

        // 设置超链接点击事件
        tvLink1.setOnClickListener(v -> {
            String url = "https://mzxy.wangliantong.com";
            TioBrowserActivity.start(GatewayActivity.this, url);
        });

        tvLink2.setOnClickListener(v -> {
            String url = "https://yszc.wangliantong.com";
            TioBrowserActivity.start(GatewayActivity.this, url);
        });

        tvLink3.setOnClickListener(v -> {
            String url = "https://xxsj.wangliantong.com";
            TioBrowserActivity.start(GatewayActivity.this, url);
        });
    }

    private void gatewayReq(String enterpriseId) {
        SingletonProgressDialog.show_unCancel(this, getString(R.string.user_curr_info));
        new GatewayReq(enterpriseId).get(new TioCallbackImpl<GatewayResp>(){
            @Override
            public void onTioSuccess(GatewayResp data) {
                HttpPreferences.saveGatewayId(enterpriseId);
                if(data.expire == 1) {
                    HttpPreferences.saveBaseUrl(data.host);
                    HttpPreferences.saveResUrl(data.res);
                    Intent intent = new Intent(GatewayActivity.this, com.tiocloud.chat.feature.splash.SplashActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.showShort("企业已过期");
                }
                SingletonProgressDialog.dismiss();
            }

            @Override
            public void onTioError(String msg) {
                SingletonProgressDialog.dismiss();
                ToastUtils.showShort(msg);
            }
        });
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
}
