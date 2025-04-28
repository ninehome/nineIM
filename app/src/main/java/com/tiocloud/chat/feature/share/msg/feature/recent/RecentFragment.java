package com.tiocloud.chat.feature.share.msg.feature.recent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.databinding.FragmentShareMsgRecentBinding;
import com.tiocloud.chat.feature.group.create.CreateGroupActivity;
import com.tiocloud.chat.feature.share.msg.ShareMsgActivity;
import com.tiocloud.chat.feature.share.msg.model.MsgForwardTo;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ChatListReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   :
 * </pre>
 */
public class RecentFragment extends TioFragment {

    private FragmentShareMsgRecentBinding binding;
    private RecentAdapter recentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShareMsgRecentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        load();
    }

    private void load() {
        ChatListReq chatListReq = new ChatListReq();
        chatListReq.setCancelTag(this);
        chatListReq.get(new TioCallback<ChatListResp>() {
            @Override
            public void onTioSuccess(ChatListResp lists) {
                recentAdapter.setNewData(lists);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }


    private void initRecyclerView() {

        recentAdapter = new RecentAdapter(binding.recyclerView);
        recentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShareMsgActivity shareSessionActivity = getShareSessionActivity();
                if (shareSessionActivity != null) {
                    MsgForwardTo shareCardTo = getShareCardTo(recentAdapter.getData().get(position));
                    shareSessionActivity.forwardMsg(shareCardTo);
                }
            }

            private MsgForwardTo getShareCardTo(ChatListResp.List list) {
                String toUid = null, toGroupId = null;
                if (list.chatmode == 1) {
                    // 私聊
                    toUid = list.bizid;
                } else {
                    // 群聊
                    toGroupId = list.bizid;
                }
                return new MsgForwardTo(list.avatar, list.name, toUid, toGroupId,list.bizid,list.id);
            }
        });
    }

    private @Nullable
    ShareMsgActivity getShareSessionActivity() {
        if (getActivity() instanceof ShareMsgActivity) {
            return (ShareMsgActivity) getActivity();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
