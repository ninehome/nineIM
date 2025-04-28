package com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
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
public class ReceiveListAdapter extends BaseMultiItemQuickAdapter<ReceiveModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ReceiveListAdapter(List<ReceiveModel> data) {
        super(data);
        addItemType(ReceiveModel.ITEM_TYPE_PACKET, R.layout.wallet_redpacket_receive_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveModel item) {
        int itemType = item.getItemType();
        if (itemType == ReceiveModel.ITEM_TYPE_PACKET) {
            PacketItem packetItem = item.getPacketItem();
            convertPacket(helper, packetItem);
        }
    }

    private void convertPacket(BaseViewHolder helper, PacketItem item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.getTitleTxt());

        if (item.isPin()) {
            Drawable drawable = ResourceUtils.getDrawable(R.drawable.wallet_ic_pin_14);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_title.setCompoundDrawables(null, null, drawable, null);
        } else {
            tv_title.setCompoundDrawables(null, null, null, null);
        }

        TextView tv_subtitle = helper.getView(R.id.tv_subtitle);
        tv_subtitle.setText(item.getSubtitleTxt());

        TextView tv_moneyInfo = helper.getView(R.id.tv_moneyInfo);
        tv_moneyInfo.setText(item.getMoneyInfoTxt());

        TioImageView iv_avatar = helper.getView(R.id.iv_avatar);
        iv_avatar.loadStatic(item.getAvatarUrl());
    }

}
