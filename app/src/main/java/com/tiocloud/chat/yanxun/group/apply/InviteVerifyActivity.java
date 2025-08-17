package com.tiocloud.chat.yanxun.group.apply;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.groupsend.adapter.CommonAdapter;
import com.tiocloud.chat.yanxun.groupsend.adapter.CommonViewHolder;
import com.tiocloud.chat.yanxun.view.MyGridView;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.DealApplyReq;
import com.watayouxiang.httpclient.model.request.DealGroupApplyReq;
import com.watayouxiang.httpclient.model.request.GroupApplyInfoReq;
import com.watayouxiang.httpclient.model.response.GroupApplyInfoResp;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgGroupApply;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 群聊邀请验证页面
 */
public class InviteVerifyActivity extends TioActivity {
    String text, isInvite, reason;
    String id;
    private String mLoginUserId;
    // 邀请者发送的邀请消息，所有的内容都在里面
//    private InnerMsgGroupApply message;
    private String friendId;
    private String packet;
    private String mRoomId;
    private TioImageView mInviteAvatarIv;
    private TextView mInveiteNameTv;
    private TextView mInveiteCountTv;
    private TextView mInveitReasonTv;
    private MyGridView mVerifyGridView;
    private VerifyAdapter mVerifyAdapter;
    private List<GroupApplyInfoResp.GroupApplyItem> mInviteList;
    private TextView mSureTv;
    private Integer applyId;
    private Integer msgId;
    GroupApplyInfoResp groupApplyInfoResp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_verify);
        mLoginUserId = String.valueOf(TioDBPreferences.getCurrUid());
        msgId = getIntent().getIntExtra("msgId", 0);
        applyId = getIntent().getIntExtra("applyId", 0);
        if (applyId == 0 || msgId == 0){
            ToastUtils.showShort(getString(R.string.wuxiaocahnshu));
            finish();
            return;
        }
        isInvite = "0";
//        if (getIntent() != null) {
//            friendId = getIntent().getStringExtra("VERIFY_MESSAGE_FRIEND_ID");
//            packet = getIntent().getStringExtra("VERIFY_MESSAGE_PACKET");
//            mRoomId = getIntent().getStringExtra("VERIFY_MESSAGE_ROOM_ID");
//            if (!TextUtils.isEmpty(packet)) {
//                message = ChatMessageDao.getInstance().findMsgById(mLoginUserId, friendId, packet);
//            }
//            if (TextUtils.isEmpty(packet) || message == null) {
//                Toast.makeText(this, R.string.tip_get_detail_error, Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
        initAction();
        initData();

    }

    private void initAction() {
//        findViewById(R.id.iv_title_left).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        TextView tvTitle = (TextView) findViewById(R.id.tv_title_center);
//        tvTitle.setText(R.string.invite_detail);
    }

    private void initData() {
        GroupApplyInfoReq req = new GroupApplyInfoReq(String.valueOf(applyId));
        req.setCancelTag(this);
        req.get(new TioCallback<GroupApplyInfoResp>() {
            @Override
            public void onTioSuccess(GroupApplyInfoResp groupApplyInfoResp) {
                InviteVerifyActivity.this.groupApplyInfoResp = groupApplyInfoResp;
                initView();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
                finish();
            }
        });


        mInviteList = new ArrayList<>();
        List<String> lIst = new ArrayList<>();
//        try {
//            org.json.JSONObject jsonObject = new org.json.JSONObject(message.getObjectId());
//            id = jsonObject.getString("userIds");
//            String name = jsonObject.getString("userNames");
//            String[] ids = id.split(",");
//            String[] names = name.split(",");
//            for (int i = 0; i < ids.length; i++) {
//                Invite invite = new Invite();
//                invite.setInvitedId(ids[i]);
//                invite.setInvitedName(names[i]);
//                mInviteList.add(invite);
//                lIst.add(ids[i]);
//            }
//
//            text = JSON.toJSONString(lIst);
//            isInvite = jsonObject.getString("isInvite");
//            if (TextUtils.isEmpty(isInvite)) {
//                isInvite = "0";
//            }
//            reason = jsonObject.getString("reason");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void initView() {
        mInviteAvatarIv = (TioImageView) findViewById(R.id.invite_iv);
        mInveiteNameTv = (TextView) findViewById(R.id.invite_name);
        mInveiteCountTv = (TextView) findViewById(R.id.invite_number);
        mInveitReasonTv = (TextView) findViewById(R.id.invite_reasonr);

//        AvatarHelper.getInstance().displayAvatar(message.getFromUserName(), message.getFromUserId(), mInviteAvatarIv, false);
        GroupApplyInfoResp.GroupApply groupApply = groupApplyInfoResp.apply;
        mInviteList = groupApplyInfoResp.items;
        mInviteAvatarIv.load(groupApply.groupavator);

        mInveiteNameTv.setText(groupApply.srcnick);
        if (isInvite.equals("0")) {
            mInveiteCountTv.setText(getString(R.string.tip_invite_count_place_holder, groupApplyInfoResp.items.size()));
        } else {
            mInveiteCountTv.setText(groupApply.srcnick + getString(R.string.wanna_in));
        }
        mInveitReasonTv.setText("");
        mVerifyGridView = findViewById(R.id.verify_gd);
        mVerifyAdapter = new VerifyAdapter(this, mInviteList);
        mVerifyGridView.setAdapter(mVerifyAdapter);
        mSureTv = (TextView) findViewById(R.id.sure_tv);
        if (groupApply.status == 1) {// 已确认
            mSureTv.setText(R.string.has_confirm);
            mSureTv.setBackgroundResource(R.drawable.bg_verify_sure_grey);
        } else if (groupApply.status == 3){
            mSureTv.setText("已驳回");
            mSureTv.setBackgroundResource(R.drawable.bg_verify_sure_grey);
        }else {// 待确认
            mSureTv.setBackgroundResource(R.drawable.bg_verify_sure);
            mSureTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inviteFriend();
                }
            });
        }
    }

    /**
     * 邀请好友
     */
    private void inviteFriend() {

        DealGroupApplyReq req = new DealGroupApplyReq(String.valueOf(applyId), String.valueOf(msgId));
        req.setCancelTag(this);
        req.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });


