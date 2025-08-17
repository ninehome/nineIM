package com.tiocloud.chat.yanxun.lable.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.lable.create.fragment.CreateLableFragment;
import com.tiocloud.chat.yanxun.lable.create.mvp.ActivityCreateLableContract;
import com.tiocloud.chat.yanxun.lable.create.mvp.ActivityCreateLablePresenter;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class CreateLableSelectContactActivity extends TioActivity implements ActivityCreateLableContract.View {

    private FrameLayout frameLayout;
    private EditText et_input;
    private WtTitleBar titleBar;

    private ActivityCreateLablePresenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateLableSelectContactActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_create_lable);
        findViews();
        initViews();

        presenter = new ActivityCreateLablePresenter(this);
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
    public void addFragment(CreateLableFragment fragment) {
        fragment.setContainerId(frameLayout.getId());
        super.addFragment(fragment);
    }
}
