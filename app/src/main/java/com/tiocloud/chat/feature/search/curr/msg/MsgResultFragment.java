package com.tiocloud.chat.feature.search.curr.msg;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class MsgResultFragment extends BaseResultFragment<List<Map<String, Object>>> {

    // ====================================================================================
    // data
    // ====================================================================================
    private List<Map<String, Object>> queryMsg(String keyWord) {
        return GroupMsgTableCrud.queryChatMsgGroup(keyWord);
    }
    @Override
    protected void loadData(String keyWord) {
//        getModel().requestMailList("3", keyWord, new BaseModel.DataProxy<MailListResp>() {
//            @Override
//            public void onSuccess(MailListResp lists) {
////                if (lists.fd != null && lists.fd.size() != 0) {
////                    notifySuccess(lists);
////                } else {
////                    notifyEmpty();
////                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                notifyError();
//            }
//        });

        List<Map<String, Object>> mapList = queryMsg(keyWord);
        if (mapList == null || mapList.size() == 0){
            notifyEmpty();

        }else {
            notifySuccess(mapList);
        }

    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected int successLayoutId() {
        return R.layout.tio_search_friend_result_fragment;
    }

    @Override
    protected void initSuccessLayout(List<Map<String, Object>> lists, String keyWord) {
        RecyclerView friendList = findViewById(R.id.rv_friendList);
        friendList.setLayoutManager(new LinearLayoutManager(friendList.getContext()));
        final MsgListAdapter friendAdapter = new MsgListAdapter(getContext(), lists, keyWord);
        friendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                MailListResp.Friend friend = friendAdapter.getData().get(position);
//                UserDetailActivity.start(getActivity(), String.valueOf(friend.uid));
                Map<String, Object> map = lists.get(position);
                MsgSearchResultActivity.start(getActivity(), keyWord, (int)(map.get("chatMode")), (String)(map.get("chatLinkId")), StringUtil.nonNull(map.get("name")), StringUtil.nonNull(map.get("avatar")));
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
