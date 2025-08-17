package com.watayouxiang.wallet.feature.withdraw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.KeyboardUtils;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletWithdrawActivityBinding;
import com.watayouxiang.wallet.feature.withdraw.mvp.Contract;
import com.watayouxiang.wallet.feature.withdraw.mvp.Presenter;
import com.watayouxiang.wallet.feature.withdraw_record.WithdrawRecordActivity;
import com.watayouxiang.wallet.tools.MoneyInputFilter;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   : 提现
 * </pre>
 */
public class WithdrawActivity extends EasyLightActivity<WalletWithdrawActivityBinding> implements Contract.View {

    public final ObservableField<String> amount = new ObservableField<>("");
    public final ObservableField<String> remainingRmb = new ObservableField<>("");
    private Presenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawActivity.class);
        context.startActivity(starter);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_withdraw_activity;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setData(this);
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

    // 提现记录
    private void clickWithdrawRecord(View view) {
        if (ClickUtils.isViewSingleClick(view)) {
            KeyboardUtils.hideSoftInput(this);
            WithdrawRecordActivity.start(this);
        }
    }

    // 全部提现
    public void clickWithdrawAll(View view) {
        presenter.getWalletInfo(true);
    }

    // 提现
    public void clickWithdraw(View view) {
        if (ClickUtils.isViewSingleClick(view)) {
            presenter.postWithdraw(amount.get());
        }
    }

    public void clickClear(View view) {
        binding.etAmount.setText("");
    }

    @Override
    public void resetUI() {
        // init Clear btn
        binding.ivClear.setVisibility(View.GONE);
        // init EditText
        binding.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                binding.ivClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }
        });
        binding.etAmount.setFilters(new InputFilter[]{new MoneyInputFilter(binding.etAmount)});
        // titleBar - rightBtn
        binding.titleBar.getTvRight().setOnClickListener(this::clickWithdrawRecord);
        // 当前金额
        remainingRmb.set("当前余额--元");
        // 弹出键盘
        KeyboardUtils.showSoftInput(binding.etAmount);
    }

    @Override
    public void onMoneyResp(String money) {
        remainingRmb.set(String.format(Locale.getDefault(), "当前余额%s元", money));
    }

    @Override
    public void onWithdrawAllResp(String money) {
        amount.set(money);

        binding.etAmount.postDelayed(() -> {
            int length = 0;
            Editable text = binding.etAmount.getText();
            if (text != null) {
                length = text.length();
            }
            binding.etAmount.setSelection(length);
        }, 100);
    }
}
