package com.watayouxiang.wallet.yanxun.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserWithdrawApplyReq;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.widget.keyboard.InputPwdUtils;
import com.watayouxiang.wallet.widget.keyboard.OnEncryPasswordInputFinish;
import com.watayouxiang.wallet.yanxun.entity.ScanWithDrawSelectType;
import com.watayouxiang.wallet.yanxun.utisl.ButtonColorChange;
import com.watayouxiang.wallet.yanxun.utisl.Money;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 扫码提现
 */
public class ScanWithdrawActivity extends TioActivity {
    private static final int SELECT_TYPE_REQUEST_CODE = 0x01;
    public static String amount;// 提现金额 单位:元
    private EditText mMentionMoneyEdit;
    private TextView mBalanceTv;
    private TextView mAllMentionTv;
    private ImageView mTypeIv;
    private TextView mTypeTv;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private UserRecieverAccountResp.UserWithdrawAccount drawSelectType;

    public static void start(Context context, String money) {
        Intent intent = new Intent(context, ScanWithdrawActivity.class);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setColor(this, getgetColor(R.color.white));
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_scan_withdraw);
        initActionbar();
        initView();
        intEvent();

        checkHasPayPassword();
    }

    private void checkHasPayPassword() {
//        boolean hasPayPassword = PreferenceUtils.getBoolean(this, Constants.IS_PAY_PASSWORD_SET + coreManager.getSelf().getUserId(), true);
//        if (!hasPayPassword) {
//            ToastUtil.showToast(this, R.string.tip_no_pay_password);
//            Intent intent = new Intent(this, ChangePayPasswordActivity.class);
//            startActivity(intent);
//            finish();
//        }
        return;
    }

    private void initActionbar() {
//        getSupportActionBar().hide();
//        findViewById(R.id.iv_title_left).setOnClickListener(v -> finish());
//        TextView mTvTitle = (TextView) findViewById(R.id.tv_title_center);
//        mTvTitle.setText(getString(R.string.withdraw));
    }

    private void initView() {
        mMentionMoneyEdit = (EditText) findViewById(R.id.tixianmoney);
        String money = getIntent().getStringExtra("money");
        if (!TextUtils.isEmpty(money)) {
            mMentionMoneyEdit.setText(money);
        }
        mBalanceTv = (TextView) findViewById(R.id.blance_weixin);
        mBalanceTv.setText(decimalFormat.format((float)TioDBPreferences.getCurrMoney()/100));
        mAllMentionTv = (TextView) findViewById(R.id.tixianall);
        mTypeIv = findViewById(R.id.type_iv);
        mTypeTv = findViewById(R.id.type_tv);
//        ButtonColorChange.colorChange(this, findViewById(R.id.sure_withdraw_btn));
    }

    private void intEvent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mMentionMoneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 删除开头的0，
                int end = 0;
                for (int i = 0; i < editable.length(); i++) {
                    char ch = editable.charAt(i);
                    if (ch == '0') {
                        end = i + 1;
                    } else {
                        break;
                    }
                }
                if (end > 0) {
                    editable.delete(0, end);
                    mMentionMoneyEdit.setText(editable);
                }
            }
        });

        mAllMentionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float money = TioDBPreferences.getCurrMoney();
                if (money < 100) {
                    ToastUtils.showShort(getString(R.string.tip_withdraw_too_little));
                } else {
                    mMentionMoneyEdit.setText(String.valueOf(money/100));
                }
            }
        });

        findViewById(R.id.ll_select).setOnClickListener(v -> startActivityForResult(new Intent(this, ScanWithdrawListActivity.class), SELECT_TYPE_REQUEST_CODE));

        findViewById(R.id.sure_withdraw_btn).setOnClickListener(v -> {
            String moneyStr = mMentionMoneyEdit.getText().toString();
            if (checkMoney(moneyStr)) {
                amount = Money.fromYuan(moneyStr);
                if (drawSelectType != null) {
//                    PaySecureHelper.inputPayPassword(this, getString(R.string.withdraw), amount, password -> {
//                        withdraw(amount, password);
//                    });
                    Integer f = (int)(Float.parseFloat(amount)*100);
                    InputPwdUtils.input(ScanWithdrawActivity.this, f, new OnEncryPasswordInputFinish() {
                        @Override
                        public void pwd(String pwd, long timestamp) {
                            withdraw(f, pwd, timestamp);
                        }
                    });
                } else {
                    ToastUtils.showShort(getString(R.string.please_select_withdraw_type));
                }
            }
        });
    }

    /**
     * 提交提现申请
     */
    public void withdraw(Integer money, String password, long timestamp) {
//        private String withdrawacount;
//        private String withdrawremark;
//        private String amountreal;
        Integer paytype = null;
        ////提现支付方式11微信SDK 12微信扫码  21支付宝SDK 22支付宝扫码 3银行卡转账 4现金 5活动
        if (drawSelectType.getAccounttype() == 2){
            paytype = 22;
        }else if (drawSelectType.getAccounttype() == 3){
            paytype = 3;
        }
        if (paytype == null){
            ToastUtils.showShort(getString(R.string.no_suport_tixian));
            return;
        }
        UserWithdrawApplyReq userWithdrawApplyReq = new UserWithdrawApplyReq(drawSelectType.getId(), money,password, timestamp);
        userWithdrawApplyReq.setCancelTag(this);
        userWithdrawApplyReq.get(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String commonResp) {
                ToastUtils.showShort(R.string.wait_server_notify);
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });


