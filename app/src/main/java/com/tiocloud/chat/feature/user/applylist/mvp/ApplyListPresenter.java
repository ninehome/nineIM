package com.tiocloud.chat.feature.user.applylist.mvp;

import android.view.View;

import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.feature.user.detail.model.NonFriendApply;
import com.tiocloud.chat.mvp.dealapply.DealApplyContract;
import com.tiocloud.chat.mvp.dealapply.DealApplyPresenter;
import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class ApplyListPresenter extends ApplyListContract.Presenter {

    private final DealApplyPresenter dealApplyPresenter;

    public ApplyListPresenter(ApplyListContract.View view) {
        super(new ApplyListModel(), view);
        dealApplyPresenter = new DealApplyPresenter(() -> getView().getActivity());
    }

    @Override
    public void detachView() {
        super.detachView();
        dealApplyPresenter.detachView();
    }

    @Override
    public void init() {
        getView().initTitleBar();
        getView().initRecyclerView();
    }

    /**
     * 申请列表数据
     */
    @Override
    public void requestApplyList() {
        getModel().getApplyList(new BaseModel.DataProxy<ApplyListResp>() {
            @Override
            public void onSuccess(ApplyListResp data) {
                getView().onApplyListResp(data);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
    }

    /**
     * 同意添加好友
     *
     * @param applyId
     * @param remarkName
     * @param position
     * @param view
     */
    @Override
    public void doAgreeAddFriend(String applyId, String remarkName, final int position, final View view) {
        view.setEnabled(false);
        dealApplyPresenter.start(applyId, remarkName, new DealApplyContract.Presenter.DealApplyProxy() {
            @Override
            public void onSuccess(DealApplyResp data) {
                getView().onAgreeAddFriend(data, position);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                view.setEnabled(true);
            }
        });
    }

    @Override
    public void openUserDetailActivity(ApplyListResp.Data item) {
        NonFriendApply nonFriendApply = new NonFriendApply(item.greet, String.valueOf(item.id), item.nick);
        UserDetailActivity.start(getView().getActivity(), String.valueOf(item.uid), nonFriendApply, false);
    }
}
