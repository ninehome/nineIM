package com.tiocloud.chat.yanxun.groupsend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.groupsend.fragment.SelectGroupSendFragment;
import com.tiocloud.chat.yanxun.groupsend.mvp.ActivityGroupSendContract;
import com.tiocloud.chat.yanxun.groupsend.mvp.ActivityGroupSendPresenter;
import com.tiocloud.chat.yanxun.groupsend.select.SelectFriendItem;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.ActChatReq;
import com.watayouxiang.httpclient.model.response.ActChatResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class SelectGroupSendActivity extends TioActivity implements ActivityGroupSendContract.View {

    private FrameLayout frameLayout;
    private EditText et_input;
    private WtTitleBar titleBar;
    private Button btnNextStep;

    private ActivityGroupSendPresenter presenter;

    public static void start(Activity context) {
        Intent starter = new Intent(context, SelectGroupSendActivity.class);
        context.startActivityForResult(starter, 1);
    }

    public void setBtnNextStep(String str){
        this.btnNextStep.setText(str);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_group_send);
        findViews();
        initViews();

        presenter = new ActivityGroupSendPresenter(this);
        presenter.installFragment();
        presenter.initEditText(et_input);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK){
            setResult(2);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initViews() {

    }

    private void findViews() {
        frameLayout = findViewById(R.id.frameLayout);
        et_input = findViewById(R.id.et_input);
        titleBar = findViewById(R.id.titleBar);
        btnNextStep = findViewById(R.id.btn_next_step);
        titleBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    private boolean isExsit(List<SelectFriendItem> list, SelectFriendItem item){
        for (SelectFriendItem selectFriendItem : list){
            if (selectFriendItem.getUserId().equals(item.getUserId())){
                return true;
            }
        }
        return false;
    }

    private void nextStep() {
        List<SelectFriendItem> items = new ArrayList<>();
        List<String> checkNames = presenter.fragment.getCheckNames();
        List<String> checkIds = presenter.fragment.getCheckIds();
        for (int i = 0; i < checkIds.size(); i++) {
            items.add(new SelectFriendItem(checkIds.get(i), checkNames.get(i), 0));
        }
        if (presenter.fragment.getSelectedLableFriendIds() != null){
            for (SelectFriendItem selectFriendItem : presenter.fragment.getSelectedLableFriendIds()){
                if (!isExsit(items, selectFriendItem)){
                    items.add(selectFriendItem);
                }
            }
        }
        if (presenter.fragment.getSelectGroup() != null){
            items.addAll(presenter.fragment.getSelectGroup());
        }
        if (items.size() > 0) {
            initChatLinkIds(items);
        } else {
            ToastUtils.showShort(getString(R.string.qingzhishaoxuanze));
        }
    }

    volatile int count;

    public synchronized void setCount(int count) {
        this.count = count;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void countDown(){
        this.count--;
    }
    private List<String> chatLinkIdList = new ArrayList<>();
    private void initChatLinkIds(List<SelectFriendItem> items) {
        setCount(items.size());
        for (SelectFriendItem selectFriendItem : items){
            if (selectFriendItem.getIsRoom() == 0){
                ActChatReq actChatReq = ActChatReq.getP2P(selectFriendItem.getUserId());
                actChatReq.setCancelTag(this);
                actChatReq.get(new TioCallbackImpl<ActChatResp>() {
                    @Override
                    public void onTioSuccess(ActChatResp actChatResp) {
                        chatLinkIdList.add(actChatResp.chat.id);
                        com.blankj.utilcode.util.LogUtils.d("zlb单聊==>"+ JSON.toJSONString(actChatResp));
//                        proxy.onSuccess(actChatResp);
                        countDown();
                        if (getCount() == 0){
                            startSend(items);
                        }
                    }

                    @Override
                    public void onTioError(String msg) {
//                        proxy.onFailure(msg);
                        ToastUtils.showShort(getString(R.string.get_session_fail));
                        finish();
                    }
                });
            }else {
                ActChatReq actChatReq = ActChatReq.getGroup(selectFriendItem.getUserId());
                actChatReq.setCancelTag(this);
                actChatReq.get(new TioCallbackImpl<ActChatResp>() {
                    @Override
                    public void onTioSuccess(ActChatResp actChatResp) {
                        chatLinkIdList.add(actChatResp.chat.id);
                        LogUtils.d("zlb群聊==>"+ JSON.toJSONString(actChatResp));
//                        proxy.onSuccess(actChatResp);
                        countDown();
                        if (getCount() == 0){
                            startSend(items);
                        }
                    }

                    @Override
                    public void onTioError(String msg) {
//                        proxy.onFailure(msg);
                        ToastUtils.showShort(getString(R.string.get_session_fail));
                        finish();
                    }
                });
            }

        }
    }

    public void startSend(List<SelectFriendItem> items){
        GroupSendMessageActivity.items = items;
        GroupSendMessageActivity.chatLinkIdList = chatLinkIdList;
        Intent intent = new Intent(SelectGroupSendActivity.this, GroupSendMessageActivity.class);
        startActivityForResult(intent, 2);
    }


    public TextView getTvMenuBtn() {
        if (titleBar != null) {
            return titleBar.getTvRight();
        }
        return null;
    }

    @Override
    public void addFragment(SelectGroupSendFragment fragment) {
        fragment.setContainerId(frameLayout.getId());
        super.addFragment(fragment);
    }
}
