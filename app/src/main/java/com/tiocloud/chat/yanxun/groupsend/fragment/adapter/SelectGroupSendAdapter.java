package com.tiocloud.chat.yanxun.groupsend.fragment.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.KeywordUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.model.MultiModel;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.model.MultiType;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.model.SectionModel;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.LinkedList;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectGroupSendAdapter extends BaseMultiItemQuickAdapter<MultiModel, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private LinkedList<String> checkedIds = new LinkedList<>();
    private LinkedList<String> checkedNames = new LinkedList<>();
    private String keyWord;

    public SelectGroupSendAdapter() {
        super(null);
        addItemType(MultiType.SECTION.value, R.layout.tio_create_group_item_section);
        addItemType(MultiType.CONTACT.value, R.layout.tio_group_send_item);

        checkedIds.clear();
        checkedNames.clear();
        setOnItemClickListener(this);
    }

    public void clearCheck(){
        checkedIds.clear();
        checkedNames.clear();
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    protected void convert(BaseViewHolder helper, MultiModel item) {
        switch (item.type) {
            case CONTACT:
                convertContact(helper, item.contact, item.isHideDivider);
                break;
            case SECTION:
                convertSection(helper, item.section);
                break;
        }
    }

    private void convertSection(BaseViewHolder helper, SectionModel section) {
        helper.setText(R.id.tv_title, StringUtil.nonNull(section.title));
    }

    private void convertContact(BaseViewHolder helper, MailListResp.Friend item, boolean isHideDivider) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        CheckBox checkBox = helper.getView(R.id.checkBox);
        View v_divider = helper.getView(R.id.v_divider);

        // 头像
        hiv_avatar.tio_roundAvatar(item.avatar);
        // title
        tv_name.setText(KeywordUtil.matcherSearchTitle(
                Color.parseColor("#FF4C94E8"),
                !TextUtils.isEmpty(item.remarkname) ? item.remarkname : StringUtil.nonNull(item.nick),
                keyWord));
        tv_name.setVisibility(TextUtils.isEmpty(tv_name.getText()) ? View.GONE : View.VISIBLE);
//        // 昵称
//        tv_subtitle.setText(KeywordUtil.matcherSearchTitle(
//                Color.parseColor("#FF4C94E8"),
//                TextUtils.isEmpty(item.remarkname) ? "" : ("昵称: " + item.nick),
//                keyWord));
//        tv_subtitle.setVisibility(TextUtils.isEmpty(tv_subtitle.getText()) ? View.GONE : View.VISIBLE);
        // 单选
        checkBox.setClickable(false);
        checkBox.setChecked(checkedIds.contains(String.valueOf(item.uid)));
        // 下划线显隐
        v_divider.setVisibility(isHideDivider ? View.GONE : View.VISIBLE);
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    // ====================================================================================
    // 点击事件
    // ====================================================================================

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.d("zlb", "position===>"+position);
        MultiModel multiModel = getData().get(position);
        if (multiModel.type == MultiType.CONTACT) {
            MailListResp.Friend friend = multiModel.contact;

            String uid = String.valueOf(friend.uid);
            if (!checkedIds.remove(uid)) {
                checkedIds.add(uid);
            }
            if (!checkedNames.remove(friend.nick)) {
                checkedNames.add(friend.nick);
            }
//            notifyItemChanged(position);
            notifyDataSetChanged();
            onCheckedItemChange(checkedIds);
        }
    }

    protected void onCheckedItemChange(LinkedList<String> checkedIds) {

    }

    public void addCheckedIds(String id) {
        this.checkedIds.add(id);
    }

    public void addCheckedNames(String name) {
        this.checkedNames.add(name);
    }

    public LinkedList<String> getCheckedIds() {
        return checkedIds;
    }

    public LinkedList<String> getCheckedNames() {
        return checkedNames;
    }


}
