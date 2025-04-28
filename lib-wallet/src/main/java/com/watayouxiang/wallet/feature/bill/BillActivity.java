package com.watayouxiang.wallet.feature.bill;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletBillActivityBinding;
import com.watayouxiang.wallet.feature.bill.adapter.BillFragmentAdapter;
import com.watayouxiang.wallet.feature.bill.adapter.BillTabAdapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   : 钱包明细
 * </pre>
 */
public class BillActivity extends EasyLightActivity<WalletBillActivityBinding> {
    public static void start(Context context) {
        Intent starter = new Intent(context, BillActivity.class);
        context.startActivity(starter);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_bill_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setData(this);
        // viewPager
        binding.vpPager.setAdapter(new BillFragmentAdapter(getSupportFragmentManager()));
        // indicator
        BillTabAdapter tabAdapter = new BillTabAdapter(binding.rvIndicator);
        tabAdapter.setViewPager(binding.vpPager);
    }
}
