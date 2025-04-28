package com.watayouxiang.wallet.feature.withdraw_record.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.watayouxiang.httpclient.model.response.PayWithholdListResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class ListModel implements MultiItemEntity {
    public static final int ITEM_TYPE_RED = 1;

    private final int itemType;
    private final NormalItem normalItem;
    private final PayWithholdListResp.ListBean original;

    public ListModel(NormalItem normalItem, PayWithholdListResp.ListBean original) {
        this.itemType = ITEM_TYPE_RED;
        this.normalItem = normalItem;
        this.original = original;
    }

    public PayWithholdListResp.ListBean getOriginal() {
        return original;
    }

    public NormalItem getNormalItem() {
        return normalItem;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
