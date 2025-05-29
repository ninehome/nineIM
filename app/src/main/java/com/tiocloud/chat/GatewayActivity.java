package com.tiocloud.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import com.tiocloud.chat.constant.TioConfig;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);

        etEnterpriseId = findViewById(R.id.et_enterprise_id);
        btnLogin = findViewById(R.id.btn_login);
         tvLink1 = findViewById(R.id.tv_link1);
        tvLink2 = findViewById(R.id.tv_link2);
        tvLink3 = findViewById(R.id.tv_link3);
        checkGateway();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterpriseId = etEnterpriseId.getText().toString().trim();
                if (TextUtils.isEmpty(enterpriseId)) {
                    Toast.makeText(GatewayActivity.this, "请输入企业 ID", Toast.LENGTH_SHORT).show();
                } else {


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
                String url = "https://mzsm.wangliantong.com";
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
                String url = "https://grxx.wangliantong.com";
                TioBrowserActivity.start(GatewayActivity.this, url);
            }
        });
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
}
