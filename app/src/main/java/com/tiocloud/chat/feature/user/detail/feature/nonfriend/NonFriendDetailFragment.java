package com.tiocloud.chat.feature.user.detail.feature.nonfriend;

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
import com.tiocloud.chat.databinding.TioAddFriendFragmentBinding;
import com.tiocloud.chat.feature.user.detail.feature.nonfriend.mvp.NonFriendContract;
import com.tiocloud.chat.feature.user.detail.feature.nonfriend.mvp.NonFriendPresenter;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TioImageBrowser;
import com.tiocloud.chat.util.StringUtil;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc : 非好友 - 信息详情页
 */
public class NonFriendDetailFragment extends TioFragment implements NonFriendContract.View {

    private TioAddFriendFragmentBinding binding;

    public static TioFragment create(String uid, boolean forceAddorDel) {
        NonFriendDetailFragment fragment = new NonFriendDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_USER_ID, uid);
        bundle.putBoolean("forceAddorDel", forceAddorDel);
        fragment.setArguments(bundle);
        return fragment;
    }

    private NonFriendPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TioAddFriendFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new NonFriendPresenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    public boolean getForceAddorDel(){
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getBoolean("forceAddorDel", false);
        }
        return false;
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
    public void resetViews() {
        binding.tvName.setText("");
        binding.tvAddress.setText("");
    }

    @Override
    public void initViews() {
        binding.tvAddFriend.setOnClickListener(v -> presenter.doAddFriend(v));
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
        // 签名
        binding.tvSign.setText(TextUtils.isEmpty(resp.sign) ? getString(R.string.tahaimeiyougexingsin):resp.sign);
        if (!TioConfig.OpenCloseConfig.isMapEnable()){
            binding.llAddress.setVisibility(View.GONE);
            binding.tvAddress.setVisibility(View.GONE);
            binding.ivAddress.setVisibility(View.GONE);
        }

        // 昵称
        binding.tvName.setText(StringUtil.nonNull(resp.nick));

        // 头像查看
        TioImageBrowser.getInstance().clickViewShowPic(binding.hivAvatar, resp.avatar);
    }
}
