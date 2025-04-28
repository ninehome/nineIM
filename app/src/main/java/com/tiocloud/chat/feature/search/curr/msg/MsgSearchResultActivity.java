package com.tiocloud.chat.feature.search.curr.msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.msg.adapter.MsgSearchResultAdapter;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.dao.GroupMsgTableCrud;
import com.watayouxiang.db.table.GroupMsgTable;

import java.util.List;

public class MsgSearchResultActivity extends TioActivity {

    public static void start(Context context, String keyword, int chatMode, String chatlinkId, String chatName, String avatar) {
        Intent starter = new Intent(context, MsgSearchResultActivity.class);
        starter.putExtra("keyword", keyword);
        starter.putExtra("chatMode", chatMode);
        starter.putExtra("chatlinkId", chatlinkId);
        starter.putExtra("chatName", chatName);
        starter.putExtra("avatar", avatar);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_search_result);
        BarUtils.setStatusBarCustom(findViewById(R.id.h_view));
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        String chatName = intent.getStringExtra("chatName");
        String avatar = intent.getStringExtra("avatar");
        String chatLinkid = intent.getStringExtra("chatlinkId");
        int chatMode = intent.getIntExtra("chatMode", 0);
        List<GroupMsgTable> msgTables = GroupMsgTableCrud.queryList(keyword, chatMode, chatLinkid);
        MsgSearchResultAdapter adapter = new MsgSearchResultAdapter(msgTables, this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatMode == 1){
                    P2PSessionActivity.enter(getActivity(), chatLinkid);
                }else {
                    GroupSessionActivity.enter(getActivity(), chatLinkid);
                }
            }
        }, avatar, chatName, keyword);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupMsgTable groupMsgTable = msgTables.get(position);
                if (chatMode == 1){
                    P2PSessionActivity.enter(getActivity(), chatLinkid, groupMsgTable.getMsgId());
                }else {
                    GroupSessionActivity.enter(getActivity(), chatLinkid, groupMsgTable.getMsgId());
                }
            }
        });
    }
}
