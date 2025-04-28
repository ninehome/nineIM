package com.tiocloud.chat.feature.home.user.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class UserPresenter extends UserContract.Presenter {

    public UserPresenter(UserContract.View view) {
        super(new UserModel(), view);
    }

    @Override
    public void init() {
        getView().initUI();
        updateUIData();
    }

    @Override
    public void updateUIData() {
        getModel().getUserCurrReq().get(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                getView().updateUI(userCurrResp);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

}
