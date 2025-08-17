package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.tiocloud.chat.util.TioImageBrowser;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class MsgPictureViewHolder extends MsgBaseViewHolder {

    private TioImageView msgImageView;
    private String mOriginalImgUrl;
    private InnerMsgImage data;

    public MsgPictureViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_picture;
    }

    @Override
    protected void inflateContent() {
        msgImageView = findViewById(R.id.msgImageView);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        data = (InnerMsgImage) getMessage().getContentObj();
        if (data == null) return;
        mOriginalImgUrl = data.url;
        if (TioConfig.OpenCloseConfig.limitChatPicHeight()){
            int screenHeight = ScreenUtils.getScreenHeight();
            if (data.height > screenHeight/4){
                msgImageView.load_msg_pic_limit(data.coverurl, (screenHeight/4)*data.width/data.height, (screenHeight/4));
            }else {
                msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
            }
        }else {
            msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
        }
    }

    @Override
    protected void onContentClick(View view) {
        openWatchImageActivity(view);
    }

    /**
     * 消息图片预览 - 增加左右滑动预览功能
     */
    private void openWatchImageActivity(View view) {
        List<TioMsg> data = getAdapter().getData();
        int size;
        if (data == null || (size = data.size()) == 0) return;

        // 获取所有 "图片消息"
        ArrayList<TioMsg> imgMsgs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TioMsg msg = data.get(i);
            if (msg.getMsgType() == TioMsgType.image) {
                Object contentObj = msg.getContentObj();
                if (contentObj instanceof InnerMsgImage) {
                    imgMsgs.add(msg);
                }
            }
        }

        // 获取 "图片集合"
        // 获取当前 "图片" 所处的位置
        ArrayList<String> imgUrls = new ArrayList<>(imgMsgs.size());
        int imgUrlsIndex = 0;
        int currImgPosition = 0;
        for (int i = 0, imgMsgSize = imgMsgs.size(); i < imgMsgSize; i++) {
            TioMsg msg = imgMsgs.get(i);
            if (msg.getId() != null && msg.getId().equals(getMessage().getId())) {
                currImgPosition = imgUrlsIndex;
            }
            InnerMsgImage imgMsg = (InnerMsgImage) msg.getContentObj();
            imgUrls.add(imgUrlsIndex++, imgMsg.url);
        }

        // 显示图片
        if (mOriginalImgUrl != null) {
            TioImageBrowser.getInstance().showPic(view, currImgPosition, imgUrls.toArray(new String[]{}));
        }
    }

    @Override
    public int[] getPicWH() {
        return new int[]{data.width, data.height};
    }

    @Override
    public String getPicUrl() {
        return data.url;
    }
}
