package com.tiocloud.chat.feature.group.transfergroup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.transfergroup.fragment.adapter.TransferGroupAdapter;
import com.tiocloud.chat.feature.group.transfergroup.fragment.mvp.TransferGroupContract;
import com.tiocloud.chat.feature.group.transfergroup.fragment.mvp.TransferGroupPresenter;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class TransferGroupFragment extends TioFragment implements TransferGroupContract.View {

    public TransferGroupPresenter presenter = new TransferGroupPresenter(this);
    private RecyclerView recyclerView;
    private TransferGroupAdapter adapter;

    public static TransferGroupFragment create(String groupId) {
        TransferGroupFragment fragment = new TransferGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_GROUP_ID, groupId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public String getGroupId() {
        return getArguments() != null ? getArguments().getString(TioExtras.EXTRA_GROUP_ID) : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_transfer_group_fragment, container, false);
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
        adapter = new TransferGroupAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                GroupUserListResp.GroupMember user = adapter.getData().get(position);
                presenter.showTransferGroupConfirmDialog(user.uid, user.nick);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadMoreList();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public TransferGroupAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
