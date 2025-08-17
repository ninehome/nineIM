package com.tiocloud.chat.feature.user.selectfriend.fragment.mvp;

import android.app.Activity;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.selectfriend.SelectFriendActivity;
import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.ExSelectFriendAdapter;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.widget.dialog.base.CardDialog;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.tiocloud.chat.util.StringUtil;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentSelectFriendPresenter extends FragmentSelectFriendContract.Presenter {

    private ExSelectFriendAdapter adapter;

    public FragmentSelectFriendPresenter(FragmentSelectFriendContract.View view) {
        super(new FragmentSelectFriendModel(), view);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void initRecyclerView(RecyclerView recyclerView, ContactsCatalogView contactsCatalogView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ExSelectFriendAdapter() {
            @Override
            protected void onClickContactItem(MailListResp.Friend friend) {
                super.onClickContactItem(friend);
                showConfirmDialog(friend);
            }
        };
        adapter.installCatalogView(contactsCatalogView, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void showConfirmDialog(MailListResp.Friend friend) {
        CardDialog cardDialog = new CardDialog(getView().getActivity());
        cardDialog.hiv_avatar.tio_roundAvatar(friend.avatar);
        cardDialog.tv_usrName.setText(!TextUtils.isEmpty(friend.remarkname) ? friend.remarkname : StringUtil.nonNull(friend.nick));
        cardDialog.tv_positiveBtn.setText(getView().getActivity().getString(R.string.send_card));
        cardDialog.tv_positiveBtn.setOnClickListener(view -> {
            Activity activity = getView().getActivity();
            if (activity instanceof SelectFriendActivity) {
                SelectFriendActivity friendActivity = (SelectFriendActivity) activity;
                friendActivity.closePage(friend.uid);
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
        getModel().getMailList(keyWord, new BaseModel.DataProxy<List<MailListResp.Friend>>() {
            @Override
            public void onSuccess(List<MailListResp.Friend> friends) {
                adapter.updateData(friends, keyWord);
            }
        });
    }

}
