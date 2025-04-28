package com.tiocloud.account.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountProtocolViewBinding;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.TioWebUrl;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/23
 *     desc   :
 * </pre>
 */
public class ProtocolView extends RelativeLayout {

    public final ObservableField<Boolean> isCheckbox = new ObservableField<>(false);

    private AccountProtocolViewBinding binding;

    public ProtocolView(Context context) {
        super(context);
        init(context);
    }

    public ProtocolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProtocolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.account_protocol_view, this, true);
        binding.setData(this);

        binding.tvCheckBox.setSelected(isCheckbox.get());
    }

    // CheckBox
    public void onClick_tvCheckBox(View view) {
        isCheckbox.set(!binding.tvCheckBox.isSelected());
        binding.tvCheckBox.setSelected(isCheckbox.get());
    }

    // 协议
    public void onClick_xieyi(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        TioBrowserActivity.start(getActivity(), TioHttpClient.getBaseUrl() + TioWebUrl.TIO_USER_PROTOCOL);
    }

    // 政策
    public void onClick_zhengce(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        TioBrowserActivity.start(getActivity(), TioHttpClient.getBaseUrl() + TioWebUrl.TIO_PRIVATE_POLICY);
    }

    private Activity getActivity() {
        return (Activity) getContext();
    }

    public AccountProtocolViewBinding getBinding() {
        return binding;
    }
}
