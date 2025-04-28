package com.tiocloud.chat.feature.user.detail.feature.myself.mvp;

import android.content.Context;

import com.tiocloud.chat.feature.curr.modify.ModifyActivity;
import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.mvp.deletefriend.DeleteFriendPresenter;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class MyselfDetailPresenter extends MyselfDetailContract.Presenter {
    private final DeleteFriendPresenter deleteFriendPresenter;
    private String mRemarkName;

    public MyselfDetailPresenter(MyselfDetailContract.View view) {
        super(new MyselfDetailModel(), view);
        deleteFriendPresenter = new DeleteFriendPresenter(() -> getView().getActivity());
    }

    @Override
    public void detachView() {
        super.detachView();
        deleteFriendPresenter.detachView();
    }

    @Override
    public void reqUserInfo() {
        getModel().getUserInfo(getView().getUid(), new BaseModel.DataProxy<UserInfoResp>() {
            @Override
            public void onSuccess(UserInfoResp resp) {
                mRemarkName = resp.remarkname;
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
    public void init() {
        getView().initViews();
    }

    @Override
    public void doModifyRemarkName(Context context, String uid) {
        ModifyActivity.start_user(context, ModifyType.USER_REMARK_NAME, uid, mRemarkName);
    }
}
