package com.watayouxiang.wallet.feature.wallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ehking.sdk.wepay.utlis.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.CheckPayPwdReq;
import com.watayouxiang.httpclient.model.request.PayGetClientTokenReq;
import com.watayouxiang.httpclient.model.request.PayPwdUpdateReq;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletWalletActivityBinding;
import com.watayouxiang.wallet.feature.account.AccountActivity;
import com.watayouxiang.wallet.feature.bill.BillActivity;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.wallet.mvp.Contract;
import com.watayouxiang.wallet.feature.wallet.mvp.Presenter;
import com.watayouxiang.wallet.widget.keyboard.InputPwdUtils;
import com.watayouxiang.wallet.widget.keyboard.OnEncryPasswordInputFinish;
import com.watayouxiang.wallet.widget.keyboard.OpenCallback;
import com.watayouxiang.wallet.yanxun.activity.ScanRechargeActivity;
import com.watayouxiang.wallet.yanxun.activity.ScanWithdrawActivity;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/28
 *     desc   : 钱包主页
 * </pre>
 */
public class WalletActivity extends EasyActivity<WalletWalletActivityBinding> implements Contract.View {

    private Presenter presenter;

    public static void start(Activity activity) {
        Presenter.openWalletActivity(activity);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected Integer getStatusBarColor() {
        return Color.parseColor("#252A31");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_wallet_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this);
        binding.setView(this);
        presenter = new Presenter(this);
        presenter.init();
    }

    @Override
    public void onResume(int count) {
        super.onResume(count);
        if (count > 1) {
            presenter.getWalletInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    // 金额显隐
    public void clickEye(View v) {
        boolean moneyVisibility = isMoneyVisibility();
        if (moneyVisibility) {
            // 变成不可见
            SharedPreferencesUtil.savePreference(this, "app", "showMoney", "0");
            hideMoney();
        } else {
            // 变成可见
            SharedPreferencesUtil.savePreference(this, "app", "showMoney", "1");
            presenter.query$ShowMoney();
        }
    }

    // 提现
    public void clickWithdraw(View v) {
        if (ClickUtils.isViewSingleClick(v)){
            startActivity(new Intent(this, ScanWithdrawActivity.class));
        }
//            WithdrawActivity.start(this);
    }

    // 充值
    public void clickRecharge(View v) {
        if (ClickUtils.isViewSingleClick(v)){
            startActivity(new Intent(this, ScanRechargeActivity.class));
        }
//            RechargeActivity.start(this);
    }

    // 账号信息
    public void clickAccountDetail(View v) {
        if (ClickUtils.isViewSingleClick(v))
            AccountActivity.start(this);
    }

    // 钱包明细
    public void clickWalletDetail(View v) {
        if (ClickUtils.isViewSingleClick(v))
            BillActivity.start(this);
    }

    // 红包记录
    public void clickRedPaperRecord(View v) {
        if (ClickUtils.isViewSingleClick(v))
            RedPacketActivity.start(this);
    }

    // 银行卡
    public void clickBank(View v) {
        if (ClickUtils.isGlobalSingleClick())
            presenter.getClientToken(PayGetClientTokenReq.ACCESS_CARDlIST);
    }

    // 安全设置
    public void clickSafe(View v) {
        if (ClickUtils.isGlobalSingleClick()){
            InputPwdUtils.input(this, 0, new OnEncryPasswordInputFinish() {
                @Override
                public void pwd(String pwd, long timestamp) {
                    checkPwd(pwd,timestamp);
                }
            });
        }
//            presenter.getClientToken(PayGetClientTokenReq.ACCESS_SAFETY);
    }

    private void checkPwd(String pwd, long timestamp) {
        CheckPayPwdReq payPwdReq = new CheckPayPwdReq(pwd, timestamp);
        payPwdReq.setCancelTag(this);
        payPwdReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                setNewPwd(pwd, timestamp);
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }

    private void setNewPwd(String pwd, long timestamp) {
        InputPwdUtils.setPwdP(this, true, false, new OpenCallback() {
            @Override
            public void result(boolean success, String msg) {
                if (success){
                    PayPwdUpdateReq payPwdUpdateReq = new PayPwdUpdateReq(pwd, String.valueOf(timestamp), msg);
                    payPwdUpdateReq.setCancelTag(this);
                    payPwdUpdateReq.post(new TioCallback<String>() {
                        @Override
                        public void onTioSuccess(String payGetWalletInfoResp) {
                            ToastUtils.showShort(getString(R.string.xiugaichenggong));
                        }

                        @Override
                        public void onTioError(String msg) {
                            ToastUtils.showShort(msg);
                        }
                    });
                }
            }
        });
    }

    // 帮助中心
    public void clickHelp(View v) {
        // TODO: 11/18/20
    }

    @Override
    public void resetUI() {
        // 底部文字
        binding.tvBottomInfo.setText(String.format(Locale.getDefault(),
                getString(R.string.benfuwuyous), getString(R.string.app_name)));
        String booleanPreference = SharedPreferencesUtil.getPreference(this, "app", "showMoney");
        if (booleanPreference != null && booleanPreference.equals("1")){
            presenter.query$ShowMoney();
        }else {
            hideMoney();
        }
        // 余额显示隐藏处理
        // 隐藏帮助中心
        binding.rlHelp.setVisibility(View.GONE);
    }

    @Override
    public boolean isMoneyVisibility() {
        return binding.tvMoneyEye.isSelected();
    }

    @Override
    public void hideMoney() {
        binding.tvMoneyEye.setSelected(false);
        SpanUtils.with(binding.tvMoney)
                .append("******").setFontSize(26, true)
                .create();
    }

    @Override
    public void showMoney(String money) {
        binding.tvMoneyEye.setSelected(true);
        SpanUtils.with(binding.tvMoney)
                .append("¥ ").setFontSize(16, true)
                .append(money).setFontSize(26, true)
                .create();
    }
}
