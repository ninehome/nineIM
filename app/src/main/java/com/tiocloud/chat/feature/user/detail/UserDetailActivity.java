package com.tiocloud.chat.feature.user.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioUserInfoActivityBinding;
import com.tiocloud.chat.feature.user.detail.feature.friend.FriendDetailFragment;
import com.tiocloud.chat.feature.user.detail.feature.myself.MyselfDetailFragment;
import com.tiocloud.chat.feature.user.detail.feature.nonfriend.NonFriendDetailFragment;
import com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.NonFriendApplyFragment;
import com.tiocloud.chat.feature.user.detail.model.NonFriendApply;
import com.tiocloud.chat.feature.user.detail.mvp.UserContract;
import com.tiocloud.chat.feature.user.detail.mvp.UserPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.IsFriendReq;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 * <p>
 * 用户信息详情页
 * <p>
 * 好友
 * ｜- 自己
 * ｜- 普通好友
 * <p>
 * 非好友
 * ｜- 申请中的非好友
 * ｜- 普通非好友
 */
public class UserDetailActivity extends TioActivity implements UserContract.View {

    private TioUserInfoActivityBinding binding;
    private UserPresenter presenter;

    public static void start(Context context, String uid){
        start(context, uid, false, false);
    }

    public static void start(Context context, String uid, boolean onlyFriend, boolean forceAddorDel) {
        if (onlyFriend){
            IsFriendReq isFriendReq = new IsFriendReq(String.valueOf(uid));
            TioHttpClient.get(context, isFriendReq, new TaoCallback<BaseResp<Integer>>() {
                @Override
                public void onSuccess(Response<BaseResp<Integer>> response) {
                    BaseResp<Integer> body = response.body();
                    Integer data = body.getData();
                    if (data != null && data == 1) {
                        start(context, uid, null, forceAddorDel);
                    }
                }

                @Override
                public void onError(Response<BaseResp<Integer>> response) {
                    super.onError(response);

                }
            });
        }else {
            start(context, uid, null, forceAddorDel);
        }
    }

    @Override
    public boolean getNeedSetStatusBar() {
        return false;
    }

    public static void start(Context context, String uid, @Nullable NonFriendApply nonFriendApply, boolean forceAddorDel) {
        if (context == null || TextUtils.isEmpty(uid)) return;
        Intent starter = new Intent(context, UserDetailActivity.class);
        starter.putExtra(TioExtras.EXTRA_USER_ID, uid);
        starter.putExtra(TioExtras.EXTRA_MODEL_NON_FRIEND_APPLY, nonFriendApply);
        starter.putExtra("forceAddorDel", forceAddorDel);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    public boolean getForceAddorDel(){
        return getIntent().getBooleanExtra("forceAddorDel", false);
    }

    private String getUid() {
        return getIntent().getStringExtra(TioExtras.EXTRA_USER_ID);
    }

    private NonFriendApply geNonFriendApply() {
        return (NonFriendApply) getIntent().getSerializableExtra(TioExtras.EXTRA_MODEL_NON_FRIEND_APPLY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        // 设置window背景色
//        setBackgroundColor(Color.WHITE);
        // 绑定布局
        binding = DataBindingUtil.setContentView(this, R.layout.tio_user_info_activity);
        // 状态栏
//        setStatusBarColor(getResources().getColor(R.color.gray_f9f9f9));
        addMarginTopEqualStatusBarHeight(binding.frameLayout);

        presenter = new UserPresenter(this);
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onIsFriendResp(boolean isFriend) {
        TioFragment fragment;
        if (isFriend) {
            String currUid = String.valueOf(TioDBPreferences.getCurrUid());
            boolean myself = currUid != null && currUid.equals(getUid());
            if (myself) {
                // 自己
                fragment = MyselfDetailFragment.create(getUid());
            } else {
                // 普通好友
                fragment = FriendDetailFragment.create(getUid(), getForceAddorDel());
            }
        } else {
            NonFriendApply nonFriendApply = geNonFriendApply();
            if (nonFriendApply != null) {
                // 申请中的非好友
                fragment = NonFriendApplyFragment.create(getUid(), nonFriendApply);
            } else {
                // 普通非好友
                fragment = NonFriendDetailFragment.create(getUid(), getForceAddorDel());
            }
        }
        fragment.setContainerId(binding.frameLayout.getId());
        replaceFragment(fragment);
    }

    /**
     * 多次刷新页面
     */
    public void updateUI() {
        String uid = getUid();
        if (presenter != null && uid != null) {
            presenter.updateUI(uid);
        }
    }
}
