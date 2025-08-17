package com.tiocloud.chat.feature.home.group.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.widget.header.NumCountHeader;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-01-23
 * desc :
 */
public class GroupAdapter extends BaseQuickAdapter<MailListResp.Group, BaseViewHolder> {

    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());

    @Override
    public void setNewData(@Nullable List<MailListResp.Group> data) {
        super.setNewData(data);
    }

    public GroupAdapter(RecyclerView recyclerView) {
        super(R.layout.tio_group_list_item);
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(this);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.divider_bg));
//        recyclerView.addItemDecoration(dividerItemDecoration);

        // 添加列头
        NumCountHeader header = new NumCountHeader(recyclerView.getContext());
        addFooterView(header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MailListResp.Group item) {
        TioImageView avatar = helper.getView(R.id.hiv_avatar);
        TextView name = helper.getView(R.id.tv_name);
        TextView tv_memberNum = helper.getView(R.id.tv_memberNum);
        ImageView iv_mgrIcon = helper.getView(R.id.iv_mgrIcon);

        avatar.tio_roundAvatar(item.avatar);
        name.setText(StringUtil.nonNull(item.name));
        tv_memberNum.setText(item.joinnum+ Utils.getApp().getString(R.string.ren));


        // 群主标志
        boolean isGroupOwner = Integer.parseInt(currUid) == item.uid;
        iv_mgrIcon.setVisibility(isGroupOwner ? View.VISIBLE : View.GONE);


    }

    // 设置群组个数
    public void setGroupSize(int size) {
        LinearLayout headerLayout = getFooterLayout();
        int childCount = headerLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = headerLayout.getChildAt(i);
            if (child instanceof NumCountHeader) {
                NumCountHeader header = (NumCountHeader) child;
                header.setCenterText(String.format(Locale.getDefault(), mContext.getString(R.string.d_group_chat), size));
            }
        }
    }
}
