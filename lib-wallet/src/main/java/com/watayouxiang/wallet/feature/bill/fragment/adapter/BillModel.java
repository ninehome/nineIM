package com.watayouxiang.wallet.feature.bill.fragment.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class BillModel implements MultiItemEntity {
    public static final int ITEM_TYPE_NORMAL = 1;

    private final int itemType;
    private final BillItem billItem;
    private final PayGetWalletItemsResp.ListBean original;

    public BillModel(BillItem billItem, PayGetWalletItemsResp.ListBean original) {
        this.itemType = ITEM_TYPE_NORMAL;
        this.billItem = billItem;
        this.original = original;
    }

    public BillItem getBillItem() {
        return billItem;
    }

    public PayGetWalletItemsResp.ListBean getOriginal() {
        return original;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
