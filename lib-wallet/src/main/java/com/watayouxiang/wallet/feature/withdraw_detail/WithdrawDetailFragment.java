package com.watayouxiang.wallet.feature.withdraw_detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.httpclient.model.vo.WithholdStatus;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletWithdrawDetailFragmentBinding;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/01
 *     desc   :
 * </pre>
 */
public class WithdrawDetailFragment extends TioFragment {

    private WalletWithdrawDetailFragmentBinding binding;

    public final ObservableField<String> brief = new ObservableField<>("提现到-民生银行(2934)");
    public final ObservableField<String> money_receive = new ObservableField<>("9.90");
    public final ObservableField<String> money_total = new ObservableField<>("¥10.00");
    public final ObservableField<String> money_fee = new ObservableField<>("¥0.01");
    public final ObservableField<String> serialNumber = new ObservableField<>("DE1897277FDDS17376GGF");
    public final ObservableField<String> bankInfo = new ObservableField<>("民生银行(2349)");
    public final ObservableField<String> submitTime = new ObservableField<>("2020-9-10  13:00:00");
    public final ObservableField<String> lastItem_key = new ObservableField<>("到账时间");
    public final ObservableField<String> lastItem_value = new ObservableField<>("2020-9-10  13:00:00");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WalletWithdrawDetailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setData(this);
        resetUI();
        initUI();
    }

    private void initUI() {
        WithdrawDetailVo vo = getWithdrawDetailActivity().getDetailVo();

        brief.set(String.format(Locale.getDefault(), "提现到-%s(%s)", vo.bankName, vo.bankCardLast4No));
        money_receive.set(vo.money_receive);
        money_total.set("¥" + vo.money_total);
        money_fee.set("¥" + vo.money_fee);
        serialNumber.set(vo.serialNumber);
        bankInfo.set(String.format(Locale.getDefault(), "%s(%s)", vo.bankName, vo.bankCardLast4No));
        submitTime.set(vo.submitTime);

        if (WithholdStatus.isSuccess(vo.status)) {
            lastItem_key.set("到账时间");
            lastItem_value.set(vo.finishTime);
        } else {
            lastItem_key.set("提现失败");
            lastItem_value.set(vo.failedReason);
        }

        binding.tivBankIcon.load(vo.bankIcon);

        if (WithholdStatus.SUCCESS.equals(vo.status)) {
            setStatus(1);
        } else if (WithholdStatus.PROCESS.equals(vo.status)) {
            setStatus(2);
        } else {
            setStatus(3);
        }
    }

    private void resetUI() {
        brief.set("");
        money_receive.set("");
        money_total.set("");
        money_fee.set("");
        serialNumber.set("");
        bankInfo.set("");
        submitTime.set("");
        lastItem_key.set("");
        lastItem_value.set("");
        binding.tivBankIcon.load(null);
        setStatus(1);
    }

    public WithdrawDetailActivity getWithdrawDetailActivity() {
        return (WithdrawDetailActivity) getActivity();
    }

    /**
     * @param status 1 成功，2 处理中，3 失败
     */
    private void setStatus(int status) {
        if (status == 1) {
            binding.ivPoint1.setImageResource(R.drawable.wallet_process_point_blue_light);
            binding.ivPoint2.setImageResource(R.drawable.wallet_process_point_blue_light);
            binding.ivPoint3.setImageResource(R.drawable.wallet_process_point_blue);
            binding.vLine1.setBackgroundColor(Color.parseColor("#AFCFFF"));
            binding.vLine2.setBackgroundColor(Color.parseColor("#AFCFFF"));
            binding.tvText1.setTextColor(Color.parseColor("#888888"));
            binding.tvText2.setTextColor(Color.parseColor("#888888"));
            binding.tvText3.setTextColor(Color.parseColor("#333333"));
            binding.tvText1.setText("提现申请");
            binding.tvText2.setText("银行处理中");
            binding.tvText3.setText("提现成功");
        } else if (status == 2) {
            binding.ivPoint1.setImageResource(R.drawable.wallet_process_point_blue_light);
            binding.ivPoint2.setImageResource(R.drawable.wallet_process_point_blue);
            binding.ivPoint3.setImageResource(R.drawable.wallet_process_point_gray);
            binding.vLine1.setBackgroundColor(Color.parseColor("#AFCFFF"));
            binding.vLine2.setBackgroundColor(Color.parseColor("#F1F1F1"));
            binding.tvText1.setTextColor(Color.parseColor("#888888"));
            binding.tvText2.setTextColor(Color.parseColor("#333333"));
            binding.tvText3.setTextColor(Color.parseColor("#888888"));
            binding.tvText1.setText("提现申请");
            binding.tvText2.setText("银行处理中");
            binding.tvText3.setText("提现成功");
        } else if (status == 3) {
            binding.ivPoint1.setImageResource(R.drawable.wallet_process_point_blue_light);
            binding.ivPoint2.setImageResource(R.drawable.wallet_process_point_blue_light);
            binding.ivPoint3.setImageResource(R.drawable.wallet_process_point_red);
            binding.vLine1.setBackgroundColor(Color.parseColor("#AFCFFF"));
            binding.vLine2.setBackgroundColor(Color.parseColor("#AFCFFF"));
            binding.tvText1.setTextColor(Color.parseColor("#888888"));
            binding.tvText2.setTextColor(Color.parseColor("#888888"));
            binding.tvText3.setTextColor(Color.parseColor("#333333"));
            binding.tvText1.setText("提现申请");
            binding.tvText2.setText("银行处理中");
            binding.tvText3.setText("提现失败");
        }
    }
}
