package com.tiocloud.chat.feature.search.curr.group;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class GroupResultFragment extends BaseResultFragment<List<MailListResp.Group>> {

    // ====================================================================================
    // data
    // ====================================================================================

    @Override
    protected void loadData(String keyWord) {
        getModel().requestMailList("2", keyWord, new BaseModel.DataProxy<MailListResp>() {
            @Override
            public void onSuccess(MailListResp lists) {
                if (lists.group != null && lists.group.size() != 0) {
                    notifySuccess(lists.group);
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
        return R.layout.tio_search_group_result_fragment;
    }

    @Override
    protected void initSuccessLayout(List<MailListResp.Group> lists, String keyWord) {
        RecyclerView list = findViewById(R.id.rv_groupList);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
        final GroupListAdapter listAdapter = new GroupListAdapter(getContext(), lists, keyWord);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MailListResp.Group group = listAdapter.getData().get(position);
                GroupSessionActivity.active(getActivity(), group.groupid);
            }
        });
        list.setAdapter(listAdapter);

        // 添加头部
        View view = new View(getContext());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(1));
//        view.setLayoutParams(params);
        listAdapter.addHeaderView(view);
    }
}
