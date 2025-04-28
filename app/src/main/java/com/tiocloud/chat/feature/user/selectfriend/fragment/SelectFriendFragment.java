package com.tiocloud.chat.feature.user.selectfriend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.selectfriend.fragment.mvp.FragmentSelectFriendContract;
import com.tiocloud.chat.feature.user.selectfriend.fragment.mvp.FragmentSelectFriendPresenter;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectFriendFragment extends TioFragment implements FragmentSelectFriendContract.View {
    private FragmentSelectFriendPresenter presenter;

    public static SelectFriendFragment create() {
        return new SelectFriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_select_friend_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ContactsCatalogView contactsCatalogView = findViewById(R.id.ccv_catalogList);
        presenter = new FragmentSelectFriendPresenter(this);
        presenter.initRecyclerView(recyclerView, contactsCatalogView);
        presenter.search(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    public void search(String keyWord) {
        if (presenter != null) {
            presenter.search(keyWord);
        }
    }

    @Override
    public void finishPage() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
