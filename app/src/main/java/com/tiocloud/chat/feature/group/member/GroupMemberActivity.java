package com.tiocloud.chat.feature.group.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.forbidden.ForbiddenMvpPresenter;
import com.tiocloud.chat.feature.group.member.mvp.GroupMemberContract;
import com.tiocloud.chat.feature.group.member.mvp.GroupMemberPresenter;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.widget.TioRefreshView;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 群成员页面（设置管理员、群主、删除成员、添加成员）
 */
public class GroupMemberActivity extends TioActivity implements GroupMemberContract.View {

    private WtTitleBar titleBar;
    private RecyclerView rv_memberList;
    private TioEditText et_input;
    private TioRefreshView refresh_view;

    private GroupMemberPresenter presenter;
    private ForbiddenMvpPresenter forbiddenMvpPresenter;
    private ListAdapter listAdapter;

    private int myRole = 2;
    private boolean canFriend = true;

    public static void start(Context context, String groupId, int myRole, boolean canFriend, boolean isManager) {
        Intent starter = new Intent(context, GroupMemberActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        starter.putExtra("myRole", myRole);
        starter.putExtra("canFriend", canFriend);
        starter.putExtra("isManager", isManager);
        context.startActivity(starter);
    }

    public void refreshData(){
        presenter.refresh();
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    public TioActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_group_member_activity);
        myRole = getIntent().getIntExtra("myRole", 2);
        canFriend = getIntent().getBooleanExtra("canFriend", true);
        titleBar = findViewById(R.id.titleBar);
        if (false){
            titleBar.setTitle(getString(R.string.appoint_manager));
        }else {
            titleBar.setTitle(getString(R.string.group_member));
        }
        rv_memberList = findViewById(R.id.rv_memberList);
        et_input = findViewById(R.id.et_input);
        refresh_view = findViewById(R.id.refresh_view);

        presenter = new GroupMemberPresenter(this);
        forbiddenMvpPresenter = new ForbiddenMvpPresenter(this);
        presenter.init();

    }

    public void refresh(){
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        forbiddenMvpPresenter.detachView();
    }

    @Override
    public void resetUI() {
        // 刷新控件
        refresh_view.setEnabled(true);
        refresh_view.setOnRefreshListener(() -> presenter.refresh());

        // 列表
        rv_memberList.setLayoutManager(new LinearLayoutManager(rv_memberList.getContext()));
        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            int uid = listAdapter.getData().get(position).uid;
            if (myRole == 1 || myRole == 3 || canFriend){
                UserDetailActivity.start(view.getContext(), String.valueOf(uid), myRole == 1 || myRole == 3 ? false : !canFriend, myRole == 1 || myRole == 3);
            }
        });
        listAdapter.setOnLoadMoreListener(() -> presenter.loadMore(), rv_memberList);
        rv_memberList.setAdapter(listAdapter);

        // 输入栏
        et_input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s != null) {
                    String keyWord = s.toString();
                    presenter.search(keyWord);
                }
            }
        });
    }

    @Override
    public void onMemberCount(int memberCount) {
        if (false){

        }else {
            titleBar.setTitle(getString(R.string.group_member) + "(" + memberCount + ")");
        }
    }

    @Override
    public void onLoadListSuccess(GroupUserListResp users) {
        if (users.firstPage) {
            listAdapter.setNewData(users.list);
            refresh_view.setRefreshing(false);
        } else {
            listAdapter.addData(users.list);
        }
        if (users.lastPage) {
            listAdapter.loadMoreEnd();
        } else {
            listAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadListError(String msg, int pageNumber) {
        if (pageNumber == 1) {
            refresh_view.setRefreshing(false);
        } else {
            listAdapter.loadMoreFail();
        }
    }

    @Override
    public void setListLongClickEnable(boolean enable) {
        listAdapter.setOnItemLongClickListener(enable ? (adapter, view, position) -> {
            GroupUserListResp.GroupMember user = listAdapter.getData().get(position);

            // 不能对群主操作
            if (user.isGroupOwner()) {
                return false;
            }

            // 显示群主操作 popup
            forbiddenMvpPresenter.mgrLongClickGroupMemberListItem(view, myRole, user, getGroupId());
            return true;

        } : null);
    }
}
