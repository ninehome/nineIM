package com.tiocloud.chat.feature.user.detail.feature.friend.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.mvp.deletefriend.DeleteFriendContract;
import com.tiocloud.chat.mvp.deletefriend.DeleteFriendPresenter;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.CommonTextInputDialog;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.model.request.ModifyRemarkNameReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

/**
 * author : TaoWang
 * date : 2020-02-21
 * desc :
 */
public class FriendDetailPresenter extends FriendDetailContract.Presenter {
    private final DeleteFriendPresenter deleteFriendPresenter;
    private String mRemarkName;

    public FriendDetailPresenter(FriendDetailContract.View view) {
        super(new FriendDetailModel(), view);
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
    public void doDelFriend(View view) {
        view.setEnabled(false);
        deleteFriendPresenter.start(Integer.parseInt(getView().getUid()), getView().getForceDel(), new DeleteFriendContract.Presenter.DeleteFriendProxy() {
            @Override
            public void onSuccess(String data) {
                getView().getActivity().finish();
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
    public void doModifyRemarkName(Context context, String uid) {
//        ModifyActivity.start_user(context, ModifyType.USER_REMARK_NAME, uid, mRemarkName);
        new CommonTextInputDialog(getView().getActivity())
                .setEditHeight(100)
                .setMaxLimit(30)
                .setTopTitle(context.getString(R.string.remark_name))
                .setSubTitle(context.getString(R.string.input_remark_name))
                .setPositiveText(context.getString(R.string.save))
                .setEdittext(mRemarkName)
                .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
//                        if (TextUtils.isEmpty(submitTxt)){
//                            ToastUtils.showShort("备注名不能为空");
//                            return;
//                        }
                        dialog.dismiss();
                        ModifyRemarkNameReq req = new ModifyRemarkNameReq(submitTxt, uid);
                        req.setCancelTag(this);
                        req.post(new TioCallbackImpl<String>() {
                            @Override
                            public void onTioSuccess(String s) {
                                if (TioConfig.friendRemarkCacheMap.containsKey(uid)){
                                    TioConfig.friendRemarkCacheMap.remove(uid);
                                }
                                reqUserInfo();
                            }

                            @Override
                            public void onTioError(String msg) {
                            }
                        });
                    }

                    @Override
                    public void onClickNegative(View view, CommonTextInputDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
