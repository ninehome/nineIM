package com.tiocloud.chat.feature.group.at;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.ActivityAtBinding;
import com.tiocloud.chat.feature.group.at.mvp.AtContract;
import com.tiocloud.chat.feature.group.at.mvp.AtPresenter;
import com.watayouxiang.httpclient.model.response.AtGroupUserListResp;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   : @ 群好友列表页
 * </pre>
 */
public class AtActivity extends TioActivity implements AtContract.View {

    public static final int REQUEST_CODE = 0x10;
    public static final String RESULT_DATA = "data";

    private AtPresenter presenter;
    private ActivityAtBinding binding;
    private AtAdapter atAdapter;

    public static void start(@NonNull Fragment fragment, @NonNull String groupId) {
        Intent starter = new Intent(fragment.getContext(), AtActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        fragment.startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    public @NonNull
    String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AtPresenter(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void bindContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_at);
    }

    @Override
    public void initEditText() {
        binding.etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                presenter.onEtTextChanged(s);
            }
        });
    }

    @Override
    public void initRecyclerView() {
        atAdapter = new AtAdapter(binding.recyclerView);
        atAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AtGroupUserListResp.List list = atAdapter.getData().get(position);
                int uid = list.uid;
                String name = list.nick;
                TeamMember member = new TeamMember(String.valueOf(uid), uid == -99999 ? getString(R.string.allman) : name);

                // 返回上层页面数据
                Intent intent = new Intent();
                intent.putExtra(RESULT_DATA, member);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onAtGroupUserListResp(AtGroupUserListResp lists, @Nullable String searchkey) {
        if (atAdapter != null) {
            atAdapter.setNewData(lists, searchkey);
        }
    }
}
