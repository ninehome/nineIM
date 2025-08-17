package com.tiocloud.chat.feature.search.curr.friend;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class FriendResultFragment extends BaseResultFragment<MailListResp> {

    // ====================================================================================
    // data
    // ====================================================================================

    @Override
    protected void loadData(String keyWord) {
        getModel().requestMailList("1", keyWord, new BaseModel.DataProxy<MailListResp>() {
            @Override
            public void onSuccess(MailListResp lists) {
                if (lists.fd != null && lists.fd.size() != 0) {
                    notifySuccess(lists);
                } else {
                    notifyEmpty();
                }
            }

            @Override
            public void onFailure(String msg) {
                notifyError();
            }
        });
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected int successLayoutId() {
        return R.layout.tio_search_friend_result_fragment;
    }

    @Override
    protected void initSuccessLayout(MailListResp lists, String keyWord) {
        RecyclerView friendList = findViewById(R.id.rv_friendList);
        friendList.setLayoutManager(new LinearLayoutManager(friendList.getContext()));
        final FriendListAdapter friendAdapter = new FriendListAdapter(getContext(), lists.fd, keyWord);
        friendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MailListResp.Friend friend = friendAdapter.getData().get(position);
                UserDetailActivity.start(getActivity(), String.valueOf(friend.uid));
            }
        });
        friendList.setAdapter(friendAdapter);
        // 添加头部
        View view = new View(getContext());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(12));
//        view.setLayoutParams(params);
        friendAdapter.addHeaderView(view);
    }
    public void search(String keyWord){
        BaseResultFragment fragment = this;
        fragment.updateKeyWord(keyWord);
        // 加载当前所处页面
        fragment.load();
    }
}
