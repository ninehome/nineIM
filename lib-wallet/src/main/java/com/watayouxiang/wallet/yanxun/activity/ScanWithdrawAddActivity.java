package com.watayouxiang.wallet.yanxun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserRecieverAccountAddReq;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.utisl.ButtonColorChange;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加提现账号
 */
public class ScanWithdrawAddActivity extends TioActivity {
    private int mAddType;
    private EditText mAlipayNameEdit, mAlipayAccount;
    private EditText mBandCardOwnerNameEdit, mBandCardAccountEdit, mBandCardNameEdit, mBandCardSonNameEdit, mRemarkEdit;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ScanWithdrawAddActivity.class);
        intent.putExtra("add_type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_withdraw_add);
//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
        mAddType = getIntent().getIntExtra("add_type", 1);// 1 支付宝 2 银行卡
        initActionbar();
        initView();
        intEvent();
    }

    private void initActionbar() {
        WtTitleBar mTvTitle = findViewById(R.id.titleBar);
        mTvTitle.setTitle(mAddType == 1 ? getString(R.string.select_withdraw_add_alipay_account) : getString(R.string.select_withdraw_add_band_card_account));
    }

    private void initView() {
        findViewById(mAddType == 1 ? R.id.ll1 : R.id.ll2).setVisibility(View.VISIBLE);
        mAlipayNameEdit = findViewById(R.id.alipay_name_et);
        mAlipayAccount = findViewById(R.id.alipay_account_et);
        mBandCardOwnerNameEdit = findViewById(R.id.band_card_owner_name_et);
        mBandCardAccountEdit = findViewById(R.id.band_card_account_et);
        mBandCardNameEdit = findViewById(R.id.band_name_et);
        mBandCardSonNameEdit = findViewById(R.id.band_son_name_et);
        mRemarkEdit = findViewById(R.id.band_card_remark_et);
    }

    private void intEvent() {
        findViewById(R.id.sure_band_btn).setOnClickListener(v -> {
            band();
        });
    }

    private void band() {
        UserRecieverAccountResp.UserWithdrawAccount account = new UserRecieverAccountResp.UserWithdrawAccount();
        if (mAddType == 1) {
            String alipayName = mAlipayNameEdit.getText().toString().trim();
            String alipayAccount = mAlipayAccount.getText().toString().trim();
            if (TextUtils.isEmpty(alipayName) || TextUtils.isEmpty(alipayAccount)) {
                ToastUtils.showShort(getString(R.string.must_edit_info_cannot_null));
                return;
            }
            account.setAccounttype(2);
            account.setAccountname(alipayName);
            account.setAccountno(alipayAccount);
//            params.put("type", String.valueOf(1));
//            params.put("aliPayName", alipayName);
//            params.put("aliPayAccount", alipayAccount);
        } else {
            String bandCardOwnerName = mBandCardOwnerNameEdit.getText().toString().trim();
            String bandCardAccount = mBandCardAccountEdit.getText().toString().trim();
            String bandCardName = mBandCardNameEdit.getText().toString().trim();
            String bandCardSonName = mBandCardSonNameEdit.getText().toString().trim();
            String remark = mRemarkEdit.getText().toString().trim();
            if (TextUtils.isEmpty(bandCardOwnerName) || TextUtils.isEmpty(bandCardAccount) || TextUtils.isEmpty(bandCardName)) {
                ToastUtils.showShort(getString(R.string.must_edit_info_cannot_null));
                return;
            }
            account.setAccounttype(3);
            account.setAccountname(bandCardOwnerName);
            account.setAccountno(bandCardAccount);
            account.setBankname(bandCardName);
            account.setBrandname(bandCardSonName);
            account.setRemark(remark);
//            params.put("type", String.valueOf(2));
//            params.put("cardName", bandCardOwnerName);
//            params.put("bankCardNo", bandCardAccount);
//            params.put("bankName", bandCardName);
//            params.put("bankBranchName", bandCardSonName);
//            params.put("desc", remark);
        }
        UserRecieverAccountAddReq accountAddReq = new UserRecieverAccountAddReq(account);
        accountAddReq.setCancelTag(this);
        accountAddReq.get(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String commonResp) {
                ToastUtils.showShort(getString(R.string.add_success));
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
//        DialogHelper.showDefaulteMessageProgressDialog(mContext);
//        HttpUtils.get().url(coreManager.getConfig().MANUAL_PAY_ADD_WITHDRAW_ACCOUNT)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<ScanRecharge>(ScanRecharge.class) {
//
//                    @Override
//                    public void onResponse(ObjectResult<ScanRecharge> result) {
//                        DialogHelper.dismissProgressDialog();
//                        if (Result.checkSuccess(mContext, result)) {
//                            ToastUtil.showToast(mContext, getString(R.string.addsuccess));
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        DialogHelper.dismissProgressDialog();
//                        ToastUtil.showErrorNet(mContext);
//                    }
//                });

    }
}
