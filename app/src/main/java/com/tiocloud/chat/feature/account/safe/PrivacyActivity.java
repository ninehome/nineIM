package com.tiocloud.chat.feature.account.safe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.PrivacyReq;
import com.watayouxiang.httpclient.model.request.SmsBeforeCheckReq;
import com.watayouxiang.httpclient.model.request.UpdateValidReq;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

public class PrivacyActivity extends TioActivity {

    private WtTitleBar titleBar;
    private CheckBox switch_verifyAddFriend;
    private CheckBox switch_uuid;
    private CheckBox switch_tel;
    private CheckBox switch_mail;
    private CheckBox switch_username;
    private CheckBox switch_group_chat;
    private CheckBox switch_qrcode;
    private CheckBox switch_business_card;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,PrivacyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_privacy_activity);
        initView();
        initData();
    }

    private void initData() {
        UserCurrReq userCurrReq = new UserCurrReq();
        userCurrReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        userCurrReq.setCancelTag(this);
        userCurrReq.post(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                UserCurrResp.EextData eextData = userCurrResp.extData;
                if(eextData != null){
                    switch_uuid.setChecked(eextData.uidFind.equals("1"));
                    switch_tel.setChecked(eextData.phoneFind.equals("1") );
                    switch_mail.setChecked(eextData.emailFind.equals("1"));
                    switch_username.setChecked(eextData.loginNameFind.equals("1"));
                    switch_group_chat.setChecked(eextData.groupAdd.equals("1"));
                    switch_qrcode.setChecked(eextData.qrcodeAdd.equals("1"));
                    switch_business_card.setChecked(eextData.cardAdd.equals("1"));
                }
                switch_verifyAddFriend.setChecked(userCurrResp.fdvalidtype == 1);
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }

    private void initView(){
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle(getString(R.string.privacy));
        switch_verifyAddFriend = findViewById(R.id.switch_verifyAddFriend);
        switch_verifyAddFriend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateValidReq(isChecked);
            }
        });
        switch_uuid = findViewById(R.id.switch_uuid);
        switch_uuid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String uuid = isChecked == true ? "1":"0";
                privacy("uidFind",uuid,switch_uuid);
            }
        });
        switch_tel = findViewById(R.id.switch_tel);
        switch_tel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tel = isChecked == true ? "1":"0";
                privacy("phoneFind",tel,switch_tel);
            }
        });
        switch_tel.setVisibility(View.GONE);
        switch_mail = findViewById(R.id.switch_mail);
        switch_mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String mail = isChecked ? "1":"0";
                privacy("emailFind",mail,switch_mail);
            }
        });
        switch_mail.setVisibility(View.GONE);
        switch_username = findViewById(R.id.switch_username);
        switch_username.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String username = isChecked ? "1":"0";
                privacy("loginNameFind",username,switch_username);
            }
        });
        switch_username.setVisibility(View.GONE);
        switch_group_chat = findViewById(R.id.switch_group_chat);
        switch_group_chat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String groupchat = isChecked ? "1":"0";
                privacy("groupAdd",groupchat,switch_group_chat);
            }
        });
        switch_qrcode = findViewById(R.id.switch_qrcode);
        switch_qrcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String qrcode = isChecked ? "1":"0";
                privacy("qrcodeAdd",qrcode,switch_qrcode);
            }
        });
        switch_business_card = findViewById(R.id.switch_business_card);
        switch_business_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String card = isChecked ? "1":"0";
                privacy("cardAdd",card,switch_business_card);
            }
        });

//        String json = TioDBPreferences.getExData();
//        if(!StringUtils.isEmpty(json) ){
//            UserCurrResp.EextData eextData = new Gson().fromJson(json,UserCurrResp.EextData.class);
//            if(eextData != null){
//                switch_uuid.setChecked(eextData.uidFind.equals("1"));
//                switch_tel.setChecked(eextData.phoneFind.equals("1") );
//                switch_mail.setChecked(eextData.emailFind.equals("1"));
//                switch_username.setChecked(eextData.loginNameFind.equals("1"));
//                switch_group_chat.setChecked(eextData.groupAdd.equals("1"));
//                switch_qrcode.setChecked(eextData.qrcodeAdd.equals("1"));
//                switch_business_card.setChecked(eextData.cardAdd.equals("1"));
//            }
//        }

    }

    public void updateValidReq(boolean isChecked) {
        TioHttpClient.post(this, new UpdateValidReq(isChecked), new TioCallbackImpl<Void>() {
            @Override
            public void onSuccess(Response<BaseResp<Void>> response) {
//                TioToast.showShort("验证成功");
            }

            @Override
            public void onError(Response<BaseResp<Void>> response) {
                super.onError(response);
                TioToast.showShort(response.message());
                switch_verifyAddFriend.setChecked(!isChecked);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void privacy(String name,String value,CheckBox checkBox) {

        PrivacyReq privacyReq = new PrivacyReq(name,value);
        TioHttpClient.post(privacyReq, new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
//                TioToast.showShort("验证成功");
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
                checkBox.setChecked(false);
            }
        });
    }
}
