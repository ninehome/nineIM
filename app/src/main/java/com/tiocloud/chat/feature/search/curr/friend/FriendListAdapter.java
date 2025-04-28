package com.tiocloud.chat.feature.search.curr.friend;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.util.KeywordUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class FriendListAdapter extends BaseQuickAdapter<MailListResp.Friend, BaseViewHolder> {

    @LayoutRes
    public static int layoutResId = R.layout.tio_search_friend_item2;
    private final String keyWord;

    public FriendListAdapter(Context context,  @Nullable List<MailListResp.Friend> data, String keyWord) {
        super(layoutResId, data);
        this.keyWord = keyWord;
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_result_textview, null);
        ((TextView)inflate.findViewById(R.id.textview)).setText("好友");
        addHeaderView(inflate);
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.gray_666666));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(mContext.getString(R.string.showed_all_search_result));
        addFooterView(textView);
    }

    @Override
    protected void convert(BaseViewHolder helper, MailListResp.Friend item) {
        setItemData(mContext,helper, item, keyWord);
    }

    public static void setItemData(Context context,BaseViewHolder helper, MailListResp.Friend item, String keyWord) {
        if (keyWord == null) keyWord = "";

        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        hiv_avatar.tio_roundAvatar(item.avatar);

        // 标题、副标题
        /// 获取文案
        String title = null;
        String subtitle = null;
        if (!TextUtils.isEmpty(item.remarkname) && !TextUtils.isEmpty(item.nick)) {
            title = item.remarkname;
            subtitle = item.nick;
        } else if (!TextUtils.isEmpty(item.nick)) {
            title = item.nick;
        }
        title = title + "\t\t\t\t"+context.getString(R.string.link_number)+item.uid;
        /// 设置文案
        tv_name.setText(title != null
                ? KeywordUtil.matcherSearchTitle(Color.parseColor("#FF06D89A"), title, keyWord)
                : "");
        tv_subtitle.setText(subtitle != null
                ? KeywordUtil.matcherSearchTitle(Color.parseColor("#FF06D89A"),
                context.getString(R.string.nick)+": " + subtitle, keyWord)
                : "");
        // 设置可见性
        tv_name.setVisibility(TextUtils.isEmpty(tv_name.getText()) ? View.GONE : View.VISIBLE);
        tv_subtitle.setVisibility(TextUtils.isEmpty(tv_subtitle.getText()) ? View.GONE : View.VISIBLE);
    }
}
