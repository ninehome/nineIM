package com.tiocloud.chat.feature.group.invitemember.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.invitemember.fragment.adapter.InviteMemberAdapter;
import com.tiocloud.chat.feature.group.invitemember.fragment.mvp.InviteMemberContract;
import com.tiocloud.chat.feature.group.invitemember.fragment.mvp.InviteMemberPresenter;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class InviteMemberFragment extends TioFragment implements InviteMemberContract.View {

    public InviteMemberPresenter presenter = new InviteMemberPresenter(this);
    private RecyclerView recyclerView;
    private InviteMemberAdapter adapter;

    public static InviteMemberFragment create(String groupId) {
        InviteMemberFragment fragment = new InviteMemberFragment();
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
        return inflater.inflate(R.layout.tio_invite_menber_fragment, container, false);
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
        adapter = new InviteMemberAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public InviteMemberAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
