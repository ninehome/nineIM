package com.tiocloud.chat.mvp.deletefriend;

import android.view.View;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.tiocloud.chat.widget.dialog.tio.DeleteFriendDialog;

/**
 * author : TaoWang
 * date : 2020-02-25
 * desc :
 */
public class DeleteFriendPresenter extends DeleteFriendContract.Presenter {

    public DeleteFriendPresenter(DeleteFriendContract.View view) {
        super(new DeleteFriendModel(), view);
    }

    @Override
    public void start(final int uid, boolean forceDel, final DeleteFriendProxy proxy) {
        showDeleteFriendDialog(uid, forceDel, proxy);
    }

    // ====================================================================================
    // 显示弹窗
    // ====================================================================================

    private void showDeleteFriendDialog(final int uid, boolean forceDel, final DeleteFriendProxy proxy) {
        new DeleteFriendDialog(new DeleteFriendDialog.OnBtnListener() {
            @Override
            public void onClickPositive(View view, DeleteFriendDialog dialog) {
                delFriend(uid, forceDel, proxy, dialog);
            }

            @Override
            public void onClickNegative(View view, DeleteFriendDialog dialog) {
                dialog.dismiss();
                proxy.onCancel();
                proxy.onFinish();
            }
        }).show_unCancel(getView().getContext());
    }


    // ====================================================================================
    // 调用接口
    // ====================================================================================

    private void delFriend(int uid, boolean forceDel, final DeleteFriendProxy proxy, final DeleteFriendDialog dialog) {
        getModel().delFriend(uid, forceDel, new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String resp) {
                dialog.dismiss();
                proxy.onSuccess(resp);
                proxy.onFinish();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                dialog.dismiss();
                proxy.onFailure(msg);
                proxy.onFinish();
            }
        });
    }
}
