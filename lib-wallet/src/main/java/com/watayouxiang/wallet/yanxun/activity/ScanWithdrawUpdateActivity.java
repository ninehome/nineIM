package com.watayouxiang.wallet.yanxun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserRecieverAccountDeleteReq;
import com.watayouxiang.httpclient.model.request.UserRecieverAccountUpdateReq;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.entity.ScanWithDrawSelectType;
import com.watayouxiang.wallet.yanxun.utisl.ButtonColorChange;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 修改、删除提现账号
 */
public class ScanWithdrawUpdateActivity extends TioActivity {
    private UserRecieverAccountResp.UserWithdrawAccount drawSelectType;
    private EditText mAlipayNameEdit, mAlipayAccount;
    private EditText mBandCardOwnerNameEdit, mBandCardAccountEdit, mBandCardNameEdit, mBandCardSonNameEdit, mRemarkEdit;

    public static void start(Context context, String str) {
        Intent intent = new Intent(context, ScanWithdrawUpdateActivity.class);
        intent.putExtra("drawSelectType", str);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_withdraw_update);
        String str = getIntent().getStringExtra("drawSelectType");
        drawSelectType = new Gson().fromJson(str, UserRecieverAccountResp.UserWithdrawAccount.class);
        initActionbar();
        initView();
        intEvent();
    }

    private void initActionbar() {
        WtTitleBar mTvTitle = findViewById(R.id.titleBar);
        mTvTitle.setTitle(drawSelectType.getAccounttype() == 2 ? getString(R.string.select_withdraw_modify_alipay_account) : getString(R.string.select_withdraw_modify_band_card_account));
    }

    private void initView() {
        findViewById(drawSelectType.getAccounttype() == 2 ? R.id.ll1 : R.id.ll2).setVisibility(View.VISIBLE);
//        ButtonColorChange.colorChange(this, findViewById(R.id.sure_update_btn));
        mAlipayNameEdit = findViewById(R.id.alipay_name_et);
        mAlipayAccount = findViewById(R.id.alipay_account_et);
        mBandCardOwnerNameEdit = findViewById(R.id.band_card_owner_name_et);
        mBandCardAccountEdit = findViewById(R.id.band_card_account_et);
        mBandCardNameEdit = findViewById(R.id.band_name_et);
        mBandCardSonNameEdit = findViewById(R.id.band_son_name_et);
        mRemarkEdit = findViewById(R.id.band_card_remark_et);
        if (drawSelectType.getAccounttype() == 2) {
            mAlipayNameEdit.setText(drawSelectType.getAccountname());
            mAlipayAccount.setText(drawSelectType.getAccountno());
        } else {
            mBandCardOwnerNameEdit.setText(drawSelectType.getAccountname());
            mBandCardAccountEdit.setText(drawSelectType.getAccountno());
            mBandCardNameEdit.setText(drawSelectType.getBankname());
            mBandCardSonNameEdit.setText(drawSelectType.getBrandname());
            mRemarkEdit.setText(drawSelectType.getRemark());
        }
    }

    private void intEvent() {
        findViewById(R.id.sure_update_btn).setOnClickListener(v -> {
            update();
        });
        findViewById(R.id.sure_delete_btn).setOnClickListener(v -> {
            delete();
        });
    }

    /**
     * 修改提现账号
     */
    private void update() {
        if (drawSelectType.getAccounttype() == 2) {
            String alipayName = mAlipayNameEdit.getText().toString().trim();
            String alipayAccount = mAlipayAccount.getText().toString().trim();
            if (TextUtils.isEmpty(alipayName) || TextUtils.isEmpty(alipayAccount)) {
                ToastUtils.showShort(getString(R.string.must_edit_info_cannot_null));
                return;
            }
//            params.put("type", String.valueOf(1));
//            params.put("aliPayName", alipayName);
//            params.put("aliPayAccount", alipayAccount);
            drawSelectType.setAccountname(alipayName);
            drawSelectType.setAccountno(alipayAccount);
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
//            params.put("type", String.valueOf(2));
//            params.put("cardName", bandCardOwnerName);
//            params.put("bankCardNo", bandCardAccount);
//            params.put("bankName", bandCardName);
//            params.put("bankBranchName", bandCardSonName);
//            params.put("desc", remark);
            drawSelectType.setAccountname(bandCardOwnerName);
            drawSelectType.setAccountno(bandCardAccount);
            drawSelectType.setBankname(bandCardName);
            drawSelectType.setBrandname(bandCardSonName);
            drawSelectType.setRemark(remark);
        }
        UserRecieverAccountUpdateReq accountUpdateReq = new UserRecieverAccountUpdateReq(drawSelectType);
        accountUpdateReq.setCancelTag(this);
        accountUpdateReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                ToastUtils.showShort(getString(R.string.update_success));
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
//        DialogHelper.showDefaulteMessageProgressDialog(mContext);
//        HttpUtils.get().url(coreManager.getConfig().MANUAL_PAY_UPDATE_WITHDRAW_ACCOUNT)
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

    /**
     * 删除提现账号
     */
    private void delete() {
        UserRecieverAccountDeleteReq recieverAccountDeleteReq = new UserRecieverAccountDeleteReq(drawSelectType.getId());
        recieverAccountDeleteReq.setCancelTag(this);
        recieverAccountDeleteReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                ToastUtils.showShort(getString(R.string.del_success));
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
        //        DialogHelper.showDefaulteMessageProgressDialog(mContext);
//        HttpUtils.get().url(coreManager.getConfig().MANUAL_PAY_DELETE_WITHDRAW_ACCOUNT)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<ScanRecharge>(ScanRecharge.class) {
//
//                    @Override
//                    public void onResponse(ObjectResult<ScanRecharge> result) {
//                        DialogHelper.dismissProgressDialog();
//                        if (Result.checkSuccess(mContext, result)) {
//                            ToastUtil.showToast(mContext, getString(R.string.delete_ok));
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
