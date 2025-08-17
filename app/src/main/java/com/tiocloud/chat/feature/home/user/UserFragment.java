package com.tiocloud.chat.feature.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.tiocloud.chat.GatewayActivity;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.databinding.TioUserFragment2Binding;
import com.tiocloud.chat.feature.curr.detail.CurrDetailActivity;
import com.tiocloud.chat.feature.home.user.mvp.UserContract;
import com.tiocloud.chat.feature.home.user.mvp.UserPresenter;
import com.tiocloud.chat.feature.settings.SettingsActivity;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.HeadZoomScrollView;
import com.tiocloud.chat.yanxun.share.ShareActivity;
import com.watayouxiang.androidutils.feature.TioBrowserActivity;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.qrcode.feature.qrcode_decoder.QRCodeDecoderActivity;
import com.watayouxiang.qrcode.feature.qrcode_my.MyQRCodeActivity;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;

/**
 * author : TaoWang
 * date : 2020-02-03
 * desc :
 */
public class UserFragment extends TioFragment implements UserContract.View {

    private UserPresenter presenter;
    private TioUserFragment2Binding binding;

    @Override
    public void initUI() {
//        setStatusBarCustom(binding.flStatusBar);
        binding.tvName.setText("");
//        binding.tvEmail.setText("");
        binding.rlSettings.setOnClickListener(v -> SettingsActivity.start(getActivity()));
        binding.ivInfo.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));
        binding.hivAvatar.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));
//        binding.ll3.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));
        binding.rlModifyInfo.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));
        binding.llNick.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));
        if (TioConfig.OpenCloseConfig.needSharePage()){
//            binding.rlShare.setVisibility(View.VISIBLE);
//            binding.rlShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ShareActivity.start(getActivity());
//                }
//            });
        }else {
            binding.rlShare.setVisibility(View.GONE);
        }


        binding.iv4.setOnClickListener(v -> CurrDetailActivity.start(getActivity()));

//        if (TioConfig.OpenCloseConfig.isWalletEnable()) {
//            binding.rlWallet.setVisibility(View.VISIBLE);
//            binding.view1.setVisibility(View.VISIBLE);
//            binding.rlWallet.setOnClickListener(v -> {
//                if (ClickUtils.isViewSingleClick(v)){
//                    WalletActivity.start(getActivity());
//                }
//            });
//        } else {
//            binding.rlWallet.setVisibility(View.GONE);
//            binding.view1.setVisibility(View.GONE);
//        }

        binding.rlWallet.setOnClickListener(v -> {
            String url = "https://yszc.wangliantong.com";

            TioBrowserActivity.start(getActivity(), url);

        });


        if (TioConfig.OpenCloseConfig.showMyQrcode()) {
            binding.ivQrcode.setVisibility(View.VISIBLE);
            binding.ivQrcode.setOnClickListener(v -> {
                if (ClickUtils.isViewSingleClick(v))
                    MyQRCodeActivity.start(getActivity());
            });
        } else {
            binding.ivQrcode.setVisibility(View.GONE);
        }

        binding.qrDecode.setOnClickListener(v -> QRCodeDecoderActivity.start(getActivity()));

        binding.headzommview.setOnScrollListener(new HeadZoomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }

            @Override
            public void releaseUp(float distance) {
//                Log.d("zlb", "distance:"+distance);
                if (distance > 300){
                    presenter.updateUIData();
                }
            }
        });


    }

    @Override
    public void updateUI(UserCurrResp resp) {
        binding.tvName.setText(StringUtil.nonNull(resp.nick));
//        binding.tvEmail.setText(StringUtil.nonNull(resp.phone));
        binding.hivAvatar.tio_roundAvatar(resp.avatar);
        // 检测是否绑定手机
//        TioAccount.checkIsBindPhone(resp);
        // 存储当前uid
        TioDBPreferences.saveCurrUid(resp.id);
        TioDBPreferences.savePhone(resp.phone);
        TioDBPreferences.saveEmail(resp.email);
        String json = new Gson().toJson(resp.extData);
        TioDBPreferences.saveExData(json);
//        if (resp.loginname != null && !TextUtils.isEmpty(resp.loginname)){
//            binding.tvGedahao.setText("用户名："+resp.loginname);
//        }else {
//            binding.tvGedahao.setVisibility(View.INVISIBLE);
//        }
        binding.tvGedahao.setText(/*getString(R.string.app_name)*/TioConfig.OpenCloseConfig.getSitename() +getString(R.string.hao)+
                (TioConfig.OpenCloseConfig.userFragmentShowId()?resp.id:resp.loginname));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tio_user_fragment2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StatusBarUtil.setTranslucent(getActivity());
        presenter = new UserPresenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    public void onRefresh() {
        if (presenter != null) {
            presenter.updateUIData();
        }
    }
}
