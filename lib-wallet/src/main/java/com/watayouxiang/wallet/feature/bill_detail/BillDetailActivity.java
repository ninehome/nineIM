package com.watayouxiang.wallet.feature.bill_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletBillDetailActivityBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/30
 *     desc   : 交易明细
 * </pre>
 */
public class BillDetailActivity extends EasyLightActivity<WalletBillDetailActivityBinding> {
    private static final String BILL_DETAIL_VO = "BILL_DETAIL_VO";

    public final ObservableField<String> type = new ObservableField<>("红包");
    public final ObservableField<String> desc = new ObservableField<>("收红包");
    public final ObservableField<String> money = new ObservableField<>("+9.90");
    public final ObservableField<String> status = new ObservableField<>("处理中");
    public final ObservableField<String> time = new ObservableField<>("2020-9-10  13:24:56");
    public final ObservableField<String> serialNumber = new ObservableField<>("DE1897277FDDS17376GGF");
    private BillDetailVo detailVo;

    public static void start(Context context, BillDetailVo vo) {
        Intent starter = new Intent(context, BillDetailActivity.class);
        starter.putExtra(BILL_DETAIL_VO, vo);
        context.startActivity(starter);
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    public BillDetailVo getBillDetailVo() {
        return (BillDetailVo) getIntent().getSerializableExtra(BILL_DETAIL_VO);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_bill_detail_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setData(this);
        resetUI();
        detailVo = getBillDetailVo();
        initUI();
    }

    private void initUI() {
        String _type = detailVo.getBizModeStr();
        if (_type != null) {
            type.set(_type);
        }
        if (detailVo.getBizMode() == 1 || detailVo.getBizMode() == 2){
            binding.rlStatus.setVisibility(View.VISIBLE);
            binding.rlReason.setVisibility(View.VISIBLE);
            if (detailVo.getData().get("reviewremark") != null){
                binding.tvReason.setText(String.valueOf(detailVo.getData().get("reviewremark")));
            }else {
            }
        }else {
            binding.rlStatus.setVisibility(View.GONE);
            binding.rlReason.setVisibility(View.GONE);
        }

        String _modeDesc = detailVo.getModeDesc();
        if (_modeDesc != null) {
            desc.set(_modeDesc);
        }

        String _money = detailVo.getMoneyStr();
        if (_money != null) {
            this.money.set(_money);
        }

        String _time = detailVo.getTime();
        if (_time != null) {
            this.time.set(_time);
        }

        String _serialNumber = detailVo.getSerialNumber();
        if (_serialNumber != null) {
            this.serialNumber.set(_serialNumber);
        }

        String _StatusStr = detailVo.getBizStatusStr();
        if (_StatusStr != null) {
            status.set(_StatusStr);
        }
    }

    private void resetUI() {
        desc.set("");
        money.set("");
        type.set("");
        status.set("");
        time.set("");
        serialNumber.set("");
    }
}
