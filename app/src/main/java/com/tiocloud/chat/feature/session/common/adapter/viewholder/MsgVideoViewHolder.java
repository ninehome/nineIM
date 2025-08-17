package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.player.VideoPlayerActivity;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class MsgVideoViewHolder extends MsgBaseViewHolder {

    private TioImageView msgImageView;
    private String mVideoUrl;

    public MsgVideoViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_video;
    }

    @Override
    protected void inflateContent() {
        msgImageView = findViewById(R.id.msgImageView);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        InnerMsgVideo data = (InnerMsgVideo) getMessage().getContentObj();
        if (data == null) return;
        mVideoUrl = data.url;
        msgImageView.load_msg_pic(data.coverurl, data.width, data.height);


    }

    @Override
    protected void onContentClick(View view) {
        if (mVideoUrl != null) {
            VideoPlayerActivity.start(view.getContext(), HttpCache.getResUrl(mVideoUrl));
        }
    }
}
