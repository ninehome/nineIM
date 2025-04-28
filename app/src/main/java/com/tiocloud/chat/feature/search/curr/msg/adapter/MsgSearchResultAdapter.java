package com.tiocloud.chat.feature.search.curr.msg.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.feature.session.group.fragment.msg.HistoryGroupMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.HistoryP2PMsg;
import com.tiocloud.chat.feature.session.p2p.fragment.msg.P2PMsg;
import com.tiocloud.chat.util.KeywordUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.table.GroupMsgTable;

import java.util.List;

public class MsgSearchResultAdapter extends BaseQuickAdapter<GroupMsgTable, BaseViewHolder> {
    String keyWord;

    public MsgSearchResultAdapter(@Nullable List<GroupMsgTable> data, Context context, View.OnClickListener toChatClickListnner, String avatar, String chatName, String keyWord) {
        super(R.layout.tio_search_msg_item2, data);
        this.keyWord = keyWord;

        View inflate1 = LayoutInflater.from(context).inflate(R.layout.tio_search_msg_item3, null);
        TioImageView tioImageView = inflate1.findViewById(R.id.hiv_avatar);
        TextView textView = inflate1.findViewById(R.id.tv_name);
        tioImageView.tio_roundAvatar(avatar);
        textView.setText(chatName);
        addHeaderView(inflate1);

        inflate1.setOnClickListener(toChatClickListnner);

        View inflate2 = LayoutInflater.from(context).inflate(R.layout.item_result_textview, null);
        ((TextView)inflate2.findViewById(R.id.textview)).setText(
                String.format(mContext.getString(R.string.gongstiaoxgjilu), data.size()));
        addHeaderView(inflate2);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupMsgTable item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TioMsg tioMsg = null;
        if (item.getChatMode() == 1){
            //p2p
            if (item.getSourceType() == 1){
                tioMsg = new Gson().fromJson(item.getMsgEntity(), HistoryP2PMsg.class);
            }else if (item.getSourceType() == 2){
                tioMsg = new Gson().fromJson(item.getMsgEntity(), P2PMsg.class);
            }
        }else {
            if (item.getSourceType() == 1){
                tioMsg = new Gson().fromJson(item.getMsgEntity(), HistoryGroupMsg.class);
            }else if (item.getSourceType() == 2){
                tioMsg = new Gson().fromJson(item.getMsgEntity(), GroupMsg.class);
            }
        }
        if (tioMsg == null){
            return;
        }
        String avatar = tioMsg.getAvatar();
        hiv_avatar.tio_roundAvatar(avatar);

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(tioMsg.getName());

        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        tv_subtitle.setText(KeywordUtil.matcherSearchTitle(Color.parseColor("#FF06D89A"), item.getContent(), keyWord));
        tv_subtitle.setVisibility(View.VISIBLE);
    }
}
