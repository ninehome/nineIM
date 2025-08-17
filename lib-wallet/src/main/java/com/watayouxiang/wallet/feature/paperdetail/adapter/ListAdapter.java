package com.watayouxiang.wallet.feature.paperdetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;

import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/16
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
        addItemType(ListModel.ITEM_LIST, R.layout.wallet_paperdetail_list_item);
        addItemType(ListModel.ITEM_SEND_INFO, R.layout.wallet_paperdetail_sendinfo_item);
        addItemType(ListModel.ITEM_FOOTER, R.layout.wallet_paperdetail_footer_item);
//        addItemType(ListModel.ITEM_RECEIVE_INFO, R.layout.wallet_paperdetail_receiveinfo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListModel item) {
        int itemType = item.getItemType();
        if (itemType == ListModel.ITEM_LIST) {
            ListItem packetItem = item.getListItem();
            convertList(helper, packetItem);
        } else if (itemType == ListModel.ITEM_SEND_INFO) {
            SendInfoItem sendInfoItem = item.getSendInfoItem();
            convertSendInfo(helper, sendInfoItem);
        } else if (itemType == ListModel.ITEM_FOOTER) {
            FooterItem footerItem = item.getFooterItem();
            convertFooter(helper, footerItem);
        } /*else if (itemType == ListModel.ITEM_RECEIVE_INFO) {
            ReceiveInfoItem receiveInfoItem = item.getReceiveInfoItem();
            convertReceiveInfo(helper, receiveInfoItem);
        }*/
    }

    private void convertReceiveInfo(BaseViewHolder helper, ReceiveInfoItem item) {
        TextView tv_money = helper.getView(R.id.tv_money);
        tv_money.setText(item.getInfo());

        TextView tv_tipInfo = helper.getView(R.id.tv_tipInfo);
        tv_tipInfo.setOnClickListener(view -> {
            if (ClickUtils.isViewSingleClick(view)) {
                Context context = view.getContext();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    WalletActivity.start(activity);
                }
            }
        });
    }

    private void convertFooter(BaseViewHolder helper, FooterItem item) {
        TextView tv_info = helper.getView(R.id.tv_info);
        tv_info.setText(item.getInfo());
    }

    private void convertSendInfo(BaseViewHolder helper, SendInfoItem item) {
        TextView tv_info = helper.getView(R.id.tv_info);
        tv_info.setText(item.getInfo());
    }

    private void convertList(BaseViewHolder helper, ListItem item) {
        TioImageView iv_avatar = helper.getView(R.id.iv_avatar);
        iv_avatar.loadStatic(item.getAvatar());

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());

        TextView tv_moneyInfo = helper.getView(R.id.tv_moneyInfo);
        tv_moneyInfo.setText(item.getMoneyInfo());

        TextView tv_time = helper.getView(R.id.tv_time);
        tv_time.setText(item.getTimeInfo());

        TextView tv_bestLucky = helper.getView(R.id.tv_bestLucky);
        tv_bestLucky.setVisibility(item.isBestLucky() ? View.VISIBLE : View.GONE);
    }

}
