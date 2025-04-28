package com.tiocloud.chat.widget.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.tiocloud.chat.feature.session.p2p.P2PSessionActivity;
import com.tiocloud.chat.feature.share.friend.ShareFriendActivity;
import com.tiocloud.chat.mvp.deletefriend.DeleteFriendContract;
import com.tiocloud.chat.mvp.deletefriend.DeleteFriendPresenter;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.prefernces.TioDBPreferences;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   : 联系人长按弹窗
 * </pre>
 */
public class FriendOpWindow extends BaseHomeWindow {
    private TextView tv_sendMsg;
    private TextView tv_deleteFriend;
    private TextView tv_shareOther;
    private DeleteFriendPresenter deleteFriendPresenter;
    private IContact contact;

    public FriendOpWindow(View anchor) {
        super(anchor);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.friend_oper_window;
    }

    @Override
    protected void initViews() {
        tv_sendMsg = findViewById(R.id.tv_sendMsg);
        tv_deleteFriend = findViewById(R.id.tv_deleteFriend);
        tv_shareOther = findViewById(R.id.tv_shareOther);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (deleteFriendPresenter != null) {
            deleteFriendPresenter.detachView();
        }
    }

    public void show(IContact contact) {
        this.contact = contact;

        // 聊天
        tv_sendMsg.setOnClickListener(view -> {
            P2PSessionActivity.active(getActivity(), contact.getId());
            dismiss();
        });

        // 删除好友
        if (myself()) {
            tv_deleteFriend.setVisibility(View.GONE);
        } else {
            tv_deleteFriend.setVisibility(View.VISIBLE);
            tv_deleteFriend.setOnClickListener(view -> {
                doDelFriend(contact.getId(), getActivity(), view);
            });
        }

        // 分享别人
        tv_shareOther.setOnClickListener(view -> {
            ShareFriendActivity.start(getActivity(), contact.getId());
            dismiss();
        });
        this.show();
    }

    private boolean myself() {
        if (contact != null) {
            String uid = contact.getId();
            String currUid = String.valueOf(TioDBPreferences.getCurrUid());
            return currUid != null && currUid.equals(uid);
        }
        return false;
    }

    private void doDelFriend(String uid, Activity activity, View view) {
        if (deleteFriendPresenter == null) {
            deleteFriendPresenter = new DeleteFriendPresenter(this::getActivity);
        }
        dismiss();
        view.setEnabled(false);
        deleteFriendPresenter.start(Integer.parseInt(uid), false, new DeleteFriendContract.Presenter.DeleteFriendProxy() {
            @Override
            public void onSuccess(String data) {
                onDelFriendOk(contact);
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

    // 通知删除好友成功
    public void onDelFriendOk(IContact contact) {

    }
}
