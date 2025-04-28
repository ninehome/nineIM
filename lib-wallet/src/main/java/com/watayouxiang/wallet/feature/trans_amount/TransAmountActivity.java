package com.watayouxiang.wallet.feature.trans_amount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.CommonTextInputDialog;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.androidutils.widget.imageview.WtImageView;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.TransAmountCreateReq;
import com.watayouxiang.httpclient.model.request.TransAmountPayReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.widget.keyboard.InputPwdUtils;
import com.watayouxiang.wallet.widget.keyboard.OnEncryPasswordInputFinish;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

import org.json.JSONObject;

import java.math.BigDecimal;

public class TransAmountActivity extends TioActivity {

    UserInfoResp userInfoResp;

    TioImageView ivAvatar;
    TextView tvName;
    TextView tvAddRemark;
    EditText etAmount;

    String transRemark="";

    public static void start(Context context, UserInfoResp userInfoResp) {
        Intent starter = new Intent(context, TransAmountActivity.class);
        starter.putExtra("userInfoResp", userInfoResp);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_trans_amount);
        userInfoResp = (UserInfoResp) getIntent().getSerializableExtra("userInfoResp");

        initView();
        initData();
    }

    private void initData() {
        ivAvatar.loadStatic(userInfoResp.avatar);
        if (!TextUtils.isEmpty(userInfoResp.remarkname)){
            tvName.setText(userInfoResp.remarkname);
        }else {
            tvName.setText(userInfoResp.nick);
        }

    }

    private void initView() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvAddRemark = findViewById(R.id.tv_add_remark);
        etAmount = findViewById(R.id.et_amount);
        etAmount.requestFocus();
        tvAddRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonTextInputDialog(getActivity())
                        .setTopTitle(getString(R.string.zhuanzhangshuoming))
                        .hintSubTitle()
                        .setPositiveText(getString(R.string.sure))
                        .setHintText(getString(R.string.please_input_shuoming))
                        .setMaxLimit(60)
                        .setEditHeight(120)
                        .setEdittext(transRemark)
                        .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                            @Override
                            public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                                dialog.dismiss();
                                transRemark = submitTxt;
                            }

                            @Override
                            public void onClickNegative(View view, CommonTextInputDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        findViewById(R.id.btnSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etAmount.getText())){
                    ToastUtils.showShort(getString(R.string.please_input_jinge));
                    return;
                }
                int amount;
                try {
                    String amountStr = etAmount.getText().toString();
                    amount = new BigDecimal(amountStr).multiply(new BigDecimal(100)).intValue();
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtils.showShort(getString(R.string.jinegeshicuowu));
                    return;
                }
                if (amount <= 0){
                    ToastUtils.showShort(getString(R.string.buzhichijie));
                    return;
                }
                TransAmountCreateReq transAmountCreateReq = new TransAmountCreateReq(userInfoResp.id, amount, transRemark);
                transAmountCreateReq.setCancelTag(this);
                transAmountCreateReq.post(new TioCallback<Object>() {
                    @Override
                    public void onTioSuccess(Object s) {
//                        System.out.println("zlb:"+s);
                        JsonObject jsonObject = new Gson().fromJson(s.toString(), JsonObject.class);
                        String serial = jsonObject.get("serial").getAsString();
                        System.out.println("serial:"+serial);
                        showPwdInput(serial, amount);
                    }

                    @Override
                    public void onTioError(String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
            }
        });
    }

    private void showPwdInput(String serial, Integer amount) {
        InputPwdUtils.input(this, amount, new OnEncryPasswordInputFinish() {
            @Override
            public void pwd(String pwd, long timestamp) {
                TransAmountPayReq transAmountPayReq = new TransAmountPayReq(serial, pwd, String.valueOf(timestamp));
                transAmountPayReq.setCancelTag(this);
                transAmountPayReq.post(new TioCallback<Object>() {
                    @Override
                    public void onTioSuccess(Object o) {
                        ToastUtils.showShort(getString(R.string.zhuanzhangchenggong));
                        finish();
                    }

                    @Override
                    public void onTioError(String msg) {
                        ToastUtils.showShort(msg);
                        finish();
                    }
                });
            }
        });
    }
}
