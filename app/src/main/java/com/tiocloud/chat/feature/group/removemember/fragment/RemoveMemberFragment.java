package com.tiocloud.chat.feature.group.removemember.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.removemember.fragment.adapter.RemoveMemberAdapter;
import com.tiocloud.chat.feature.group.removemember.fragment.mvp.FragmentRemoveMemberContract;
import com.tiocloud.chat.feature.group.removemember.fragment.mvp.FragmentRemoveMemberPresenter;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class RemoveMemberFragment extends TioFragment implements FragmentRemoveMemberContract.View {
    public FragmentRemoveMemberPresenter presenter = new FragmentRemoveMemberPresenter(this);
    private RecyclerView recyclerView;
    private RemoveMemberAdapter adapter;

    public static RemoveMemberFragment create(String groupId) {
        RemoveMemberFragment fragment = new RemoveMemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_GROUP_ID, groupId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public String getGroupId() {
        return getArguments().getString(TioExtras.EXTRA_GROUP_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_remove_menber_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerView);
        presenter.init();
    }

    @Override
    public void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new RemoveMemberAdapter();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (presenter != null)
                    presenter.loadMore();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public RemoveMemberAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    public void load(String keyWord) {
        if (presenter != null)
            presenter.searchKey(keyWord);
    }
}
