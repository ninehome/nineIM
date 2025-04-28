package com.tiocloud.chat.feature.user.applylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.event.UpdateFriendApplyNumEvent;
import com.tiocloud.chat.feature.search.user.SearchUserActivity;
import com.tiocloud.chat.feature.user.applylist.adapter.ApplyListAdapter;
import com.tiocloud.chat.feature.user.applylist.mvp.ApplyListContract;
import com.tiocloud.chat.feature.user.applylist.mvp.ApplyListPresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.imclient.TioIMClient;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc : 好友申请列表
 */
public class ApplyListActivity extends TioActivity implements ApplyListContract.View {

    private ApplyListPresenter presenter;
    private ApplyListAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ApplyListActivity.class);
        context.startActivity(starter);
        TioIMClient.getInstance().getEventEngine().post(new UpdateFriendApplyNumEvent());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_apply_list_activity);
        presenter = new ApplyListPresenter(this);
        presenter.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.requestApplyList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void initTitleBar() {
        WtTitleBar titleBar = findViewById(R.id.titleBar);
        TextView okBtn = titleBar.getTvRight();
        okBtn.setOnClickListener(view -> SearchUserActivity.start(getActivity()));
    }

    @Override
    public void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.divider_bg));
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ApplyListAdapter() {
            @Override
            protected void onClickAgreeBtn(@NonNull ApplyListResp.Data item, int position, View view) {
                super.onClickAgreeBtn(item, position, view);
                presenter.doAgreeAddFriend(String.valueOf(item.id), item.nick, position, view);
            }

            @Override
            protected void onClickItem(@NonNull ApplyListResp.Data item, int position) {
                super.onClickItem(item, position);
                presenter.openUserDetailActivity(item);
            }
        };
        recyclerView.setAdapter(adapter);
        // 添加头部
//        View view = new View(recyclerView.getContext());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(12));
//        view.setLayoutParams(params);
//        adapter.addHeaderView(view);
    }

    @Override
    public void onApplyListResp(ApplyListResp data) {
        if (adapter != null) {
            adapter.setNewData(data);
        }
    }

    @Override
    public void onAgreeAddFriend(DealApplyResp data, int position) {
        if (adapter != null) {
            adapter.flagAgree(position);
        }
    }
}
