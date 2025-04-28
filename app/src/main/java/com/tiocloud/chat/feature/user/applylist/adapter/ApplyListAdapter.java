package com.tiocloud.chat.feature.user.applylist.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.model.response.ApplyListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020-02-24
 * desc :
 */
public class ApplyListAdapter extends BaseQuickAdapter<ApplyListResp.Data, BaseViewHolder> {

    public ApplyListAdapter() {
        super(R.layout.tio_apply_list_item, null);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApplyListResp.Data item = getData().get(position);
                onClickItem(item, position);
            }
        });
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ApplyListResp.Data item = getData().get(position);
                if (item.status == 2) {
                    onClickAgreeBtn(item, position, view);
                }
            }
        });
    }

    protected void onClickItem(@NonNull ApplyListResp.Data item, int position) {

    }

    protected void onClickAgreeBtn(@NonNull ApplyListResp.Data item, int position, View view) {

    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListResp.Data item) {
        TioImageView hiv_avatar = helper.getView(R.id.hiv_avatar);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        TextView tv_agreeBtn = helper.getView(R.id.tv_agreeBtn);

        hiv_avatar.tio_roundAvatar(item.avatar);
        tv_name.setText(StringUtil.nonNull(item.nick));
        tv_subtitle.setText(StringUtil.nonNull(item.greet));
        // btn
        tv_agreeBtn.setEnabled(true);
        if (item.status == 2) {// 申请中
            tv_agreeBtn.setBackground(Utils.getApp().getResources().getDrawable(R.drawable.sel_add_friend_btn));
            tv_agreeBtn.setTextColor(Utils.getApp().getResources().getColorStateList(R.color.white));
            tv_agreeBtn.setText(R.string.agree);
        } else {// 申请通过
            tv_agreeBtn.setBackground(new ColorDrawable(Color.TRANSPARENT));
            tv_agreeBtn.setTextColor(Utils.getApp().getResources().getColor(R.color.gray_888888));
            tv_agreeBtn.setText(R.string.added);
        }
        helper.addOnClickListener(tv_agreeBtn.getId());
    }

    /**
     * 标记添加成功
     *
     * @param position
     */
    public void flagAgree(int position) {
        getData().get(position).status = 1;
        notifyItemChanged(position + getHeaderLayoutCount());
    }
}
