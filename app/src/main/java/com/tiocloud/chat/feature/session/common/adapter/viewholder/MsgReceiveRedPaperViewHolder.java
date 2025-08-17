package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/10
 *     desc   :
 * </pre>
 */
public class MsgReceiveRedPaperViewHolder extends MsgBaseViewHolder {

    private TextView tv_txt;

    public MsgReceiveRedPaperViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.wallet_redpaper_msg_receive;
    }

    @Override
    protected void inflateContent() {
        tv_txt = findViewById(R.id.tv_txt);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        SpanUtils.with(tv_txt)
                .append(getContext().getString(R.string.xiaosheng))
                .setForegroundColor(Color.parseColor("#FFB7B7B7"))
                .append(getContext().getString(R.string.red_package))
                .setForegroundColor(Color.parseColor("#FF5E5E"))
                .create();
    }

}
