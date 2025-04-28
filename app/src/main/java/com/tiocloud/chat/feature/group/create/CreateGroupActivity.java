package com.tiocloud.chat.feature.group.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.create.fragment.CreateGroupFragment;
import com.tiocloud.chat.feature.group.create.mvp.ActivityCreateGroupContract;
import com.tiocloud.chat.feature.group.create.mvp.ActivityCreateGroupPresenter;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class CreateGroupActivity extends TioActivity implements ActivityCreateGroupContract.View {

    private FrameLayout frameLayout;
    private EditText et_input;
    private WtTitleBar titleBar;

    private ActivityCreateGroupPresenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateGroupActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, ArrayList<String> checkIds) {
        Intent starter = new Intent(context, CreateGroupActivity.class);
        starter.putStringArrayListExtra("checkIds", checkIds);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_create_group);
        findViews();
        initViews();

        presenter = new ActivityCreateGroupPresenter(this);
        presenter.installFragment();
        presenter.initEditText(et_input);
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
    }

    public TextView getTvMenuBtn() {
        if (titleBar != null) {
            return titleBar.getTvRight();
        }
        return null;
    }

    @Override
    public void addFragment(CreateGroupFragment fragment) {
        fragment.setContainerId(frameLayout.getId());
        super.addFragment(fragment);
    }
}
