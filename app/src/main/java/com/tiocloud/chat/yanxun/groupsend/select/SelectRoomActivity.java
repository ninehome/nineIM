package com.tiocloud.chat.yanxun.groupsend.select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SelectRoomActivity extends TioActivity implements ChatAdapter.OnItemSelectedChangeListener {

    private static final String TAG = "SelectRoomActivity:zlb";
    private RecyclerView rvChatList;
    private ChatAdapter chatAdapter;
    private TextView tvSelectedCount;
    private Set<SelectFriendItem> selectedUserIdList = new HashSet<>();
    private View llSelectedCount;
    private TextView btnSelectAll;
    private View btnSelectFinish;
    private WtTitleBar titleBar;

    public static void start(Fragment fg, Activity ctx, int requestCode, List<SelectFriendItem> mItemList) {
        Intent intent = new Intent(ctx, SelectRoomActivity.class);
        if (mItemList != null && mItemList.size() > 0) {
            intent.putExtra("SELECTED_ITEMS", JSON.toJSONString(mItemList));
        }
        fg.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chat);

        initActionBar();

        String sSelectedList = getIntent().getStringExtra("SELECTED_ITEMS");
        if (!TextUtils.isEmpty(sSelectedList)) {
            selectedUserIdList.addAll(JSON.parseArray(sSelectedList, SelectFriendItem.class));
        }

        initView();

        initData();
//        EventBusHelper.register(this);
    }

//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void helloEventBus(final EventSentChatHistory message) {
//        finish();
//    }

    private void initData() {
        MailListReq mailListReq = new MailListReq("2", null);
        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(mailListReq, new TioCallback<MailListResp>() {
            @Override
            public void onTioSuccess(MailListResp mailListResp) {
//                proxy.onSuccess(mailListResp.group);
                List<ChatAdapter.Item> data = new ArrayList<>(mailListResp.group.size());
            for (MailListResp.Group group : mailListResp.group) {
                ChatAdapter.Item item = ChatAdapter.Item.fromFriend(group);
                if (selectedUserIdList.contains(new SelectFriendItem(item.friend.groupid, item.friend.name, 1))) {
                    item.selected = true;
                }
                data.add(item);
            }
                chatAdapter.setData(data);
                titleBar.getTvRight().setEnabled(true);
                updateSelectedCount();
            }

            @Override
            public void onTioError(String msg) {
//                proxy.onFailure(msg);
            }
        });
    }

    private void initView() {
        btnSelectFinish = findViewById(R.id.btnSelectFinish);
//        ButtonColorChange.colorChange(this, btnSelectFinish);
        btnSelectFinish.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.putExtra("SELECTED_ITEMS", JSON.toJSONString(selectedUserIdList));
            setResult(RESULT_OK, intent);
            finish();
        });

//        btnSelectAll = findViewById(R.id.tv_title_right);
//        btnSelectAll.setText(R.string.select_all);
        titleBar = findViewById(R.id.titleBar);
        titleBar.getTvRight().setText("全选");
        titleBar.getTvRight().setOnClickListener((v) -> {
            if (selectedUserIdList.size() == chatAdapter.getItemCount()) {
                chatAdapter.cancelAll();
            } else {
                chatAdapter.selectAll();
            }
        });
//        ButtonColorChange.textChange(this, btnSelectAll);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);
        tvSelectedCount.setText(getString(R.string.room_count_place_holder, selectedUserIdList.size()));
//        ButtonColorChange.textChange(this, tvSelectedCount);
        rvChatList = findViewById(R.id.rvChatList);
        rvChatList.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, /*coreManager.getSelf().getUserId()*/"1");
        rvChatList.setAdapter(chatAdapter);
    }

    private void initActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        findViewById(R.id.iv_title_left).setOnClickListener((v) -> {
//            onBackPressed();
//        });
//        TextView tvTitle = findViewById(R.id.tv_title_center);
//        tvTitle.setText(getString(R.string.select_room));
    }

    @Override
    public void onItemSelectedChange(ChatAdapter.Item item, boolean isSelected) {
        Log.i(TAG, "checked change " + isSelected + ", " + item);
        if (item.selected) {
            selectedUserIdList.add(new SelectFriendItem(item.friend.groupid, item.friend.name, 1));
        } else {
            selectedUserIdList.remove(new SelectFriendItem(item.friend.groupid, item.friend.name, 1));
        }
        updateSelectedCount();
    }

    private void updateSelectedCount() {
        if (selectedUserIdList.isEmpty()) {
            btnSelectFinish.setEnabled(false);

            tvSelectedCount.setText(getString(R.string.room_count_place_holder, selectedUserIdList.size()));
        } else {
            btnSelectFinish.setEnabled(true);
            tvSelectedCount.setText(getString(R.string.room_count_place_holder, selectedUserIdList.size()));
        }
        if (selectedUserIdList.size() == chatAdapter.getItemCount()) {
            titleBar.getTvRight().setText(R.string.cancel);
        } else {
            titleBar.getTvRight().setText(R.string.select_all);
        }
    }
}
