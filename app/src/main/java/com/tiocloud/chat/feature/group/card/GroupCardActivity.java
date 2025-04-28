package com.tiocloud.chat.feature.group.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.card.fragment.SelectGroupFragment;
import com.tiocloud.chat.feature.group.card.mvp.ActivitySelectGroupContract;
import com.tiocloud.chat.feature.group.card.mvp.ActivitySelectGroupPresenter;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc : 群名片
 */
public class GroupCardActivity extends TioActivity implements ActivitySelectGroupContract.View {

    private FrameLayout frameLayout;
    private TioEditText et_input;
    private ActivitySelectGroupPresenter presenter;

    public static final int REQ_CODE_GROUP_ID = 1001;
    public static final int RESP_CODE_GROUP_ID = 1002;
    public static final String EXTRA_GROUP_ID = "extra_group_id";
    private WtTitleBar titleBar;

    public static void start(Activity context) {
        Intent starter = new Intent(context, GroupCardActivity.class);
        context.startActivityForResult(starter, REQ_CODE_GROUP_ID);
    }

    public void closePage(String groupId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GROUP_ID, groupId);
        setResult(GroupCardActivity.RESP_CODE_GROUP_ID, intent);
        finish();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_select_group_activity);
        findViews();
        initViews();

        presenter = new ActivitySelectGroupPresenter(this);
        presenter.installFragment();
        presenter.initEditText(et_input);
    }

    private void initViews() {
        titleBar.setTitle(getString(R.string.select_group));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void findViews() {
        frameLayout = findViewById(R.id.frameLayout);
        et_input = findViewById(R.id.et_input);
        titleBar = findViewById(R.id.titleBar);
    }

    @Override
    public void addFragment(SelectGroupFragment fragment) {
        fragment.setContainerId(frameLayout.getId());
        super.addFragment(fragment);
    }
}
