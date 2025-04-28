package com.tiocloud.chat.feature.search.curr.group;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class GroupListAdapter extends BaseQuickAdapter<MailListResp.Group, BaseViewHolder> {

    private final String keyWord;

    public GroupListAdapter(Context context,  @Nullable List<MailListResp.Group> data, String keyWord) {
        super(R.layout.tio_search_group_item2, data);
        this.keyWord = keyWord;
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_result_textview, null);
        ((TextView)inflate.findViewById(R.id.textview)).setText(mContext.getString(R.string.group_zu));
        addHeaderView(inflate);
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.gray_666666));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(mContext.getString(R.string.showed_all_search_result));
        addFooterView(textView);
    }

    @Override
    protected void convert(BaseViewHolder helper, MailListResp.Group item) {
        setItemData(helper, item, keyWord);
    }

    public static void setItemData(BaseViewHolder helper, MailListResp.Group item, String keyWord) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);

        hiv_avatar.tio_roundAvatar(item.avatar);

        tv_name.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF06D89A"),
                StringUtil.nonNull(item.name),
                keyWord));

        tv_subtitle.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF06D89A"),
                item.joinnum+ Utils.getApp().getString(R.string.ren),
                keyWord));
    }
}
