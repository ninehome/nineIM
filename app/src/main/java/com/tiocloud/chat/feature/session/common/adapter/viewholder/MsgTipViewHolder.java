package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioP2PErrorMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.mvp.addfriend.AddFriendContract;
import com.tiocloud.chat.util.TimeUtil;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;

import java.util.Date;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : Tip类型消息
 */
public class MsgTipViewHolder extends MsgBaseViewHolder {
    private TextView notificationTextView;

    public MsgTipViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.message_item_notification;
    }

    @Override
    protected void inflateContent() {
        notificationTextView = findViewById(R.id.message_item_notification_label);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        TioMsg message = getMessage();
        if (message instanceof TioP2PErrorMsg) {
            TioP2PErrorMsg tioP2PErrorMsg = (TioP2PErrorMsg) message;
            handleTioP2PErrorMsg(tioP2PErrorMsg);
        } else {
            handleTioMsg(message);
//            holder.itemView.setVisibility(View.GONE);
        }
        checkBox.setVisibility(View.GONE);
    }

    private void handleTioP2PErrorMsg(TioP2PErrorMsg msg) {
        WxFriendErrorNtf ntf = msg.getWxFriendErrorNtf();
        if (ntf.getCode() == WxFriendErrorNtf.Code.NO_LINK) {
            SpanUtils.with(notificationTextView)
                    .append(getContext().getString(R.string.you_not_his_friend))
                    .setForegroundColor(Color.parseColor("#FF909090"))
                    .append(getContext().getString(R.string.send_friend_check))
                    .setClickSpan(Color.parseColor("#4C94E8"), true, v -> showAddFriendDialog(msg.getToUid()))
                    .create();
        } else {
            SpanUtils.with(notificationTextView)
                    .append(ntf.msg)
                    .setForegroundColor(Color.parseColor("#FF909090"))
                    .create();
        }
    }

    private void showAddFriendDialog(String _toUid) {
        int toUid = -1;
        try {
            toUid = Integer.parseInt(_toUid);
        } catch (Exception ignored) {
        }
        if (toUid == -1) {
            TioToast.showShort("好友id转换失败");
            return;
        }
        getAdapter().getAddFriendPresenter().uncheckStart(toUid, new AddFriendContract.Presenter.AddFriendProxy() {
            @Override
            public void onAddFriendResp(AddFriendResp data) {
                TioToast.showShort(getContext().getString(R.string.friend_add_success));
            }

            @Override
            public void onFriendApplyResp(FriendApplyResp data) {
                super.onFriendApplyResp(data);
                TioToast.showShort(getContext().getString(R.string.friend_apply_success));
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.showShort(msg);
            }
        }, getActivity());
    }

    private void handleTioMsg(TioMsg msg) {
        String content = msg.getContent();
        if (TextUtils.isEmpty(content)) {
            content = getContext().getResources().getString(R.string.unknown_notification);
        }

        if (TimeUtil.isTimestamp(content)) { //消息时间戳
            try {
                Long l = Long.parseLong(content);
                if (DateUtils.isToday(l)){
                    content = TimeUtils.date2String(new Date(l), "HH:mm");
                }else {
                    content = TimeUtils.date2String(new Date(l), "yyyy-MM-dd HH:mm");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        SpanUtils.with(notificationTextView)
                .append(content)
                .setForegroundColor(Color.parseColor("#FF909090"))
                .create();
    }

    @Override
    protected View.OnLongClickListener onContentLongClick() {
        return null;
    }
}