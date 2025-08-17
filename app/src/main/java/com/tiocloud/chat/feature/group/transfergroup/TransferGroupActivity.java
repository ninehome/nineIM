package com.tiocloud.chat.feature.group.transfergroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.group.transfergroup.fragment.TransferGroupFragment;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class TransferGroupActivity extends TioActivity {

    private FrameLayout frameLayout;
    private EditText et_input;
    private TransferGroupFragment fragment;

    public static void start(Context context, String groupId) {
        Intent starter = new Intent(context, TransferGroupActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        context.startActivity(starter);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_transfer_group_activity);
        initToolbar();
        findViews();
        initEditText();
        installFragment();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    private void installFragment() {
        fragment = TransferGroupFragment.create(getGroupId());
        fragment.setContainerId(frameLayout.getId());
        addFragment(fragment);
    }

    private void initEditText() {
        et_input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s != null && fragment != null) {
                    String keyWord = s.toString();
                    fragment.presenter.search(keyWord);
                }
            }
        });
    }

    private void initToolbar() {

    }

    private void findViews() {
        frameLayout = findViewById(R.id.frameLayout);
        et_input = findViewById(R.id.et_input);
    }
}
