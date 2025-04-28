package com.tiocloud.chat.feature.group.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.info.mvp.ActivityGroupInfoContract;
import com.tiocloud.chat.feature.group.info.mvp.ActivityGroupInfoPresenter;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class GroupInfoActivity extends TioActivity implements ActivityGroupInfoContract.View {

    private ActivityGroupInfoPresenter presenter;

    public static void start(Context context, String groupId) {
        if (context == null || TextUtils.isEmpty(groupId)) return;
        Intent starter = new Intent(context, GroupInfoActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        context.startActivity(starter);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_group_info_activity);

        presenter = new ActivityGroupInfoPresenter(this);
        presenter.showFragment();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void addFragment(TioFragment fragment) {
        fragment.setContainerId(R.id.ll_rootView);
        super.addFragment(fragment);
    }

    public WtTitleBar getTitleBar() {
        return findViewById(R.id.titleBar);
    }
}
