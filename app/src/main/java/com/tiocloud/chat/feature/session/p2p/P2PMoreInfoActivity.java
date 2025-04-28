package com.tiocloud.chat.feature.session.p2p;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.group.create.CreateGroupActivity;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.converter.ChatListTableConverter;
import com.watayouxiang.db.dao.ChatListTableCrud;
import com.watayouxiang.db.table.ChatListTable;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.OperReq;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;
import com.watayouxiang.imclient.engine.TioEventEngine;
import com.watayouxiang.imclient.event.ClearChatMsg;
import com.watayouxiang.imclient.event.NoticeClearBean;

import java.util.ArrayList;

public class P2PMoreInfoActivity extends TioActivity {
    UserInfoResp userInfoResp = null;

    public static void start(Context context, String uid, String chatLinkId, int topFlag) {
        Intent starter = new Intent(context, P2PMoreInfoActivity.class);
        starter.putExtra("uid", uid);
        starter.putExtra("chatlinkId", chatLinkId);
        starter.putExtra("topFlag", topFlag);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this);
//        BarUtils.setStatusBarLightMode(this, true);
        setContentView(R.layout.activity_p2p_more_info);
        TioImageView tioImageView = findViewById(R.id.hiv_avatar);
        TextView textView = findViewById(R.id.tv_name);
        String uid = getIntent().getStringExtra("uid");
        String chatlinkid = getIntent().getStringExtra("chatlinkId");
        int topFlag = getIntent().getIntExtra("topFlag", 0);

        CheckBox switchMaterial = findViewById(R.id.switch_toggleInviteMember);
        switchMaterial.setChecked(topFlag == 1);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                postTopChatOper(b?1:0, chatlinkid);
            }
        });
        CheckBox switchNotrub = findViewById(R.id.switch_notrub);
        switchNotrub.setChecked(TioConfig.isNoDisturb(1, true, uid));
        switchNotrub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TioConfig.saveNoDisturbBizId(1, uid, b);
                TioConfig.saveNoDisturbChatLinkId(1, chatlinkid, b);
            }
        });

        UserInfoReq req = new UserInfoReq(uid);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<UserInfoResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserInfoResp>> response) {
                userInfoResp = response.body().getData();
                if (userInfoResp != null) {
                    tioImageView.load(userInfoResp.avatar);
                    textView.setText(StringUtils.isEmpty(userInfoResp.remarkname)?userInfoResp.nick:userInfoResp.remarkname);
                } else {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });

        findViewById(R.id.ll_groupIntro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EasyOperDialog.Builder(getString(R.string.confirm_report_user))
                        .setPositiveBtnTxt(getString(R.string.confirm))
                        .setNegativeBtnTxt(getString(R.string.cancel))
                        .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                            @Override
                            public void onClickPositive(View view, EasyOperDialog dialog) {
                                requestReport(chatlinkid);
                                dialog.dismiss();
                            }

                            @Override
                            public void onClickNegative(View view, EasyOperDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show_unCancel(P2PMoreInfoActivity.this);
            }
        });


        findViewById(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list =  new ArrayList<>();
                list.add(uid);
                CreateGroupActivity.start(getActivity(), list);
            }
        });

        tioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendDetail(uid);
            }
        });

        findViewById(R.id.rl_deleteChatRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EasyOperDialog.Builder(getString(R.string.confirm_clear_chat_history))
                        .setPositiveBtnTxt(getString(R.string.confirm))
                        .setNegativeBtnTxt(getString(R.string.cancel))
                        .setPosBtnRedRoundBg()
                        .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                            @Override
                            public void onClickPositive(View view, final EasyOperDialog dialog) {
                                dialog.dismiss();
                                OperReq req = OperReq.deleteChatRecord(chatlinkid);
                                TioHttpClient.post(this, req, new TaoCallback<BaseResp<String>>() {
                                    @Override
                                    public void onSuccess(Response<BaseResp<String>> resp) {
                                        BaseResp<String> body = resp.body();
                                        if (body.isOk()) {
                                            ToastUtils.showShort(getString(R.string.clear_success));
                                            ChatListTable table = ChatListTableCrud.query(chatlinkid);
                                            if (table != null) {
                                                // 如果存在，则更新
                                                table.setMsgresume("");
                                                table.setAtnotreadcount(0);
                                                table.setNotreadcount(0);
                                                ChatListTableCrud.update(table);
                                            }
                                            new TioEventEngine().post(new ClearChatMsg(chatlinkid));
                                        } else {
                                            ToastUtils.showShort(body.getMsg());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onClickNegative(View view, EasyOperDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show_unCancel(getActivity());



            }
        });
    }

    // 举报用户
    private void requestReport(String chatlinkid) {
        OperReq complaint = OperReq.complaint(chatlinkid);
        complaint.setCancelTag(this);
        complaint.get(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                TioToast.showShort(getString(R.string.report_user_success));
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    // 置顶会话请求
    private void postTopChatOper(int topflag, String chatlinkid) {
        OperReq operReq = OperReq.topChat(chatlinkid, topflag == 1);
        operReq.setCancelTag(this);
        operReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                Log.e("zlb", "OperReq==>"+s);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void friendDetail(String uid){
        UserDetailActivity.start(this, uid);
    }
}
