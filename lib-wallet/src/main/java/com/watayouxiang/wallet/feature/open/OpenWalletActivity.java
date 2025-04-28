package com.watayouxiang.wallet.feature.open;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.page.easy.EasyLightActivity;
import com.watayouxiang.androidutils.util.BrowserUtils;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.TioWallet;
import com.watayouxiang.wallet.databinding.WalletOpenWalletActivityBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/10/30
 *     desc   : 钱包账户开通说明
 * </pre>
 */
public class OpenWalletActivity extends EasyLightActivity<WalletOpenWalletActivityBinding> {

    public final ObservableField<String> userName = new ObservableField<>("");
    public final ObservableField<String> userId = new ObservableField<>("");
    public final ObservableField<String> userPhone = new ObservableField<>("");
    public final ObservableField<Boolean> isCheckbox = new ObservableField<>(false);
    private OpenWalletViewModel viewModel;

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, OpenWalletActivity.class);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setView(this);
        binding.tvCheckbox.setSelected(isCheckbox.get());
        viewModel = newViewModel(OpenWalletViewModel.class);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_open_wallet_activity;
    }

    public void clickCheckBox(View view) {
        boolean selected = view.isSelected();
        isCheckbox.set(!selected);
        view.setSelected(isCheckbox.get());
    }

    public void clickServiceProtocol(View view) {
        BrowserUtils.openBrowserActivity(this, TioWallet.PAY_EASE_SERVICE_AGREEMENT);
    }

    public void clickPrivateProtocol(View view) {
        BrowserUtils.openBrowserActivity(this, TioWallet.PAY_EASE_PRIVACY_POLICY);
    }

    public void clickAgreeBtn(View view) {
        if (ClickUtils.isViewSingleClick(view))
            viewModel.reqOpenWallet(this);
    }
}
