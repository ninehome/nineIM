package com.tiocloud.account.feature.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.data.AccountSP;
import com.tiocloud.account.databinding.AccountLoginActivityBinding;
import com.tiocloud.account.feature.login_sms.SmsLoginActivity;
import com.tiocloud.account.feature.register.RegisterActivity;
import com.tiocloud.account.feature.register.RegisterSuccessActivity;
import com.tiocloud.account.feature.retrieve_pwd.RetrievePwdActivity;
import com.tiocloud.account.mvp.login.LoginContract;
import com.tiocloud.account.mvp.login.LoginPresenter;
import com.tiocloud.account.widget.ThirdPartyLoginView;
import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.request.OnkeyLoginReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.OnkeyLoginResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/21
 *     desc   : 邮箱登录/手机号登录
 * </pre>
 */
public class LoginActivity extends EasyActivity<AccountLoginActivityBinding> implements LoginContract.View {

//    public final ObservableField<String> txt_top_right = new ObservableField<>("");
    public final ObservableField<String> txt_account = new ObservableField<>("");
    public final ObservableField<String> txt_pwd = new ObservableField<>("");
    private LoginPresenter presenter;
    private ProgressDialog mProgressDialog;
    private UMVerifyHelper mPhoneNumberAuthHelper;
    private AuthPageConfig mUIConfig;

