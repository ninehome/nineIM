package com.tiocloud.chat.feature.search.curr.msg;

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
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public class MsgListAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    @LayoutRes
    public static int layoutResId = R.layout.tio_search_msg_item2;
    private final String keyWord;

    public MsgListAdapter(Context context, @Nullable List<Map<String, Object>> data, String keyWord) {
        super(layoutResId, data);
        this.keyWord = keyWord;
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_result_textview, null);
        ((TextView)inflate.findViewById(R.id.textview)).setText("聊天记录");
        addHeaderView(inflate);
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.gray_666666));
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText("已展示所有搜索结果");
        addFooterView(textView);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        setItemData(mContext,helper, item, keyWord);
    }

    public static void setItemData(Context context,BaseViewHolder helper, Map<String, Object> item, String keyWord) {
        if (keyWord == null) keyWord = "";

        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        hiv_avatar.tio_roundAvatar(StringUtil.nonNull(item.get("avatar")));
        // 标题、副标题
        /// 获取文案
        /// 设置文案
        tv_name.setText(StringUtil.nonNull(item.get("name")));
        tv_subtitle.setText(item.get("count")+context.getString(R.string.tiaoxgjilu));
        // 设置可见性
        tv_name.setVisibility(TextUtils.isEmpty(tv_name.getText()) ? View.GONE : View.VISIBLE);
        tv_subtitle.setVisibility(TextUtils.isEmpty(tv_subtitle.getText()) ? View.GONE : View.VISIBLE);
    }
}
