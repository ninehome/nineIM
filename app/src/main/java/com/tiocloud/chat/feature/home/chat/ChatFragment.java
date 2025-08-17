package com.tiocloud.chat.feature.home.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiocloud.chat.BuildConfig;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.databinding.TioChatFragmentBinding;
import com.tiocloud.chat.feature.home.chat.adapter.BaseUIAdapter;
import com.tiocloud.chat.feature.home.chat.adapter.ChatAdapter;
import com.tiocloud.chat.feature.home.chat.mvp.ChatContract;
import com.tiocloud.chat.feature.home.chat.mvp.ChatPresenter;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.main.model.MainTab;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.widget.popupwindow.HomeOpWindow;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.request.QueryOnlineReq;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.imclient.event.ClearChatMsg;
import com.watayouxiang.imclient.event.TioStateEvent;
import com.watayouxiang.imclient.utils.IMConst;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-01-13
 * desc : 聊天
 */
public class ChatFragment extends TioFragment implements ChatContract.View {

    private ChatPresenter presenter;
    private TioChatFragmentBinding binding;
    @Nullable
    private ChatAdapter listAdapter;

    @Nullable
    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TioChatFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ChatPresenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void initUI() {
        // 初始化刷新控件
        initRefreshView();
        // 初始化列表
        MainActivity mainActivity = getMainActivity();
        if (mainActivity != null) {
            initChatListView(mainActivity);
        }
        if (TioConfig.OpenCloseConfig.showOnlineStatus()){
            toQueryOnline();
        }
    }

    public static String queryUids = null;

    public void toQueryOnline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean[] hasGet = {false};
                while (!hasGet[0]){
                    if (getActivity() == null || getActivity().isFinishing()){
                        return;
                    }
                    if (queryUids == null){
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    LogUtils.d("zlb, 获取用户在线状态======>"+queryUids);
                    QueryOnlineReq queryOnlineReq = new QueryOnlineReq(queryUids.toString());
                    queryOnlineReq.setCancelTag(ChatFragment.this);
                    queryOnlineReq.get(new TioCallback<Object>() {
                        @Override
                        public void onTioSuccess(Object s) {
                            try {
                                hasGet[0] = true;
                                LogUtils.d("zlb, 在线状态======>"+s.toString());
                                JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(s));
                                listAdapter.setOnlineArray(jsonArray);
                                listAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onTioError(String msg) {
                            LogUtils.d("zlb, 在线状态异常======>"+msg);
                        }
                    });

                    try {
                        Thread.sleep(TioConfig.OpenCloseConfig.getOnlineRefreshTime() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTioStateEvent(TioStateEvent event){
        switch (event.state){
            case CONNECT:
                toQueryOnline();
                break;
        }
    }

    /**
     * 初始化 - 会话列表
     */
    private void initChatListView(@NonNull MainActivity activity) {
        /* 未读消息数变化 */
        listAdapter = new ChatAdapter(binding.recyclerView) {
            @Override
            public void onTotalUnreadChanged(int totalUnread) {
                super.onTotalUnreadChanged(totalUnread);
                activity.updateRedDot(MainTab.CHAT.tabIndex, totalUnread);
            }
        };
        /* 单击逻辑 */
        listAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (IMConst.isSyncing){
                return;
            }
            ChatListResp.List item = listAdapter.getData().get(position);
            switch (item.chatmode) {
                case 1:// 私聊
                    // 进入私聊页
                    P2PSessionActivity.enter(activity, item.id);
                    break;
                case 2:// 群聊
                    // 进入群聊页
                    GroupSessionActivity.enter(activity, item.id);
                    break;
            }
        });
        /* 长按逻辑 */
        listAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            ChatListResp.List list = listAdapter.getData().get(position);
            new HomeOpWindow(view).show(list);
            return true;
        });
    }

    /**
     * 初始化 - 刷新控件
     */
    private void initRefreshView() {
        binding.refreshView.setEnabled(/*BuildConfig.DEBUG*/true);
        binding.refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getChatList(true);
            }
        });
    }

    @Override
    public void onChatListRespSuccess(@Nullable ChatListResp lists) {
        if (listAdapter != null) {
            listAdapter.setNewData(lists);
        }
        for (ChatListResp.List list : lists){
            if (list.chatmode == 2){
                String groupId = String.valueOf(-Integer.parseInt(list.id));
                GroupInfoReq groupInfoReq = new GroupInfoReq("1", groupId);
                groupInfoReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
                TioHttpClient.get(this, groupInfoReq, new TaoCallback<BaseResp<GroupInfoResp>>() {
                    @Override
                    public void onSuccess(Response<BaseResp<GroupInfoResp>> response) {
                        GroupInfoResp data = response.body().getData();
                        //保存群主
                        try {
                            CacheTableCrud.setGroupRole(groupId, String.valueOf(data.group.uid), 1);
                            //保存我的角色
                            if (data.groupuser != null){
                                CacheTableCrud.setGroupRole(groupId, String.valueOf(TioDBPreferences.getCurrUid()), data.groupuser.grouprole);
                            }
                            //保存管理员
                            if (data.manges != null){
                                for (Integer integer : data.manges){
                                    CacheTableCrud.setGroupRole(groupId, String.valueOf(integer), 3);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onEndRefresh() {
        if (binding != null) {
            binding.refreshView.finishRefresh(false);
        }
    }

    @Override
    public void onChatListTableEvent(@NonNull ChatListTableEvent event) {
        if (!event.isOk()) {
            TioToast.showLong("聊天列表同步失败");
            return;
        }
        if (listAdapter == null) return;

        if (event.isAll()) {
            listAdapter.setNewData(event.getChatList());
            binding.recyclerView.scrollToPosition(0);
        } else {
            List<ChatListResp.List> delList = event.getDelList();
            listAdapter.removeItem(delList);
            List<ChatListResp.List> chatList = event.getChatList();
            listAdapter.updateItem(chatList);
        }
    }

    @Override
    public void onOffline(String uid, boolean onoff, String lastOnlineTime) {
        BaseUIAdapter.updateOnlineArray(uid, onoff, lastOnlineTime);
        listAdapter.notifyDataSetChanged();
    }

}
