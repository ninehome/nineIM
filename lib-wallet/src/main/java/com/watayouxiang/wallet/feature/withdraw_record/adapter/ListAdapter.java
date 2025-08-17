package com.watayouxiang.wallet.feature.withdraw_record.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.httpclient.model.vo.WithholdStatus;
import com.watayouxiang.wallet.R;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class ListAdapter extends BaseMultiItemQuickAdapter<ListModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ListAdapter(List<ListModel> data) {
        super(data);
        addItemType(ListModel.ITEM_TYPE_RED, R.layout.wallet_withdraw_record_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListModel item) {
        int itemType = item.getItemType();
        if (itemType == ListModel.ITEM_TYPE_RED) {
            NormalItem normalItem = item.getNormalItem();
            convertRedItem(helper, normalItem);
        }
    }

    @SuppressLint("SetTextI18n")
    private void convertRedItem(BaseViewHolder helper, NormalItem item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        TextView tv_moneyInfo = helper.getView(R.id.tv_moneyInfo);
        TextView tv_status = helper.getView(R.id.tv_status);

        tv_title.setText(item.getTitleTxt());
        tv_subtitle.setText(item.getSubtitleTxt());

        // 金额
        tv_moneyInfo.setText(item.getMoney());
        if (WithholdStatus.isSuccess(item.getStatus())) {
            tv_moneyInfo.setTextColor(Color.parseColor("#4C94FF"));
        } else {
            tv_moneyInfo.setTextColor(Color.parseColor("#999999"));
        }

        // 状态
        if (WithholdStatus.isSuccess(item.getStatus())) {
            tv_status.setVisibility(View.GONE);
        } else {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText(StringUtils.null2Length0(WithholdStatus.getDesc(item.getStatus())));
        }
    }

}
