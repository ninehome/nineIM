package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.text.Html;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.main.fragment.Nav1Fragment;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.HistoryP2PMsg;
import com.tiocloud.chat.feature.share.msg.HistoryMsgActivity;
import com.tiocloud.chat.util.MoonUtil;
import com.watayouxiang.httpclient.utils.JsonUtils;
import com.watayouxiang.imclient.engine.JsonEngine;
import com.watayouxiang.imclient.model.body.wx.TransHistoryMsgResp;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgHistory;

import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 文本类型消息
 */
public class MsgTransHistoryViewHolder extends MsgBaseViewHolder {
    private TextView tv_title;
    private TextView message1;
    private TextView message2;
    private TextView message3;
    private TransHistoryMsgResp data;

    public MsgTransHistoryViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.multi_message_history;
    }

    @Override
    protected void inflateContent() {
        tv_title = findViewById(R.id.tv_title);
        message1 = findViewById(R.id.message1);
        message2 = findViewById(R.id.message2);
        message3 = findViewById(R.id.message3);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {

        TioMsg tioMsg = getMessage();
        Object o = tioMsg.getContentObj();
        String json = (String) getMessage().getContentObj();
        data = new Gson().fromJson(json,TransHistoryMsgResp.class);
        tv_title.setText(data.getTitle());
        if(data.getContent().size() == 1){
            message1.setText(data.getContent().get(0).getNick()+": "+data.getContent().get(0).getText());
            message2.setVisibility(View.GONE);
            message3.setVisibility(View.GONE);
        }else if(data.getContent().size() == 2){
            message1.setText(data.getContent().get(0).getNick()+": "+data.getContent().get(0).getText());
            message2.setText(data.getContent().get(1).getNick()+": "+data.getContent().get(1).getText());
            message3.setVisibility(View.GONE);
        }else if(data.getContent().size() == 3){
            message1.setText(data.getContent().get(0).getNick()+": "+data.getContent().get(0).getText());
            message2.setText(data.getContent().get(1).getNick()+": "+data.getContent().get(1).getText());
            message3.setText(data.getContent().get(2).getNick()+": "+data.getContent().get(2).getText());
        }
    }


    @Override
    protected void onContentClick(View view) {
        HistoryMsgActivity.start(getContext(),data.getFromChatMode(),data.getMsgIds(),data.getTitle());
    }


    @Override
    protected boolean isShowContentBg() {
        return true;
    }
}
