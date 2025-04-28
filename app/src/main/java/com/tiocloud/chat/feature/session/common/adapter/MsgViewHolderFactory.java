package com.tiocloud.chat.feature.session.common.adapter;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgAudioViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgCallViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgCardViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgFaceEmotionViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgFileViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgGroupApplyViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgLocationViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgPictureViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgReceiveRedPaperViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgRedPaperViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgTextViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgTipViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgTransAmountViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgTransHistoryViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgUnknownViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgVideoViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;

import java.util.HashMap;

public class MsgViewHolderFactory {
    private static final HashMap<TioMsgType, Class<? extends MsgBaseViewHolder>> mViewHolders = new HashMap<>();

    static {
        mViewHolders.put(TioMsgType.unknown, MsgUnknownViewHolder.class);
        mViewHolders.put(TioMsgType.text, MsgTextViewHolder.class);
        mViewHolders.put(TioMsgType.tip, MsgTipViewHolder.class);
        mViewHolders.put(TioMsgType.image, MsgPictureViewHolder.class);
        mViewHolders.put(TioMsgType.video, MsgVideoViewHolder.class);
        mViewHolders.put(TioMsgType.file, MsgFileViewHolder.class);
        mViewHolders.put(TioMsgType.card, MsgCardViewHolder.class);
        mViewHolders.put(TioMsgType.call, MsgCallViewHolder.class);
        mViewHolders.put(TioMsgType.audio, MsgAudioViewHolder.class);
        mViewHolders.put(TioMsgType.redPaper, MsgRedPaperViewHolder.class);
        mViewHolders.put(TioMsgType.receiveRedPaper, MsgReceiveRedPaperViewHolder.class);
        mViewHolders.put(TioMsgType.groupApply, MsgGroupApplyViewHolder.class);
        mViewHolders.put(TioMsgType.location, MsgLocationViewHolder.class);
        mViewHolders.put(TioMsgType.faceEmotion, MsgFaceEmotionViewHolder.class);
        mViewHolders.put(TioMsgType.transAmount, MsgTransAmountViewHolder.class);
        mViewHolders.put(TioMsgType.transMessage, MsgTransHistoryViewHolder.class);
    }

    public static int getLayoutResId() {
        return R.layout.message_item;
    }

    public static HashMap<TioMsgType, Class<? extends MsgBaseViewHolder>> getViewHolders() {
        return mViewHolders;
    }

    public static int getViewType(TioMsg message) {
        TioMsgType msgType = message.getMsgType();
        if (msgType == null) {
            msgType = TioMsgType.unknown;
        }
        Class<? extends MsgBaseViewHolder> viewHolder = mViewHolders.get(msgType);
        if (viewHolder == null) {
            msgType = TioMsgType.unknown;
        }
        return msgType.getValue();
    }

}
