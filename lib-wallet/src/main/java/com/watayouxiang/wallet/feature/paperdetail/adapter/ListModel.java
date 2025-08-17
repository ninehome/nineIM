package com.watayouxiang.wallet.feature.paperdetail.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/16
 *     desc   :
 * </pre>
 */
public class ListModel implements MultiItemEntity {
    public static final int ITEM_LIST = 1;
    public static final int ITEM_SEND_INFO = 2;
    public static final int ITEM_FOOTER = 3;
    public static final int ITEM_RECEIVE_INFO = 4;

    private final int itemType;
    private ListItem listItem;
    private SendInfoItem sendInfoItem;
    private FooterItem footerItem;
    private ReceiveInfoItem receiveInfoItem;

    public ListModel(ReceiveInfoItem receiveInfoItem) {
        this.itemType = ITEM_RECEIVE_INFO;
        this.receiveInfoItem = receiveInfoItem;
    }

    public ListModel(FooterItem footerItem) {
        this.itemType = ITEM_FOOTER;
        this.footerItem = footerItem;
    }

    public ListModel(ListItem listItem) {
        this.itemType = ITEM_LIST;
        this.listItem = listItem;
    }

    public ListModel(SendInfoItem sendInfoItem) {
        this.itemType = ITEM_SEND_INFO;
        this.sendInfoItem = sendInfoItem;
    }

    public ListItem getListItem() {
        return listItem;
    }

    public SendInfoItem getSendInfoItem() {
        return sendInfoItem;
    }

    public void setSendInfoItem(SendInfoItem sendInfoItem) {
        this.sendInfoItem = sendInfoItem;
    }

    public FooterItem getFooterItem() {
        return footerItem;
    }

    public ReceiveInfoItem getReceiveInfoItem() {
        return receiveInfoItem;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
