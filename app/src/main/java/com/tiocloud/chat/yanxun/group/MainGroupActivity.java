package com.tiocloud.chat.yanxun.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.group.GroupFragment;
import com.tiocloud.chat.feature.search.curr.SearchActivity;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * 聊天界面
 */
public class MainGroupActivity extends TioActivity {
    private FrameLayout frameLayout;
    GroupFragment fragment;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainGroupActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_main_group_activity);

        frameLayout = findViewById(R.id.frameLayout);

        fragment = new GroupFragment();
        fragment.setContainerId(R.id.frameLayout);
        replaceFragment(fragment);

        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(v.getContext());
            }
        });
    }
}
