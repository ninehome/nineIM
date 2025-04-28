package com.tiocloud.chat.feature.user.detail.feature.friend;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioFriendInfoFragmentBinding;
import com.tiocloud.chat.feature.group.transfergroup.TransferGroupActivity;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.user.detail.feature.friend.mvp.FriendDetailContract;
import com.tiocloud.chat.feature.user.detail.feature.friend.mvp.FriendDetailPresenter;
import com.tiocloud.chat.widget.dialog.base.FriendOperDialog;
import com.tiocloud.chat.widget.dialog.base.GroupOperDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.TioMap;
import com.watayouxiang.httpclient.model.request.IsBlackReq;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.TioImageBrowser;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 * 好友 - 信息详情页
 */
public class FriendDetailFragment extends TioFragment implements FriendDetailContract.View {

    private FriendDetailPresenter presenter;
    private TioFriendInfoFragmentBinding binding;

    public static TioFragment create(@NonNull String uid, boolean forceAddorDel) {
        FriendDetailFragment fragment = new FriendDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_USER_ID, uid);
        bundle.putBoolean("forceAddorDel", forceAddorDel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    public String getUid() {
        return getArguments().getString(TioExtras.EXTRA_USER_ID);
    }

    @Override
    public boolean getForceDel() {
        return getArguments().getBoolean("forceAddorDel", false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tio_friend_info_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new FriendDetailPresenter(this);
        presenter.init();
        ImageView ivRight = binding.titleBar.getIvRight();
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsBlackReq isBlackReq = new IsBlackReq(getUid());
                isBlackReq.setCancelTag(this);
                isBlackReq.get(new TioCallback<Integer>() {
                    @Override
                    public void onTioSuccess(Integer integer) {
                        boolean inBlack = integer != null && integer == 1;
                        FriendOperDialog operDialog = new FriendOperDialog(getActivity(), inBlack) {
                            @Override
                            protected void onClick(FriendOperDialog dialog, View v) {
                                switch (v.getId()){
                                    case R.id.tv_black:
                                        toBlack(getUid(), !inBlack);
                                        break;
                                    case R.id.tv_delete:
                                        toDelete(v);
                                        break;
                                }
                                dialog.cancel();
                            }
                        };
                        operDialog.show();
                    }

                    @Override
                    public void onTioError(String msg) {

                    }
                });


            }
        });
    }

    private void toDelete(View view) {
        presenter.doDelFriend(view);
    }

    private void toBlack(String toUid, boolean black){
        OperReq operReq = OperReq.blackList(toUid, black);
        operReq.setCancelTag(this);
        operReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                ToastUtils.showShort(black?getString(R.string.add_black):getString(R.string.rv_black));
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
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
        binding.tvName.setText("");
        binding.tvAddress.setText("");
        binding.tvRemarkName.setText("");
        binding.tvSign.setText("");

        binding.tvP2pTalk.setOnClickListener(view1 -> P2PSessionActivity.active(getActivity(), getUid()));
        binding.ivEditRemarkName.setOnClickListener(view -> presenter.doModifyRemarkName(view.getContext(), getUid()));
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
        // 备注名、昵称
        if (!TextUtils.isEmpty(resp.remarkname)) {
            binding.tvRemarkName.setText(resp.remarkname);
            binding.tvName.setVisibility(View.VISIBLE);
            binding.tvName.setText(resp.nick);
        } else {
            binding.tvRemarkName.setText(resp.nick);
            binding.tvName.setVisibility(View.INVISIBLE);
        }

        // 头像查看
        TioImageBrowser.getInstance().clickViewShowPic(binding.hivAvatar, resp.avatar);
    }
}
