package com.tiocloud.chat.feature.group.transfergroup.fragment.mvp;

import android.view.View;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.transfergroup.fragment.adapter.TransferGroupAdapter;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ChangeOwnerResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class TransferGroupPresenter extends TransferGroupContract.Presenter {

    public TransferGroupPresenter(TransferGroupContract.View view) {
        super(new TransferGroupModel(), view);
    }

    @Override
    public void init() {
        getView().initRecyclerView();
        load(null, 1);
    }

    private int mPageNumber = 0;
    private String mKeyWord = null;

    @Override
    public void load(String keyWord, int pageNumber) {
        mPageNumber = pageNumber;
        mKeyWord = keyWord;

        final TransferGroupAdapter adapter = getView().getListAdapter();
        if (adapter == null) return;

        getModel().getGroupUserList(String.valueOf(pageNumber), getView().getGroupId(), mKeyWord, new TioCallback<GroupUserListResp>() {
            @Override
            public void onTioSuccess(GroupUserListResp users) {
                if (users.firstPage) {
                    adapter.setNewData(users, keyWord);
                } else {
                    adapter.addData(users, keyWord);
                }
                if (users.lastPage) {
                    adapter.loadMoreEnd();
                } else {
                    adapter.loadMoreComplete();
                }
            }

            @Override
            public void onTioError(String msg) {
                if (pageNumber > 1) {
                    adapter.loadMoreFail();
                }
            }
        });
    }

    @Override
    public void loadMoreList() {
        load(mKeyWord, ++mPageNumber);
    }

    @Override
    public void search(String keyWord) {
        load(keyWord, 1);
    }

    // ====================================================================================
    // 选新群主
    // ====================================================================================

    @Override
    public void showTransferGroupConfirmDialog(final int uid, String nick) {
        new EasyOperDialog.Builder(String.format(Locale.getDefault(),
                getView().getActivity().getString(R.string.confirm_choose_s_new_grouper), nick))
                .setPositiveBtnTxt(getView().getActivity().getString(R.string.confirm))
                .setNegativeBtnTxt(getView().getActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        transferGroupOwner(uid, dialog);
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getView().getActivity());
    }

    private void transferGroupOwner(final int uid, final EasyOperDialog dialog) {
        getModel().changeGroupOwner(getView().getGroupId(), uid, new BaseModel.DataProxy<ChangeOwnerResp>() {
            @Override
            public void onSuccess(ChangeOwnerResp changeOwnerResp) {
                dialog.dismiss();
                getView().getActivity().finish();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
    }
}
