package com.tiocloud.chat.feature.curr.detail.mvp;

import android.content.Context;

import com.tiocloud.chat.widget.dialog.base.AvatarDialog;
import com.tiocloud.chat.widget.dialog.base.GenderDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * author : TaoWang
 * date : 2020/3/13
 * desc :
 */
public class CurrInfoPresenter extends CurrInfoContract.Presenter {

    private GenderDialog genderDialog;
    private AvatarDialog avatarDialog;

    public CurrInfoPresenter(CurrInfoContract.View view) {
        super(new CurrInfoModel(), view);
    }

    @Override
    public void updateUIData() {
        getModel().reqCurrUser(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurr) {
                getView().onUserCurrResp(userCurr);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public AvatarDialog getAvatarDialog() {
        if (avatarDialog == null) {
            avatarDialog = new AvatarDialog(getView().getActivity(), new TioCallback<Void>() {
                @Override
                public void onTioSuccess(Void aVoid) {
                    updateUIData();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
            avatarDialog.setActivity(getView().getActivity());
        }
        return avatarDialog;
    }

    @Override
    public void showGenderDialog(Context context, int sex) {
        if (genderDialog == null) {
            genderDialog = new GenderDialog(context, new TioCallback<Void>() {
                @Override
                public void onTioSuccess(Void aVoid) {
                    genderDialog.dismiss();
                    updateUIData();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
        }
        genderDialog.setSex(sex);
        genderDialog.show();
    }
}
