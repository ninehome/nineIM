package com.watayouxiang.wallet.feature.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletAccountActivityBinding;
import com.watayouxiang.wallet.feature.account.mvp.Contract;
import com.watayouxiang.wallet.feature.account.mvp.Presenter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   : 账户信息
 * </pre>
 */
public class AccountActivity extends EasyLightActivity<WalletAccountActivityBinding> implements Contract.View {

    public final ObservableField<Boolean> idCardStatus = new ObservableField<>();
    public final ObservableField<Boolean> phoneStatus = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> idCard = new ObservableField<>("");
    public final ObservableField<String> phone = new ObservableField<>("");

    private Presenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, AccountActivity.class);
        context.startActivity(starter);
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

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_account_activity;
    }

    @Override
    public void onWalletInfoResp(PayGetWalletInfoResp resp) {
        binding.tvNameMark.setVisibility(View.VISIBLE);
        binding.tvPhoneMark.setVisibility(View.VISIBLE);
//        name.set(resp.getNameDesc());
//        idCard.set(resp.getIdCardNoDesc());
//        phone.set(resp.getMobileDesc());
//        idCardStatus.set("SUCCESS".equals(resp.getIdCardRzStatus()));
//        phoneStatus.set("SUCCESS".equals(resp.getOperatorRzStatus()));
    }

    @Override
    public void resetUI() {
        binding.tvNameMark.setVisibility(View.INVISIBLE);
        binding.tvPhoneMark.setVisibility(View.INVISIBLE);
        idCardStatus.set(false);
        phoneStatus.set(false);
        name.set("");
        idCard.set("");
        phone.set("");
    }
}
