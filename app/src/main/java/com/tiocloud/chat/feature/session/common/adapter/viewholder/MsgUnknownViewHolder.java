package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 未知消息
 */
public class MsgUnknownViewHolder extends MsgBaseViewHolder {

    private TextView unSupportDesc;

    public MsgUnknownViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.message_item_unknown;
    }

    @Override
    protected void inflateContent() {
        unSupportDesc = findViewById(R.id.tv_known_msg);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {

    }

    @Override
    protected boolean isShowContentBg() {
        return true;
    }
}
