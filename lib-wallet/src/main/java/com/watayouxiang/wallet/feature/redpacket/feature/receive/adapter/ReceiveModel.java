package com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class ReceiveModel implements MultiItemEntity {
    public static final int ITEM_TYPE_PACKET = 1;

    private final int itemType;
    private final PacketItem packetItem;

    public ReceiveModel(PacketItem packetItem) {
        this.itemType = ITEM_TYPE_PACKET;
        this.packetItem = packetItem;
    }

    public PacketItem getPacketItem() {
        return packetItem;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
