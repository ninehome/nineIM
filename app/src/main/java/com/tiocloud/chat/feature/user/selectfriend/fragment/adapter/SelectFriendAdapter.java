package com.tiocloud.chat.feature.user.selectfriend.fragment.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.model.MultiModel;
import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.model.MultiType;
import com.tiocloud.chat.feature.user.selectfriend.fragment.adapter.model.SectionModel;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectFriendAdapter extends BaseMultiItemQuickAdapter<MultiModel, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private String keyWord;

    public SelectFriendAdapter() {
        super(null);
        addItemType(MultiType.SECTION.value, R.layout.tio_select_friend_item_section);
        addItemType(MultiType.CONTACT.value, R.layout.tio_select_friend_item);

        setOnItemClickListener(this);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected void convert(BaseViewHolder helper, MultiModel item) {
        switch (item.type) {
            case CONTACT:
                convertContact(helper, item.contact);
                break;
            case SECTION:
                convertSection(helper, item.section);
                break;
        }
    }

    private void convertSection(BaseViewHolder helper, SectionModel section) {
        helper.setText(R.id.tv_title, StringUtil.nonNull(section.title));
    }

    private void convertContact(BaseViewHolder helper, MailListResp.Friend item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);

        // 头像
        hiv_avatar.tio_roundAvatar(item.avatar);
        // title
        tv_name.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF4C94E8"),
                !TextUtils.isEmpty(item.remarkname) ? item.remarkname : StringUtil.nonNull(item.nick),
                keyWord));
        // 昵称
        tv_subtitle.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF4C94E8"),
                TextUtils.isEmpty(item.remarkname) ? "" : (mContext.getString(R.string.nick)+": " + item.nick),
                keyWord));
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    // ====================================================================================
    // 点击事件
    // ====================================================================================

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultiModel multiModel = getData().get(position);
        if (multiModel.type == MultiType.CONTACT) {
            MailListResp.Friend friend = multiModel.contact;
            onClickContactItem(friend);
        }
    }

    protected void onClickContactItem(MailListResp.Friend friend) {

    }

}