//        Map<String, String> params = new HashMap<>();
//        params.put("access_token", coreManager.getSelfStatus().accessToken);
//        params.put("roomId", mRoomId);
//        params.put("text", text);
//        DialogHelper.showDefaulteMessageProgressDialog(this);
//
//        HttpUtils.get().url(coreManager.getConfig().ROOM_MEMBER_UPDATE)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<Void>(Void.class) {
//
//                    @Override
//                    public void onResponse(ObjectResult<Void> result) {
//                        DialogHelper.dismissProgressDialog();
//                        if (Result.checkSuccess(mContext, result)) {
//                            mSureTv.setBackgroundResource(R.drawable.bg_verify_sure_grey);
//                            mSureTv.setText(R.string.has_confirm);
//
//                            List<ChatMessage> mNeedUpdateVerifyMessage = new ArrayList<>();
//                            mNeedUpdateVerifyMessage.add(message);
//
//                            // 因为该用户可能请求了很多次，其余请求消息也需要更新
//                            List<ChatMessage> verifyMessages = ChatMessageDao.getInstance().getAllVerifyMessage(mLoginUserId, friendId, message.getFromUserId());
//                            for (int i = 0; i < verifyMessages.size(); i++) {
//                                ChatMessage verifyMessage = verifyMessages.get(i);
//                                if (!TextUtils.isEmpty(verifyMessage.getObjectId())
//                                        && verifyMessage.getObjectId().contains("isInvite")
//                                        && verifyMessage.getObjectId().contains("reason")) {// 基本可以确定为群聊邀请确认
//                                    try {
//                                        org.json.JSONObject jsonObject = new org.json.JSONObject(verifyMessage.getObjectId());
//                                        String isInvite2 = jsonObject.getString("isInvite");
//                                        if (TextUtils.isEmpty(isInvite2)) {
//                                            isInvite2 = "0";
//                                        }
//                                        String id2 = jsonObject.getString("userIds");
//                                        if (isInvite2.equals(isInvite)) {
//                                            if (isInvite.equals("0")) {
//                                                if (!TextUtils.isEmpty(id2)
//                                                        && id.equals(id2)
//                                                        && !verifyMessage.isDownload()) {// 基本可以确定为同一条
//                                                    mNeedUpdateVerifyMessage.add(verifyMessage);
//                                                }
//                                            } else {
//                                                if (!verifyMessage.isDownload()) {// 未确认
//                                                    mNeedUpdateVerifyMessage.add(verifyMessage);
//                                                }
//                                            }
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            for (int i = 0; i < mNeedUpdateVerifyMessage.size(); i++) {// 更新消息内容 与 确认状态
//                                String str = message.getContent().replace(getString(R.string.to_confirm), getString(R.string.has_confirm));
//                                ChatMessageDao.getInstance().updateMessageContent(mLoginUserId, friendId, mNeedUpdateVerifyMessage.get(i).getPacketId(), str);
//                                ChatMessageDao.getInstance().updateGroupVerifyMessageStatus(mLoginUserId, friendId, mNeedUpdateVerifyMessage.get(i).getPacketId(), true);
//                            }
//                            setResult(Activity.RESULT_OK, getIntent());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        DialogHelper.dismissProgressDialog();
//                        Toast.makeText(com.sk.weichat.ui.message.multi.InviteVerifyActivity.this, getString(R.string.net_exception), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    class VerifyAdapter extends CommonAdapter<GroupApplyInfoResp.GroupApplyItem> {

        VerifyAdapter(Context context, List<GroupApplyInfoResp.GroupApplyItem> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommonViewHolder viewHolder = CommonViewHolder.get(mContext, convertView, parent,
                    R.layout.item_verify, position);
            TioImageView mAvatarIv = viewHolder.getView(R.id.verify_iv);
            TextView mNameTv = viewHolder.getView(R.id.verify_tv);
            mAvatarIv.load(data.get(position).avatar);
//            AvatarHelper.getInstance().displayAvatar(data.get(position).getInvitedName(), data.get(position).getInvitedId(), mAvatarIv, false);
            mNameTv.setText(data.get(position).nick);
            return viewHolder.getConvertView();
        }
    }

    class Invite {
        private String invitedId;
        private String invitedName;

        public String getInvitedId() {
            return invitedId;
        }

        public void setInvitedId(String invitedId) {
            this.invitedId = invitedId;
        }

        public String getInvitedName() {
            return invitedName;
        }

        public void setInvitedName(String invitedName) {
            this.invitedName = invitedName;
        }
    }
}
