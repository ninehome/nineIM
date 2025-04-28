package com.tiocloud.chat.feature.session.common.panel;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ThreadUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.model.SessionContainer;
import com.tiocloud.chat.feature.session.common.proxy.MsgListProxy;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.yanxun.map.PermissionUtil;
import com.watayouxiang.androidutils.recyclerview.BaseFetchLoadAdapter;
import com.watayouxiang.db.dao.GroupMsgTableCrud;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-27
 * desc :
 */
public class MsgListPanel implements MsgListProxy {
    // container
    private final SessionContainer container;

    // message list
    private RecyclerView recyclerView;
    private List<TioMsg> items;
    private MsgAdapter adapter;
    private NewMsgTipHelper newMsgTipHelper;
    private String chatMode;


    public MsgListPanel(SessionContainer container,String chatMode) {
        this.container = container;
        this.chatMode = chatMode;
        initListView();
    }

    private void initListView() {
        // list
        recyclerView = container.rootView.findViewById(R.id.messageListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.requestDisallowInterceptTouchEvent(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    container.proxy.collapseInputPanel();
                }
            }
        });
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // adapter
        items = new ArrayList<>();
        adapter = new MsgAdapter(recyclerView, chatMode,items, container.chatLinkIds.get(0)) {
            @Override
            public boolean onAvatarLongClick(View v, TioMsg msg) {
                return container.proxy.onAvatarLongClick(v, msg);
            }
        };

        recyclerView.setAdapter(adapter);

        // 新消息提示
        TextView tvNewMsgTip = container.rootView.findViewById(R.id.tv_newMsgTip);
        newMsgTipHelper = new NewMsgTipHelper(recyclerView, this, tvNewMsgTip);
    }

    public void release() {
        if (adapter != null) {
            adapter.release();
            adapter = null;
        }
    }

    // ====================================================================================
    // MsgListProxy
    // ====================================================================================

    @Override
    public void sendMsg(TioMsg message) {
        newMsgTipHelper.onBeforeDataChanged();
        adapter.appendData(message);
        newMsgTipHelper.onAfterDataChanged(message);
    }

    @Override
    public void deleteMsg(long mid) {
        adapter.deleteMsg(mid);
    }

    @Override
    public void deleteUserMsg(long uid) {
        List<TioMsg> data = adapter.getData();
        List<String> msgIdList = new ArrayList<>();
        for (TioMsg tioMsg : data){
            Log.e("zlb", "uid===>"+tioMsg.getUid());
            if (tioMsg.getUid() != null && Long.parseLong(tioMsg.getUid()) == uid){
//                adapter.deleteMsg(Long.parseLong(tioMsg.getId()));
                msgIdList.add(tioMsg.getId());
            }
        }
        for (String msgId : msgIdList){
            adapter.deleteMsg(Long.parseLong(msgId));
        }
    }

    @Override
    public void clearList() {
        adapter.clearData();
    }
    @Override
    public void setNewData(List<TioMsg> msgList){
        List<TioMsg> data = adapter.getData();
        if (data.size() == 0){
            adapter.setNewData(msgList);
            adapter.fetchMoreComplete(msgList.size());
            return;
        }
        if (msgList.size() == 0){
            adapter.clearData();
            return;
        }
//        if (Integer.parseInt(newData.get(0).getId()) < Integer.parseInt(data.get(0).getId())){
//            adapter.setNewData(newData);
//            adapter.fetchMoreComplete(newData.size());
//        }
        String newStart = msgList.get(0).getId();
        String newEnd = msgList.get(msgList.size() - 1).getId();
        String oldStart = data.get(0).getId();
        String oldEnd = data.get(data.size() - 1).getId();

        //交集部分以新的msgList为准
        compareDeleteMsg(msgList, adapter.getData());
        adapter.notifyDataSetChanged();
        if (Integer.parseInt(newStart) < Integer.parseInt(oldStart)){
            List<TioMsg> newList = new ArrayList<>();
            for (TioMsg tioMsg : msgList){
                if (Integer.parseInt(tioMsg.getId()) < Integer.parseInt(oldStart)){
                    newList.add(tioMsg);
                }else {
                    break;
                }
            }
            adapter.fetchMoreComplete(newList);
        }
        if (Integer.parseInt(newEnd) > Integer.parseInt(oldEnd)){
            for (TioMsg tioMsg : msgList){
                if (Integer.parseInt(tioMsg.getId()) > Integer.parseInt(oldEnd)){
                    sendMsg(tioMsg);
                }
            }
        }else if (Integer.parseInt(newEnd) < Integer.parseInt(oldEnd)){
            for (TioMsg tioMsg : msgList){
                if (Integer.parseInt(tioMsg.getId()) > Integer.parseInt(newEnd)){
                    GroupMsgTableCrud.deleteMsg(tioMsg.getChatLinkId(), tioMsg.getId());
                    deleteMsg(Long.parseLong(tioMsg.getId()));
                }
            }
        }
    }

    @Override
    public void fetchMoreEnd(List<TioMsg> msgList) {
        if (msgList.size() > 0 && adapter.getData().size() > 0){
            String newStart = msgList.get(0).getId();
            String newEnd = msgList.get(msgList.size() - 1).getId();
            List<TioMsg> data = adapter.getData();
            String oldStart = data.get(0).getId();
            String oldEnd = data.get(data.size() - 1).getId();

            //交集部分以新的msgList为准
            compareDeleteMsg(msgList, adapter.getData());

            if (Integer.parseInt(newStart) < Integer.parseInt(oldStart)){
                List<TioMsg> newList = new ArrayList<>();
                for (TioMsg tioMsg : msgList){
                    if (Integer.parseInt(tioMsg.getId()) < Integer.parseInt(oldStart)){
                        newList.add(tioMsg);
                    }else {
                        break;
                    }
                }
                adapter.fetchMoreEnd(newList, true);
                return;
            }else {
                adapter.fetchMoreEnd(new ArrayList<>(), true);
                return;
            }
        }
        adapter.fetchMoreEnd(msgList, true);
    }

    private void compareDeleteMsg(List<TioMsg> newList, List<TioMsg> oldList) {
        int newStart = Integer.parseInt(newList.get(0).getId());
        int newEnd = Integer.parseInt(newList.get(newList.size() - 1).getId());
        int oldStart = Integer.parseInt(oldList.get(0).getId());
        int oldEnd = Integer.parseInt(oldList.get(oldList.size() - 1).getId());

        if (newEnd < oldStart){
            return;
        }
//        if (oldEnd < newEnd){
//            return;
//        }
        int start = Math.max(newStart, oldStart);
        int end = Math.min(newEnd, oldEnd);

        List<TioMsg> tmpList = newList;
        List<TioMsg> delList = new ArrayList<>();
        for (TioMsg tioMsg : oldList){
            int mid = Integer.parseInt(tioMsg.getId());
            if (mid >= start && mid <= end){
                boolean r = isInNew(tmpList, mid);
                if (r){

                }else {
                    GroupMsgTableCrud.deleteMsg(tioMsg.getChatLinkId(), String.valueOf(mid));
                    delList.add(tioMsg);
                }
//                tmpList = (List<TioMsg>)inNew[1];
            }
        }

        for (TioMsg tioMsg : delList){
            oldList.remove(tioMsg);
        }

//        List<TioMsg> needAddMsgList = new ArrayList<>();
        for (TioMsg tioMsg : newList){
            int mid = Integer.parseInt(tioMsg.getId());
            if (mid > oldStart && mid < oldEnd){
                if (!isInNew(oldList, mid)){
//                    needAddMsgList.add(tioMsg);
                    adapter.addOrderMsg(tioMsg);
                }
            }
        }

    }

    private boolean isInNew(List<TioMsg> msgList, int mid){
        for (TioMsg tioMsg:msgList){
            int cMid = Integer.parseInt(tioMsg.getId());
            if (cMid == mid){
                return true;
            }
        }
        return false;
//        for (TioMsg tioMsg:msgList){
//            int cMid = Integer.parseInt(tioMsg.getId());
//            if (cMid == mid){
//                return new Object[]{true, msgList.subList(1, msgList.size())};
//            }else if (cMid > mid){
//                return new Object[]{false, msgList};
//            }else if (cMid < mid){
//                continue;
//            }
//        }
//        return new Object[]{false, new ArrayList<TioMsg>()};
    }

    @Override
    public void fetchMoreComplete(List<TioMsg> msgList) {
        if (msgList.size() > 0 && adapter.getData().size() > 0){
            String newStart = msgList.get(0).getId();
            String newEnd = msgList.get(msgList.size() - 1).getId();
            List<TioMsg> data = adapter.getData();
            String oldStart = data.get(0).getId();
            String oldEnd = data.get(data.size() - 1).getId();
            if (Integer.parseInt(newStart) < Integer.parseInt(oldStart)){
                List<TioMsg> newList = new ArrayList<>();
                for (TioMsg tioMsg : msgList){
                    if (Integer.parseInt(tioMsg.getId()) < Integer.parseInt(oldStart)){
                        newList.add(tioMsg);
                    }else {
                        break;
                    }
                }
                adapter.fetchMoreComplete(newList);
//                adapter.fetchMoreComplete(newList);
            }else {
//                adapter.fetchMoreComplete(new ArrayList<>());
            }
            if (Integer.parseInt(newEnd) > Integer.parseInt(oldEnd)){
                for (TioMsg tioMsg : msgList){
                    if (Integer.parseInt(tioMsg.getId()) > Integer.parseInt(oldEnd)){
                        sendMsg(tioMsg);
                    }
                }
            }
        }else if (msgList.size() > 0){
            adapter.fetchMoreComplete(msgList);
        }

    }

    @Override
    public void readAllMsg() {
        adapter.readAllMsg();
    }

    @Override
    public void scrollToBottom() {
        if (adapter == null){
            return;
        }
        recyclerView.scrollToPosition(adapter.getBottomDataPosition());
    }

    @Override
    public void scrollToPosition(int p) {
        if (adapter == null){
            return;
        }
        recyclerView.scrollToPosition(p);
    }

    @Override
    public void scrollToBottomDelayed() {
        ThreadUtils.getMainHandler().postDelayed(this::scrollToBottom, 200);
    }

    @Override
    public void setOnFetchMoreListener(BaseFetchLoadAdapter.RequestFetchMoreListener requestFetchMoreListener) {
        if(adapter != null){
            adapter.setOnFetchMoreListener(requestFetchMoreListener);
        }
    }
}
