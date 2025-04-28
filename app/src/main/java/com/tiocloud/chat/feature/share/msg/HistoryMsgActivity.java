package com.tiocloud.chat.feature.share.msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.RecyclerViewDivider;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.MsgHistotyEntity;
import com.watayouxiang.imclient.model.body.wx.WxHistoryMsgResp;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   : 把 "消息" 转发给 "好友/群聊"（消息转发）
 * </pre>
 */
public class HistoryMsgActivity extends TioActivity{


    private WtTitleBar titleBar;
    private RecyclerView recyclerView;
    private String chatMode;
    private String msgIds;
    private String title;
    private String chatLinkId;
    private List<MsgHistotyEntity> msgHistotyEntities = new ArrayList<>();
    private HistoryMsgAdapter msgAdapter;

    public static void start(Context context, String chatMode, String msgIds,String title) {
        Intent starter = new Intent(context, HistoryMsgActivity.class);
        starter.putExtra("chatMode", chatMode);
        starter.putExtra("msgIds", msgIds);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_msg);
        initView();
    }

    private void initView(){
        chatMode = getIntent().getStringExtra("chatMode");
        msgIds = getIntent().getStringExtra("msgIds");
        title = getIntent().getStringExtra("title");
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle(title);
        titleBar.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.requestDisallowInterceptTouchEvent(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                }
            }
        });
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        msgAdapter = new HistoryMsgAdapter(this,msgHistotyEntities);


        recyclerView.setAdapter(msgAdapter);
        getData();
    }

    private void getData(){

        final QueryMsgHistoryReq req = new QueryMsgHistoryReq(chatMode,msgIds);
        if (req == null) return;


        TioHttpClient.post(req, new TioCallback<List<MsgHistotyEntity>>() {

            @Override
            public void onTioSuccess(List<MsgHistotyEntity> tiHistorytMsgList) {
                msgHistotyEntities = tiHistorytMsgList;
                msgAdapter.setNewDatas(msgHistotyEntities);
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showLong(msg);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
