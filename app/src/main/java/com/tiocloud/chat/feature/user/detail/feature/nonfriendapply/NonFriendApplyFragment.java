package com.tiocloud.chat.feature.user.detail.feature.nonfriendapply;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.FragmentNonFriendApplyBinding;
import com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.mvp.NonFriendApplyContract;
import com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.mvp.NonFriendApplyPresenter;
import com.tiocloud.chat.feature.user.detail.model.NonFriendApply;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TioImageBrowser;
import com.tiocloud.chat.util.StringUtil;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc : 申请中的非好友 - 信息详情页
 */
public class NonFriendApplyFragment extends TioFragment implements NonFriendApplyContract.View {

    private FragmentNonFriendApplyBinding binding;

    public static TioFragment create(String uid, @NonNull NonFriendApply nonFriendApply) {
        NonFriendApplyFragment fragment = new NonFriendApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_USER_ID, uid);
        bundle.putSerializable(TioExtras.EXTRA_MODEL_NON_FRIEND_APPLY, nonFriendApply);
        fragment.setArguments(bundle);
        return fragment;
    }

    private NonFriendApplyPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNonFriendApplyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new NonFriendApplyPresenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public String getUid() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString(TioExtras.EXTRA_USER_ID, null);
        }
        return null;
    }

    @Override
    public @NonNull
    NonFriendApply getNonFriendApply() {
        return (NonFriendApply) getArguments().getSerializable(TioExtras.EXTRA_MODEL_NON_FRIEND_APPLY);
    }

    @Override
    public void resetViews() {
        binding.tvName.setText("");
        binding.tvAddress.setText("");
    }

    @Override
    public void initViews() {
        binding.tvAppendMsg.setText(StringUtil.nonNull(getNonFriendApply().appendMsg));

        binding.tvAgreeAddFriend.setOnClickListener(v -> {
            NonFriendApply apply = getNonFriendApply();
            presenter.doAgreeAddFriend(apply.applyId, apply.remarkName, v);
        });
    }

    @Override
    public void onUserInfoResp(UserInfoResp resp) {
        if (TioMap.currentLau.equals("vi")){
            binding.tvNumber.setText(/*getString(R.string.app_name)+getString(R.string.hao)*/"ID："+resp.id);
        }else {
            binding.tvNumber.setText(getString(R.string.app_name)+getString(R.string.hao)+resp.id);
        }
        binding.hivAvatar.tio_roundAvatar(resp.avatar);
        binding.tvName.setText(StringUtil.nonNull(resp.nick));
        binding.tvAddress.setText(String.format(Locale.getDefault(), "%s %s", resp.country, resp.city));
        if (TioConfig.HIDE_ADDRESS){

        }
        if (!TioConfig.OpenCloseConfig.isMapEnable()){
            binding.llAddress.setVisibility(View.GONE);
            binding.tvAddress.setVisibility(View.GONE);
            binding.ivAddress.setVisibility(View.GONE);
        }
        // 签名
        binding.tvSign.setText(TextUtils.isEmpty(resp.sign) ? getString(R.string.tahaimeiyougexingsin):resp.sign);
        // 昵称
        binding.tvName.setText(StringUtil.nonNull(resp.nick));

        // 头像查看
        TioImageBrowser.getInstance().clickViewShowPic(binding.hivAvatar, resp.avatar);
    }
}
