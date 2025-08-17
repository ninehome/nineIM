package com.tiocloud.chat.feature.group.card.fragment.mvp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.widget.dialog.base.CardDialog;
import com.tiocloud.chat.feature.group.card.GroupCardActivity;
import com.tiocloud.chat.feature.group.card.fragment.adapter.ExSelectGroupAdapter;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.CheckSendCardReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentSelectGroupPresenter extends FragmentSelectGroupContract.Presenter {

    private ExSelectGroupAdapter adapter;

    public FragmentSelectGroupPresenter(FragmentSelectGroupContract.View view) {
        super(new FragmentSelectGroupModel(), view);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ExSelectGroupAdapter() {
            @Override
            protected void onClickGroupItem(MailListResp.Group group) {
                super.onClickGroupItem(group);
                checkSendCardReq(group);
            }
        };
        recyclerView.setAdapter(adapter);
        // 添加头部
        View view = new View(recyclerView.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(12));
        view.setLayoutParams(params);
        adapter.addHeaderView(view);
    }

    private void checkSendCardReq(MailListResp.Group group) {
        CheckSendCardReq req = new CheckSendCardReq(group.groupid);
        req.setCancelTag(getModel());
        req.get(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                showConfirmDialog(group);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private void showConfirmDialog(MailListResp.Group group) {
        CardDialog cardDialog = new CardDialog(getView().getActivity());
        cardDialog.tv_title.setText(getView().getActivity().getString(R.string.groupchat_invite));
        cardDialog.hiv_avatar.tio_roundAvatar(group.avatar);
        cardDialog.tv_usrName.setText(StringUtil.nonNull(group.name));
        cardDialog.tv_positiveBtn.setText(getView().getActivity().getString(R.string.send_mingpian));
        cardDialog.tv_positiveBtn.setOnClickListener(view -> {
            Activity activity = getView().getActivity();
            if (activity instanceof GroupCardActivity) {
                GroupCardActivity friendActivity = (GroupCardActivity) activity;
                friendActivity.closePage(group.groupid);
            }
        });
        cardDialog.show();
    }

    // ====================================================================================
    // 搜索
    // ====================================================================================

    @Override
    public void search(final String keyWord) {
        if (adapter == null) return;
        getModel().getMailList(keyWord, new BaseModel.DataProxy<List<MailListResp.Group>>() {
            @Override
            public void onSuccess(List<MailListResp.Group> groups) {
                adapter.updateData(groups, keyWord);
            }
        });
    }

}
