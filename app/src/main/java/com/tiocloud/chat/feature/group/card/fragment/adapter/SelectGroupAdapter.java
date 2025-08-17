package com.tiocloud.chat.feature.group.card.fragment.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.card.fragment.adapter.model.MultiModel;
import com.tiocloud.chat.feature.group.card.fragment.adapter.model.MultiType;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectGroupAdapter extends BaseMultiItemQuickAdapter<MultiModel, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private String keyWord;
    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());

    public SelectGroupAdapter() {
        super(null);
        addItemType(MultiType.GROUP.value, R.layout.tio_select_group_item);
        setOnItemClickListener(this);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected void convert(BaseViewHolder helper, MultiModel item) {
        switch (item.type) {
            case GROUP:
                convertGroup(helper, item.group);
                break;
        }
    }

    private void convertGroup(BaseViewHolder helper, MailListResp.Group item) {
        TioImageView avatar = helper.getView(R.id.hiv_avatar);
        TextView name = helper.getView(R.id.tv_name);
        TextView tv_memberNum = helper.getView(R.id.tv_memberNum);
        ImageView iv_mgrIcon = helper.getView(R.id.iv_mgrIcon);

        // 群头像
        avatar.tio_roundAvatar(item.avatar);
        // 群名
        name.setText(KeywordUtil.matcherSearchTitle(
                Utils.getApp().getResources().getColor(R.color.blue_4c94ff),
                StringUtil.nonNull(item.name),
                keyWord));
        // 群人数
        tv_memberNum.setText(item.joinnum+Utils.getApp().getString(R.string.ren));
        // 群主标志
        boolean isGroupOwner = Integer.parseInt(currUid) == item.uid;
        iv_mgrIcon.setVisibility(isGroupOwner ? View.VISIBLE : View.GONE);
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
        if (multiModel.type == MultiType.GROUP) {
            MailListResp.Group group = multiModel.group;
            onClickGroupItem(group);
        }
    }

    protected void onClickGroupItem(MailListResp.Group group) {

    }

}
