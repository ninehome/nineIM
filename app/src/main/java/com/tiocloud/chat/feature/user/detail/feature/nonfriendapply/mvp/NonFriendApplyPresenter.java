package com.tiocloud.chat.feature.user.detail.feature.nonfriendapply.mvp;

import android.app.Activity;
import android.view.View;

import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.mvp.dealapply.DealApplyContract;
import com.tiocloud.chat.mvp.dealapply.DealApplyPresenter;
import com.watayouxiang.httpclient.model.response.DealApplyResp;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class NonFriendApplyPresenter extends NonFriendApplyContract.Presenter {

    private final DealApplyPresenter dealApplyPresenter;

    public NonFriendApplyPresenter(NonFriendApplyContract.View view) {
        super(new NonFriendApplyModel(), view);
        dealApplyPresenter = new DealApplyPresenter(() -> getView().getActivity());
    }

    @Override
    public void detachView() {
        super.detachView();
        dealApplyPresenter.detachView();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void init() {
        getView().resetViews();
        getView().initViews();
        getModel().getUserInfo(getView().getUid(), new BaseModel.DataProxy<UserInfoResp>() {
            @Override
            public void onSuccess(UserInfoResp resp) {
                getView().onUserInfoResp(resp);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void doAgreeAddFriend(String applyId, String remarkName, View view) {
        view.setEnabled(false);
        dealApplyPresenter.start(applyId, remarkName, new DealApplyContract.Presenter.DealApplyProxy() {
            @Override
            public void onSuccess(DealApplyResp data) {
                resetFragment();
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

            private void resetFragment() {
                Activity activity = getView().getActivity();
                if (!(activity instanceof UserDetailActivity)) {
                    throw new ClassCastException("activity not UserInfoActivity");
                }
                UserDetailActivity userActivity = (UserDetailActivity) activity;
                userActivity.updateUI();
            }
        });
    }
}
