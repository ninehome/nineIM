package com.tiocloud.account.feature.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.tiocloud.account.R;
import com.tiocloud.account.feature.login_sms.SmsLoginActivity;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMAuthUIControlClickListener;
import com.umeng.umverify.listener.UMCustomInterface;
import com.umeng.umverify.view.UMAbstractPnsViewDelegate;
import com.umeng.umverify.view.UMAuthRegisterViewConfig;
import com.umeng.umverify.view.UMAuthRegisterXmlConfig;
import com.umeng.umverify.view.UMAuthUIConfig;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;

public class DialogPortConfig extends BaseUIConfig {

    public DialogPortConfig(Activity activity, UMVerifyHelper authHelper) {
        super(activity, authHelper);
    }

    @Override
    public void configAuthPage() {
        mAuthHelper.removeAuthRegisterXmlConfig();
        mAuthHelper.removeAuthRegisterViewConfig();
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        updateScreenSize(authPageOrientation);
        int dialogWidth = (int) (mScreenWidthDp * 0.8f);
        int dialogHeight = (int) (mScreenHeightDp * 0.55f);
        int designHeight = dialogHeight - 50;
        int unit = designHeight / 10;
        int logBtnHeight = (int) (unit * 1);

        mAuthHelper.addAuthRegistViewConfig("switch_msg", new UMAuthRegisterViewConfig.Builder()
//                .setView(initSwitchView(unit * 6))
                .setRootViewId(UMAuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new UMCustomInterface() {
                    @Override
                    public void onClick(Context context) {
//                        Toast.makeText(mContext, "切换到短信登录方式", Toast.LENGTH_SHORT).show();
//                        Intent pIntent = new Intent(mActivity, LoginActivity.class);
//                        mActivity.startActivityForResult(pIntent, 1002);
                        if (ConstantUtils.checkCode){
                            SmsLoginActivity.start(context);
                        }else {
                            LoginActivity.start(context);
                        }
                        mAuthHelper.quitLoginPage();
                    }
                })
                .build());


        mAuthHelper.addAuthRegisterXmlConfig(new UMAuthRegisterXmlConfig.Builder()
                .setLayout(R.layout.custom_port_dialog_action_bar, new UMAbstractPnsViewDelegate() {
                    @Override
                    public void onViewCreated(View view) {
                        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAuthHelper.quitLoginPage();
                            }
                        });
                    }
                })
                .build());


        mAuthHelper.setAuthUIConfig(new UMAuthUIConfig.Builder()
                .setAppPrivacyOne(mContext.getString(R.string.privacy_protocal), "https://www.baidu.com")
                .setAppPrivacyColor(Color.GRAY, Color.parseColor("#002E00"))
                .setPrivacyState(false)
                .setCheckboxHidden(true)
                .setNavHidden(true)
                .setSwitchAccHidden(true)
                .setNavReturnHidden(true)
                .setDialogBottom(false)

                .setNavColor(Color.TRANSPARENT)
                .setWebNavColor(Color.BLUE)

                .setLogoOffsetY(0)
                .setLogoWidth(62)
                .setLogoHeight(62)
                .setLogoImgPath("mytel_app_launcher")

                .setNumFieldOffsetY(unit + 35)
                .setNumberSize(27)

                .setLogBtnWidth(dialogWidth - 30)
                .setLogBtnMarginLeftAndRight(15)
                .setLogBtnHeight(logBtnHeight)
                .setLogBtnTextSize(16)
                .setLogBtnBackgroundPath("login_btn_bg")

                .setLogBtnOffsetY(unit * 4)
                .setSloganText(mContext.getString(R.string.phone_login))
                .setSloganOffsetY(unit * 3)
                .setSloganTextSize(14)

                .setPageBackgroundPath("dialog_page_background")

                .setAuthPageActIn("in_activity", "out_activity")
                .setAuthPageActOut("in_activity", "out_activity")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setDialogWidth(dialogWidth)
                .setDialogHeight(dialogHeight)
                .setScreenOrientation(authPageOrientation)
                .create());
    }
}
