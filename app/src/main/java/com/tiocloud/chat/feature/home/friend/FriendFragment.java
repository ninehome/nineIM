package com.tiocloud.chat.feature.home.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.tiocloud.chat.databinding.TioFriendFragmentBinding;
import com.tiocloud.chat.feature.home.friend.mvp.FriendContract;
import com.tiocloud.chat.feature.home.friend.mvp.FriendPresenter;
import com.tiocloud.chat.feature.main.fragment.MainFriendFragment;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020-01-13
 * desc : 好友
 */
public class FriendFragment extends TioFragment implements FriendContract.View {

    private FriendPresenter presenter;
    private TioFriendFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TioFriendFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new FriendPresenter(this, binding);
        presenter.initRefreshView();
        presenter.initListView();
        presenter.load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Nullable
    @Override
    public MainFriendFragment getMainFriendFragment() {
        FragmentActivity activity = getActivity();
        if (activity instanceof TioActivity) {
            TioActivity tioActivity = (TioActivity) activity;
            return (MainFriendFragment) tioActivity.findFragmentByClass(MainFriendFragment.class);
        }
        return null;
    }

    public void onRefresh() {
//        if (presenter != null) {
//            presenter.load();
//        }
    }
}
