package com.watayouxiang.wallet.yanxun.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.AdminRecieverAccountReq;
import com.watayouxiang.httpclient.model.response.AdminRecieverAccountResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.dialog.ScanRechargeBandDialog;
import com.watayouxiang.wallet.yanxun.dialog.ScanRechargeWxAlipayDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 充值
 */
public class ScanRechargeActivity extends TioActivity implements View.OnClickListener {
    private List<BigDecimal> mRechargeList = new ArrayList<>();
    private List<CheckedTextView> mRechargeMoneyViewList = new ArrayList<>();
    private EditText mSelectMoneyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_scan_recharge);

        initActionBar();
        initData();
        initView();
        initEvent();
    }

    private void initActionBar() {
//        getSupportActionBar().hide();
//        findViewById(R.id.iv_title_left).setOnClickListener(v -> finish());
//        TextView tvTitle = (TextView) findViewById(R.id.tv_title_center);
//        tvTitle.setText(getString(R.string.recharge));
    }

    private void initData() {
        mRechargeList.add(new BigDecimal("10"));
        mRechargeList.add(new BigDecimal("20"));
        mRechargeList.add(new BigDecimal("50"));
        mRechargeList.add(new BigDecimal("100"));
        mRechargeList.add(new BigDecimal("200"));
        mRechargeList.add(new BigDecimal("500"));
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        TableLayout tableLayout = findViewById(R.id.tableLayoutRechargeMoney);
//        SkinUtils.Skin skin = SkinUtils.getSkin(this);
//        ColorStateList highlightColorState = skin.getHighlightColorState();
        View.OnClickListener onMoneyClickListener = v -> {
            for (int i = 0, mRechargeMoneyViewListSize = mRechargeMoneyViewList.size(); i < mRechargeMoneyViewListSize; i++) {
                CheckedTextView textView = mRechargeMoneyViewList.get(i);
                if (textView == v) {
                    mSelectMoneyTv.setText(mRechargeList.get(i).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                    textView.setChecked(true);
                } else {
                    textView.setChecked(false);
                }
            }
        };
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int k = 0; k < tableRow.getChildCount(); k++) {
                CheckedTextView tvMoney = tableRow.getChildAt(k).findViewById(R.id.tvRechargeMoney);
                tvMoney.setOnClickListener(onMoneyClickListener);
//                tvMoney.setTextColor(highlightColorState);
//                ViewCompat.setBackgroundTintList(tvMoney, ColorStateList.valueOf(skin.getAccentColor()));
                int index = i * tableRow.getChildCount() + k;
                tvMoney.setText(mRechargeList.get(index).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + getString(R.string.yuan));
                mRechargeMoneyViewList.add(tvMoney);
            }
        }

        findViewById(R.id.btn_charge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(getCurrentMoney(), "0")) {
                    ToastUtils.showShort(getString(R.string.transfer_input_money));
                    return;
                }
                int id = ((RadioGroup)findViewById(R.id.rgGroup)).getCheckedRadioButtonId();
                if (id <= 0){
                    ToastUtils.showShort(getString(R.string.please_input_pay_type));
                    return;
                }
                if (id == R.id.rb_wx) {
                    show(1);
                } else if (id == R.id.rb_zfb) {
                    show(2);
                } else if (id == R.id.rb_band) {
                    show(3);
                }
            }
        });

        mSelectMoneyTv = findViewById(R.id.select_money_tv);

//        mSelectMoneyTv.setTextColor(skin.getAccentColor());
        mSelectMoneyTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mSelectMoneyTv.setText(s);
                        mSelectMoneyTv.setSelection(s.length());
                    }
                }

                if (!TextUtils.isEmpty(s) && s.toString().trim().substring(0, 1).equals(".")) {
                    s = "0" + s;
                    mSelectMoneyTv.setText(s);
                    mSelectMoneyTv.setSelection(1);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mSelectMoneyTv.setText(s.subSequence(0, 1));
                        mSelectMoneyTv.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mSelectMoneyTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                    mSelectMoneyTv.setHint(null);
                } else {
                    // invisible占着高度，
                    mSelectMoneyTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    mSelectMoneyTv.setHint(R.string.need_input_money);
                }
            }
        });
    }

    private void initEvent() {
        findViewById(R.id.recharge_wechat).setOnClickListener(this);
        findViewById(R.id.recharge_alipay).setOnClickListener(this);
        findViewById(R.id.recharge_band).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.equals(getCurrentMoney(), "0")) {
//            ToastUtil.showToast(mContext, getString(R.string.transfer_input_money));
            ToastUtils.showShort(getString(R.string.transfer_input_money));
            return;
        }
        int id = v.getId();
        if (id == R.id.recharge_wechat) {
            show(1);
        } else if (id == R.id.recharge_alipay) {
            show(2);
        } else if (id == R.id.recharge_band) {
            show(3);
        }
    }

    private String getCurrentMoney() {
        if (TextUtils.isEmpty(mSelectMoneyTv.getText())) {
            return "0";
        }
        return new BigDecimal(mSelectMoneyTv.getText().toString()).stripTrailingZeros().toPlainString();
    }

    private void show(int type) {
//        DialogHelper.showDefaulteMessageProgressDialog(this);

//        Map<String, String> params = new HashMap<>();
//        params.put("type", String.valueOf(type));// 支付方式 1.微信 2.支付宝 3.银行卡
//
        AdminRecieverAccountReq recieverAccountReq = new AdminRecieverAccountReq(type);
        recieverAccountReq.setCancelTag(this);
        recieverAccountReq.get(new TioCallback<AdminRecieverAccountResp>() {
            @Override
            public void onTioSuccess(AdminRecieverAccountResp adminRecieverAccountResp) {
                LogUtils.d("===>"+new Gson().toJson(adminRecieverAccountResp));
                if (type == 3) {
                    ScanRechargeBandDialog scanRechargeBandDialog = new ScanRechargeBandDialog(ScanRechargeActivity.this
                            , /*scanRecharge.getName()*/adminRecieverAccountResp.getName()
                            , /*scanRecharge.getBankCard()*/adminRecieverAccountResp.getBankcard()
                            , /*scanRecharge.getBankName()*/adminRecieverAccountResp.getBankname()
                            , getCurrentMoney());
                    scanRechargeBandDialog.show();
                } else {
                    ScanRechargeWxAlipayDialog scanRechargeWxAlipayDialog = new ScanRechargeWxAlipayDialog(ScanRechargeActivity.this
                            , type,
                            getCurrentMoney(),
                            adminRecieverAccountResp.getUrl());
                    scanRechargeWxAlipayDialog.show();
                }
            }

            @Override
            public void onTioError(String msg) {
                LogUtils.e("===ERROR==>"+msg);
                ToastUtils.showShort(msg);
            }
        });
    }
}
