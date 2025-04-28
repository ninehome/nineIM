package com.tiocloud.chat.feature.share.friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.ActivityShareFriendBinding;
import com.tiocloud.chat.feature.share.friend.feature.recent.RecentFragment;
import com.tiocloud.chat.feature.share.friend.feature.result.ResultFragment;
import com.tiocloud.chat.feature.share.friend.model.ShareCardEntity;
import com.tiocloud.chat.feature.share.friend.model.ShareCardFrom;
import com.tiocloud.chat.feature.share.friend.model.ShareCardTo;
import com.tiocloud.chat.feature.share.friend.mvp.ShareFriendContract;
import com.tiocloud.chat.feature.share.friend.mvp.ShareFriendPresenter;
import com.tiocloud.chat.widget.dialog.base.CardDialog;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   : 把 "好友" 分享给 "好友/群聊"
 * </pre>
 */
public class ShareFriendActivity extends TioActivity implements ShareFriendContract.View {

    private ShareFriendPresenter presenter;
    private ActivityShareFriendBinding binding;
    private RecentFragment recentFragment;
    private ResultFragment resultFragment;

    /**
     * @param friendUid 好友的uid
     */
    public static void start(Context context, String friendUid) {
        Intent starter = new Intent(context, ShareFriendActivity.class);
        starter.putExtra(TioExtras.FRIEND_UID, friendUid);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShareFriendPresenter(this);
        presenter.initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void bindContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_friend);
    }

    @Override
    public void initEditText() {
        binding.etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                presenter.onEtTextChanged(s);
            }
        });
    }

    @Override
    public void initFragmentContainers() {
        recentFragment = new RecentFragment();
        recentFragment.setContainerId(binding.frameLayout.getId());
        addFragment(recentFragment);

        resultFragment = new ResultFragment();
        resultFragment.setContainerId(binding.frameLayout.getId());
        addFragment(resultFragment);
    }

    @Override
    public void showRecentPage() {
        showFragment(recentFragment);
        hideFragment(resultFragment);
    }

    @Override
    public void showSearchResultPage(String s) {
        resultFragment.setSearchKey(s);
        showFragment(resultFragment);
        hideFragment(recentFragment);
    }

    @Override
    public void shareCard(ShareCardTo entity) {
        if (presenter != null) {
            presenter.shareCard(entity);
        }
    }

    @Override
    public String getFromFriendUid() {
        return getIntent().getStringExtra(TioExtras.FRIEND_UID);
    }

    @Override
    public void showCardDialog(ShareCardEntity entity) {
        ShareCardFrom from = entity.from;
        ShareCardTo to = entity.to;

        CardDialog cardDialog = new CardDialog(getActivity());
        cardDialog.tv_title.setText("发送给");
        cardDialog.hiv_avatar.tio_roundAvatar(to.toAvatar);
        cardDialog.tv_usrName.setText(StringUtils.null2Length0(to.toName));
        cardDialog.tv_positiveBtn.setText("发送名片");
        cardDialog.tv_positiveBtn.setOnClickListener(view -> {
            presenter.reqShareCard("1", to.toUid, to.toGroupId, from.fromCardId);
        });
        cardDialog.show();
    }
}
