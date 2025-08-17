package com.tiocloud.account.feature.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.tiocloud.account.R;
import com.tiocloud.account.TioAccount;
import com.tiocloud.account.data.AccountSP;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.CodeRegisterReq;
import com.watayouxiang.httpclient.model.request.OnekeyRegisterReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.OnkeyLoginResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class InputNickActivity extends TioActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputnick);
        if (getIntent().getStringExtra("code") != null){
            //验证码
            inputNickForCode(getIntent().getStringExtra("phone"), getIntent().getStringExtra("code"));
        }else {
            OnkeyLoginResp onkeyLoginResp = (OnkeyLoginResp) getIntent().getSerializableExtra("onkey");
            inputNick(onkeyLoginResp);
        }
    }

    //未注册用户，输入昵称
    private void inputNickForCode(String phone, String code) {
        new InputNickDialog(new InputNickDialog.OnBtnListener() {
            @Override
            public void onClickPositive(View view, String submitTxt, InputNickDialog dialog) {
                if (TextUtils.isEmpty(submitTxt.trim())){
                    ToastUtils.showShort(getString(R.string.nickname_not_empty));
                    return;
                }
                dialog.dismiss();
                showLoadingDialog(getString(R.string.now_register));
                toRegiestForCode(submitTxt.trim(), phone, code);
//                postCreateGroup(submitTxt, uidArray, dialog);
            }

            @Override
            public void onClickNegative(View view, InputNickDialog dialog) {
                super.onClickNegative(view, dialog);
                ActivityUtils.finishAllActivities();
            }
        }).show_unCancel(this);
    }

    private void toRegiestForCode(String trim, String phone, String code) {
        CodeRegisterReq codeRegisterReq = new CodeRegisterReq(phone, code, trim);
        codeRegisterReq.setCancelTag(this);
        codeRegisterReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                loginStep2();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }

    //未注册用户，输入昵称
    private void inputNick(OnkeyLoginResp onkeyLoginResp) {
        new InputNickDialog(new InputNickDialog.OnBtnListener() {
            @Override
            public void onClickPositive(View view, String submitTxt, InputNickDialog dialog) {
                if (TextUtils.isEmpty(submitTxt.trim())){
                    ToastUtils.showShort(getString(R.string.nickname_not_empty));
                    return;
                }
                dialog.dismiss();
                showLoadingDialog(getString(R.string.now_register));
                toRegiest(submitTxt.trim(), onkeyLoginResp);
//                postCreateGroup(submitTxt, uidArray, dialog);
            }

            @Override
            public void onClickNegative(View view, InputNickDialog dialog) {
                super.onClickNegative(view, dialog);
                ActivityUtils.finishAllActivities();
            }
        }).show_unCancel(this);
    }

    private void toRegiest(String submitTxt, OnkeyLoginResp onkeyLoginResp) {
        OnekeyRegisterReq onekeyRegisterReq =
                new OnekeyRegisterReq(onkeyLoginResp.getTempId(), onkeyLoginResp.getToken(), submitTxt);
        onekeyRegisterReq.setCancelTag(this);
        onekeyRegisterReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                loginStep2();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
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
    private ProgressDialog mProgressDialog;
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