//        DialogHelper.showDefaulteMessageProgressDialog(mContext);
//
//        final Map<String, String> params = new HashMap<>();
//        params.put("amount", money);
//        params.put("withdrawAccountId", drawSelectType.getId());
//
//        PaySecureHelper.generateParam(
//                mContext, password, params,
//                money + drawSelectType.getId(),
//                t -> {
//                    DialogHelper.dismissProgressDialog();
//                    ToastUtil.showToast(this, this.getString(R.string.tip_pay_secure_place_holder, t.getMessage()));
//                }, (p, code) -> HttpUtils.post().url(coreManager.getConfig().MANUAL_PAY_WITHDRAW)
//                        .params(p)
//                        .build()
//                        .execute(new BaseCallback<Void>(Void.class) {
//
//                            @Override
//                            public void onResponse(ObjectResult<Void> result) {
//                                DialogHelper.dismissProgressDialog();
//                                if (Result.checkSuccess(mContext, result)) {
//                                    ToastUtil.showToast(mContext, R.string.wait_server_notify);
//                                }
//                            }
//
//                            @Override
//                            public void onError(Call call, Exception e) {
//                                DialogHelper.dismissProgressDialog();
//                                ToastUtil.showErrorData(mContext);
//                            }
//                        }));
    }

    private boolean checkMoney(String moneyStr) {
        if (TextUtils.isEmpty(moneyStr)) {
            ToastUtils.showShort(getString(R.string.tip_withdraw_empty));
        } else {
            if (Double.valueOf(moneyStr) < 1) {
                ToastUtils.showShort(getString(R.string.tip_withdraw_too_little));
            } /*else if (Double.valueOf(moneyStr) > coreManager.getSelf().getBalance()) {
                ToastUtils.showShort(getString(R.string.tip_balance_not_enough));
            }*/ else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_TYPE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            String str = data.getStringExtra("drawSelectType");
            UserRecieverAccountResp.UserWithdrawAccount drawSelectType = new Gson().fromJson(str, UserRecieverAccountResp.UserWithdrawAccount.class);
            if (drawSelectType != null) {
                this.drawSelectType = drawSelectType;
                mTypeIv.setVisibility(View.VISIBLE);
                if (drawSelectType.getAccounttype() == 2) {
                    mTypeIv.setImageResource(R.mipmap.ic_alipay_small);
                    mTypeTv.setText(drawSelectType.getAccountno());
                } else {
                    mTypeIv.setImageResource(R.mipmap.ic_band_small);
                    mTypeTv.setText(drawSelectType.getBankname() + "(" + drawSelectType.getAccountno() + ")");
                }
            }
        }
    }
}
