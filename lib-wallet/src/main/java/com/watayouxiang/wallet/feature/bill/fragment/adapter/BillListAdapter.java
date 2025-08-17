package com.watayouxiang.wallet.feature.bill.fragment.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.wallet.R;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class BillListAdapter extends BaseMultiItemQuickAdapter<BillModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BillListAdapter(List<BillModel> data) {
        super(data);
        addItemType(BillModel.ITEM_TYPE_NORMAL, R.layout.wallet_bill_list_item_red);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillModel item) {
        int itemType = item.getItemType();
        if (itemType == BillModel.ITEM_TYPE_NORMAL) {
            BillItem billItem = item.getBillItem();
            convertRedItem(helper, billItem);
        }
    }

    private void convertRedItem(BaseViewHolder helper, BillItem item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        TextView tv_rightTitle = helper.getView(R.id.tv_rightTitle);
        TextView tv_rightSubtitle = helper.getView(R.id.tv_rightSubtitle);

        tv_title.setText(item.getTitle());
        tv_subtitle.setText(item.getSubtitle());
        tv_rightTitle.setText(item.getRightTitle());
        tv_rightTitle.setTextColor(Color.parseColor(item.getRightTitleTextColor()));
        tv_rightSubtitle.setText(item.getRightSubtitle());
    }

}
