package com.tiocloud.chat.feature.search.user.fragment.mvp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.user.fragment.adapter.SearchUserListAdapter;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.mvp.addfriend.AddFriendContract;
import com.tiocloud.chat.mvp.addfriend.AddFriendPresenter;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.httpclient.model.response.UserSearchResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SearchUserFragmentPresenter extends SearchUserFragmentContract.Presenter {

    private SearchUserListAdapter adapter;
    private final AddFriendPresenter addFriendPresenter;
    private View emptyView;
    private View recyclerView;

    public SearchUserFragmentPresenter(SearchUserFragmentContract.View view) {
        super(new SearchUserFragmentModel(), view);
        addFriendPresenter = new AddFriendPresenter();
    }

    @Override
    public void detachView() {
        super.detachView();
        addFriendPresenter.detachView();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void initListView(RecyclerView recyclerView, View emptyView) {
        this.emptyView = emptyView;
        this.recyclerView = recyclerView;
        adapter = new SearchUserListAdapter(recyclerView) {
            @Override
            protected void onClickAddBtn(@NonNull UserSearchResp.ListBean bean, View view) {
                super.onClickAddBtn(bean, view);
                startAddFriend(bean.id, view);
            }

            @Override
            protected void onClickItem(UserSearchResp.ListBean bean) {
                super.onClickItem(bean);
                UserDetailActivity.start(getView().getActivity(), String.valueOf(bean.id));
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNumber++;
                load();
            }
        }, recyclerView);
    }

    private void startAddFriend(int id, final View view) {
        view.setEnabled(false);
        addFriendPresenter.checkStart(id, false, new AddFriendContract.Presenter.AddFriendProxy() {
            @Override
            public void onAddFriendResp(AddFriendResp data) {
                TioToast.show(getView().getActivity(), "好友添加成功");
            }

            @Override
            public void onFriendApplyResp(FriendApplyResp data) {
                super.onFriendApplyResp(data);
                TioToast.show(getView().getActivity(), getView().getActivity().getString(R.string.friend_apply_success));
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(getView().getActivity(), msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                view.setEnabled(true);
            }
        }, getView().getActivity());
    }

    // ====================================================================================
    // data
    // ====================================================================================

    private String keyWord;
    private int pageNumber;

    @Override
    public void search(String keyWord) {
        this.keyWord = keyWord;
        this.pageNumber = 1;
        load();
    }

    private void load() {
        getModel().searchUser(keyWord, pageNumber, new BaseModel.DataProxy<UserSearchResp>() {
            @Override
            public void onSuccess(UserSearchResp resp) {
                List<UserSearchResp.ListBean> list = resp.list;
                if (resp.firstPage) {
                    if (list == null || list.size() == 0){
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        return;
                    }
                    adapter.setNewData(list, keyWord);
                } else {
                    adapter.addData(list);
                }
                if (resp.lastPage) {
                    adapter.loadMoreEnd(true);
                } else {
                    adapter.loadMoreComplete();
                }
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                if (pageNumber > 1) {
                    adapter.loadMoreFail();
                }
                TioToast.show(getView().getActivity(), msg);
            }
        });
    }

    public void showEmpty(){
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideEmpty(){
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
