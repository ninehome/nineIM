package com.tiocloud.chat.feature.user.selectfriend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.selectfriend.fragment.SelectFriendFragment;
import com.tiocloud.chat.feature.user.selectfriend.mvp.ActivitySelectFriendContract;
import com.tiocloud.chat.feature.user.selectfriend.mvp.ActivitySelectFriendPresenter;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class SelectFriendActivity extends TioActivity implements ActivitySelectFriendContract.View {

    private FrameLayout frameLayout;
    private TioEditText et_input;
    private ActivitySelectFriendPresenter presenter;

    public static final int REQ_CODE_UID = 1001;
    public static final int RESP_CODE_UID = 1002;
    public static final String EXTRA_UID = "extra_uid";

    public static void start(Activity context) {
        Intent starter = new Intent(context, SelectFriendActivity.class);
        context.startActivityForResult(starter, REQ_CODE_UID);
    }

    public void closePage(int uid) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_UID, uid);
        setResult(SelectFriendActivity.RESP_CODE_UID, intent);
        finish();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_select_friend_activity);
        findViews();
        initViews();

        presenter = new ActivitySelectFriendPresenter(this);
        presenter.installFragment();
        presenter.initEditText(et_input);
    }

    private void initViews() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void findViews() {
        frameLayout = findViewById(R.id.frameLayout);
        et_input = findViewById(R.id.et_input);
    }

    @Override
    public void addFragment(SelectFriendFragment fragment) {
        fragment.setContainerId(frameLayout.getId());
        super.addFragment(fragment);
    }
}
