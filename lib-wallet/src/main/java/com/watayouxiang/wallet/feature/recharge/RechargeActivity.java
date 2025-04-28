package com.watayouxiang.wallet.feature.recharge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.watayouxiang.wallet.databinding.WalletRechargeActivityBinding;
import com.watayouxiang.wallet.feature.recharge.mvp.Contract;
import com.watayouxiang.wallet.feature.recharge.mvp.Presenter;
import com.watayouxiang.wallet.tools.MoneyInputFilter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   : 充值
 * </pre>
 */
public class RechargeActivity extends EasyLightActivity<WalletRechargeActivityBinding> implements Contract.View {

    public final ObservableField<String> amount = new ObservableField<>("");
    private Presenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, RechargeActivity.class);
        context.startActivity(starter);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_recharge_activity;
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void clickRecharge(View view) {
        if (ClickUtils.isViewSingleClick(view)) {
            presenter.walletRecharge(amount.get());
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
        // 弹出键盘
        KeyboardUtils.showSoftInput(binding.etAmount);
    }
}
