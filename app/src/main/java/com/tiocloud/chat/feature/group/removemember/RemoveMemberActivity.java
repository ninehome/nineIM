package com.tiocloud.chat.feature.group.removemember;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.removemember.fragment.RemoveMemberFragment;
import com.tiocloud.chat.feature.group.removemember.mvp.ActivityRemoveMemberContract;
import com.tiocloud.chat.feature.group.removemember.mvp.ActivityRemoveMemberPresenter;
import com.watayouxiang.androidutils.widget.edittext.TioEditText;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class RemoveMemberActivity extends TioActivity implements ActivityRemoveMemberContract.View {

    private ActivityRemoveMemberPresenter presenter = new ActivityRemoveMemberPresenter(this);
    private RemoveMemberFragment fragment;

    public static void start(Context context, String groupId) {
        Intent starter = new Intent(context, RemoveMemberActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        context.startActivity(starter);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_remove_member_activity);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void installFragment() {
        fragment = RemoveMemberFragment.create(getGroupId());
        fragment.setContainerId(R.id.frameLayout);
        WtTitleBar titleBar = findViewById(R.id.titleBar);
        fragment.presenter.installMenuBtn(titleBar.getTvRight());
        super.addFragment(fragment);
    }

    @Override
    public void initEditText() {
        TioEditText et_input = findViewById(R.id.et_input);
        et_input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s != null && fragment != null) {
                    String keyWord = s.toString();
                    fragment.load(keyWord);
                }
            }
        });
    }
}
