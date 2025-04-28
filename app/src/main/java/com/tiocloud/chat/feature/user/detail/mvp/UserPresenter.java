package com.tiocloud.chat.feature.user.detail.mvp;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class UserPresenter extends UserContract.Presenter {
    public UserPresenter(UserContract.View view) {
        super(new UserModel(), view);
    }

    @Override
    public void updateUI(final String uid) {
        getModel().isFriend(Integer.parseInt(uid), new BaseModel.DataProxy<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                getView().onIsFriendResp(integer == 1);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        });
    }
}
