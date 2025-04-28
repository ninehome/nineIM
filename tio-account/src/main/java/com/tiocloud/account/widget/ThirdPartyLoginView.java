package com.tiocloud.account.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ShadowUtils;
import com.tiocloud.account.R;
import com.tiocloud.account.databinding.AccountThirdPartyLoginViewBinding;
import com.tiocloud.social.TioSocial;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.social.callback.SocialLoginCallback;
import com.watayouxiang.social.entities.ThirdInfoEntity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/21
 *     desc   :
 * </pre>
 */
public class ThirdPartyLoginView extends RelativeLayout implements SocialLoginCallback {

    private AccountThirdPartyLoginViewBinding binding;

    public ThirdPartyLoginView(Context context) {
        super(context);
        init(context);
    }

    public ThirdPartyLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThirdPartyLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.account_third_party_login_view, this, true);
        binding.setData(this);

        ShadowUtils.Config config = new ShadowUtils.Config()
                .setCircle()
                .setShadowColor(Color.TRANSPARENT, Color.parseColor("#A3C6F9"));
        ShadowUtils.apply(binding.ivQq, config);
        ShadowUtils.apply(binding.ivWx, config);
        setVisibility(GONE);

//        if (TioAccount.IS_DEVELOP) {
//            setVisibility(INVISIBLE);
//        }
    }

    public void onClick_qq(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        TioSocial.INSTANCE.socialHelper.loginQQ(getActivity(), this);
    }

    private Activity getActivity() {
        return (Activity) getContext();
    }

    public void onClick_wx(View view) {
        if (!ClickUtils.isViewSingleClick(view)) return;
        TioSocial.INSTANCE.socialHelper.loginWX(getActivity(), this);
    }

    public AccountThirdPartyLoginViewBinding getBinding() {
        return binding;
    }

    /**
     * 在activity中调用
     */
    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        TioSocial.INSTANCE.socialHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess(ThirdInfoEntity info) {

    }

    @Override
    public void socialError(String msg) {
        TioToast.showShort(msg);
    }
}
