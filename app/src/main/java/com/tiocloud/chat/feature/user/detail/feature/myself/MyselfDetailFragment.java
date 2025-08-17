package com.tiocloud.chat.feature.user.detail.feature.myself;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioMyselfInfoFragmentBinding;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.user.detail.feature.myself.mvp.MyselfDetailContract;
import com.tiocloud.chat.feature.user.detail.feature.myself.mvp.MyselfDetailPresenter;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TioImageBrowser;
import com.tiocloud.chat.util.StringUtil;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 * 自己 - 信息详情页
 */
public class MyselfDetailFragment extends TioFragment implements MyselfDetailContract.View {

    private MyselfDetailPresenter presenter;
    private TioMyselfInfoFragmentBinding binding;

    public static TioFragment create(@NonNull String uid) {
        MyselfDetailFragment fragment = new MyselfDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_USER_ID, uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    public String getUid() {
        return getArguments().getString(TioExtras.EXTRA_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tio_myself_info_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MyselfDetailPresenter(this);
        presenter.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.reqUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void initViews() {
        binding.tvAddress.setText("");
        binding.tvName.setText("");
        binding.tvSign.setText("");

        binding.tvP2pTalk.setOnClickListener(view1 -> P2PSessionActivity.active(getActivity(), getUid()));
    }

    @Override
    public void onUserInfoResp(UserInfoResp resp) {
        if (TioMap.currentLau.equals("vi")){
            binding.tvNumber.setText(/*getString(R.string.app_name)+getString(R.string.hao)*/"ID："+resp.id);
        }else {
            binding.tvNumber.setText(getString(R.string.app_name)+getString(R.string.hao)+resp.id);
        }
        binding.hivAvatar.tio_roundAvatar(resp.avatar);
        binding.tvAddress.setText(String.format(Locale.getDefault(), "%s %s", resp.country, resp.city));
        if (!TioConfig.OpenCloseConfig.isMapEnable()){
            binding.llAddress.setVisibility(View.GONE);
            binding.tvAddress.setVisibility(View.GONE);
            binding.ivAddress.setVisibility(View.GONE);
        }
        if (TioConfig.HIDE_ADDRESS){

        }
        // 签名
        binding.tvSign.setText(TextUtils.isEmpty(resp.sign) ? getString(R.string.tahaimeiyougexingsin):resp.sign);
        // 昵称
        binding.tvName.setText(StringUtil.nonNull(resp.nick));

        // 头像查看
        TioImageBrowser.getInstance().clickViewShowPic(binding.hivAvatar, resp.avatar);
    }
}