    private UMTokenResultListener mTokenResultListener;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.account_login_activity;
    }

    @Override
    protected View getStatusBarHolder() {
//        return binding.statusBar;
        return null;
    }

    @Override
    protected Integer getStatusBarColor() {
//        return Color.parseColor("#DBEAFF");
        return Color.WHITE;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setTransparent(this);

        binding.setData(LoginActivity.this);
        presenter = new LoginPresenter(this);
        resetUI();

        if (TioAccount.useOnkeyLogin){
            sdkInit();
            mUIConfig = BaseUIConfig.init(this, mPhoneNumberAuthHelper);
            oneKeyLogin();
        }
        if (TioAccount.isWxLoginEnable){
            binding.llWx.setVisibility(View.VISIBLE);
        }else {
            binding.llWx.setVisibility(View.GONE);
        }
        binding.btnRegiest.setVisibility(TioAccount.showRegiest ? View.VISIBLE : View.INVISIBLE);
        if (ConstantUtils.checkEmail == false && ConstantUtils.checkCode == false){
            binding.forgetPwd.setVisibility(View.INVISIBLE);
        }else {
            binding.forgetPwd.setVisibility(View.VISIBLE);
        }
        if (ConstantUtils.checkEmail){
            binding.etAccount.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_email), null, null, null);
        }

    }

    private void oneKeyLogin() {
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(getApplicationContext(), mTokenResultListener);
        mUIConfig.configAuthPage();
        getLoginToken(15000);
    }

    /**
     * 拉起授权页
     * @param timeout 超时时间
     */
    public void getLoginToken(int timeout) {
        mPhoneNumberAuthHelper.getLoginToken(this, timeout);
        showLoadingDialog(getString(R.string.opening_authroizon_page));
    }

    public void sdkInit() {

        mTokenResultListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                hideLoadingDialog();
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "唤起授权页成功：" + s);
                    }

                    if (UMResultCode.CODE_GET_TOKEN_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "获取token成功：" + s);
                        getResultWithToken(tokenRet.getToken());
                        mUIConfig.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
                hideLoadingDialog();
                UMTokenRet tokenRet = null;
                mPhoneNumberAuthHelper.quitLoginPage();
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    ToastUtils.showShort(tokenRet.getMsg());
                    if (UMResultCode.CODE_ERROR_USER_CANCEL.equals(tokenRet.getCode())) {
                        LoginActivity.start(LoginActivity.this);
//                        finish();
                    } else {
//                        Toast.makeText(getApplicationContext(), "一键登录失败切换到其他登录方式", Toast.LENGTH_SHORT).show();
//                        Intent pIntent = new Intent(OneKeyLoginActivity.this, MessageActivity.class);
//                        startActivityForResult(pIntent, 1002);
                        LoginActivity.start(LoginActivity.this);
//                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mUIConfig.release();
            }
        };
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(this, mTokenResultListener);
        mPhoneNumberAuthHelper.setAuthSDKInfo(TioAccount.UM_APPSECRY);
    }

    public void getResultWithToken(final String token) {
        OnkeyLoginReq onkeyLoginReq = new OnkeyLoginReq(token, TioAccount.UM_APPKEY);
        onkeyLoginReq.setCancelTag(this);
        onkeyLoginReq.post(new TioCallback<OnkeyLoginResp>() {
            @Override
            public void onTioSuccess(OnkeyLoginResp s) {
                if (s == null){
                    loginStep2();
                }else {
                    if (TioAccount.inviteEnable){
                        ToastUtils.showShort(getString(R.string.first_regiser));
                        LoginActivity.start(LoginActivity.this);
                        return;
                    }
                    LogUtils.e("onkey:==>"+s);
                    s.setToken(token);
//                    inputNick(s);
                    Intent intent = new Intent(LoginActivity.this, InputNickActivity.class);
                    intent.putExtra("onkey", s);
                    startActivity(intent);
                    ActivityUtils.finishAllActivities();
                }
            }

            @Override
            public void onTioError(String msg) {
                LogUtils.e("onkeyerr:==>"+msg);
            }
        });

    }

    private void loginStep2() {
        // 获取用户信息
        new UserCurrReq().setCancelTag(this).get(new TioCallbackImpl<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
//                proxy.onSuccess(userCurrResp);
//                proxy.onFinish();
                login(userCurrResp, userCurrResp.loginname);
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
//                proxy.onFailure(msg);
//                proxy.onFinish();
            }
        });
    }

    public static void login(UserCurrResp currInfo, String account) {
        // 存储登录名
        AccountSP.putLoginName(account);
        // 存储当前uid
        TioDBPreferences.saveCurrUid(currInfo.id);
        TioDBPreferences.savePhone(currInfo.phone);
        TioDBPreferences.saveEmail(currInfo.email);
        String json = new Gson().toJson(currInfo.extData);
        TioDBPreferences.saveExData(json);
        // 存储用户信息
        CurrUserTableCrud.insert(currInfo);
        // 打开主页
        TioAccount.getBridge().startMainActivity(Utils.getApp());
        // 关闭其他页面
        ActivityUtils.finishAllActivities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resetUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ThirdPartyLoginView.onActivityResult(requestCode, resultCode, data);
    }

    private void resetUI() {
//        txt_top_right.set("账号注册");

        // 设置显示账号
        String account = AccountSP.getLoginName();
        if (account != null) {
            txt_account.set(account);
        }
        if (TioMap.currentLau.equals("vi")){
            binding.tvTitle.setText("Đăng Nhập");
        }else {
            binding.tvTitle.setText(getString(R.string.pwd_login_app)+/*getString(R.string.app_name)*/TioAccount.sitename);
        }

        // 验证码登录
        if (ConstantUtils.checkCode == false || !TioAccount.OPEN_SMS_LOGIN) {
            binding.tvCodeLogin.setVisibility(View.INVISIBLE);
        }

        if (TioAccount.useOnkeyLogin){
            binding.tvTitle2.setVisibility(View.VISIBLE);
            binding.tvTitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    OneKeyLoginActivity.start(LoginActivity.this);
                    oneKeyLogin();
                }
            });
        }else {
            binding.tvTitle2.setVisibility(View.GONE);
        }
    }

    // 注册
    public void onClick_topRight(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        RegisterActivity.start(getActivity());
//        finish();
    }

    // 登录
    public void onClick_ok(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        presenter.pwdLogin(txt_account.get(), txt_pwd.get(), getActivity());
    }

    // 验证码登录
    public void onClick_codeLogin(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        SmsLoginActivity.start(getActivity());
//        finish();
    }

    // 忘记密码
    public void onClick_forgetPwd(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        RetrievePwdActivity.start(getActivity());
    }

    public void showLoadingDialog(String hint) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(hint);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
