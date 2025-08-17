package com.tiocloud.chat.feature.group.silent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioSilentMgrActivityBinding;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/06
 *     desc   : 禁言名单
 * </pre>
 */
public class SilentMgrActivity extends EasyActivity<TioSilentMgrActivityBinding> implements MvpContract.View {

    private MvpPresenter presenter;
    private ListAdapter listAdapter;

    public static void start(Context context, String groupId) {
        Intent starter = new Intent(context, SilentMgrActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        context.startActivity(starter);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.tio_silent_mgr_activity;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    protected Integer getStatusBarColor() {
        return Color.WHITE;
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MvpPresenter(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void resetUI() {
        // 输入栏
        binding.etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s == null) return;
                String keyWord = s.toString();
                presenter.search(keyWord);
            }
        });
        // list
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ListAdapter(null);
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_delete) {
                ListModel model = listAdapter.getData().get(position);
                if (model.getItemType() == ListModel.ITEM_TYPE_NORMAL) {
                    presenter.forbidden_cancelUser_confirmDialog(model.getNormalItem(), position);
                }
            }
        });
        listAdapter.setOnLoadMoreListener(() -> presenter.loadMore(), binding.recyclerView);
        binding.recyclerView.setAdapter(listAdapter);
        // 右侧按钮xx
        TextView tvRight = binding.titleBar.getTvRight();
        tvRight.setText(getString(R.string.edit));
        ListNormalItem.setShowDelete(false);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListNormalItem.isShowDelete()) {
                    // 显示删除：隐藏删除，显示"编辑"
                    tvRight.setText(getString(R.string.edit));
                    ListNormalItem.setShowDelete(false);
                } else {
                    // 隐藏删除：显示删除，显示"取消"
                    tvRight.setText(getString(R.string.cancel));
                    ListNormalItem.setShowDelete(true);
                }
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLoadSuccess(int pageNumber, List<ListModel> models, ForbiddenUserListResp resp) {
        if (pageNumber == 1) {
            listAdapter.setNewData(models);
        } else {
            if (models != null) listAdapter.addData(models);
        }
        if (resp.isLastPage()) {
            listAdapter.loadMoreEnd();
        } else {
            listAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadError(int pageNumber, String msg) {
        if (pageNumber != 1) {
            listAdapter.loadMoreFail();
        }
        TioToast.showShort(msg);
    }

    @Override
    public void onRemoveListItem(int position) {
        listAdapter.remove(position);
    }
}
