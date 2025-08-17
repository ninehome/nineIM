package com.watayouxiang.wallet.feature.redpacket.feature.send.adapter;

import android.view.View;
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
public class SendListAdapter extends BaseMultiItemQuickAdapter<SendModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SendListAdapter(List<SendModel> data) {
        super(data);
        addItemType(SendModel.ITEM_TYPE_PACKET, R.layout.wallet_redpacket_send_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SendModel item) {
        int itemType = item.getItemType();
        if (itemType == SendModel.ITEM_TYPE_PACKET) {
            PacketItem packetItem = item.getPacketItem();
            convertPacket(helper, packetItem);
        }
    }

    private void convertPacket(BaseViewHolder helper, PacketItem item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.getTitleTxt());

        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        tv_subtitle.setText(item.getSubtitleTxt());

        TextView tv_moneyInfo = helper.getView(R.id.tv_moneyInfo);
        tv_moneyInfo.setText(item.getMoneyInfoTxt());

        TextView tv_amountInfo = helper.getView(R.id.tv_amountInfo);
        tv_amountInfo.setText(item.getAmountInfoTxt());

        TextView tv_timeout = helper.getView(R.id.tv_timeout);
        tv_timeout.setVisibility(item.isTimeoutRed() ? View.VISIBLE : View.GONE);
    }

}
