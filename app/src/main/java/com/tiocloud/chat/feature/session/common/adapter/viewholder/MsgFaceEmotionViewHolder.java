package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.widget.emotion.emoji.EmojiView;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFaceEmotion;
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
public class MsgFaceEmotionViewHolder extends MsgBaseViewHolder {

    private TioImageView msgImageView;
    private String faceEmotion;
    private Integer type;
    private int[] wh;

    public MsgFaceEmotionViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_face_emotion;
    }

    @Override
    protected void inflateContent() {
        msgImageView = findViewById(R.id.msgImageView);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        InnerMsgFaceEmotion data = (InnerMsgFaceEmotion) getMessage().getContentObj();
        if (data == null) return;
        faceEmotion = data.emotion;
//        msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
        type = data.type;

        if (type.intValue() == 0){
            int resId = EmojiView.Gifs.textMapId(faceEmotion);
            int[] wh = getWH(resId);
            msgImageView.setWH(wh[0], wh[1], 1.3f, SizeUtils.dp2px(100));
            // 设置边框
            msgImageView.setBorder(Color.WHITE, 1);
            msgImageView.load(resId, true);
        }else {
            wh = new int[]{data.width, data.height};
            int dw, dh;
            dw = data.width==0?SizeUtils.dp2px(80):data.width;
            dh = data.height==0?SizeUtils.dp2px(70):data.height;
            if (TioConfig.OpenCloseConfig.limitChatPicHeight() && dh > ScreenUtils.getScreenHeight()/4){
                int screenHeight = ScreenUtils.getScreenHeight();
                dw = (screenHeight/4)*dw/dh;
                dh = (screenHeight/4);
            }
            msgImageView.setWH(dw, dh, 1.0f, SizeUtils.dp2px(160));
            // 设置边框
//            msgImageView.setBorder(Color.WHITE, 1);
            msgImageView.load(faceEmotion, true);
        }
    }

    private int[] getWH(int resId){

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //只请求图片宽高，不解析图片像素(请求图片属性但不申请内存，解析bitmap对象，该对象不占内存)
        opts.inJustDecodeBounds = true;
        //String path = Environment.getExternalStorageDirectory() + "/dog.jpg";
        BitmapFactory.decodeResource(getContext().getResources(), resId, opts);
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;
        return new int[]{imageWidth, imageHeight};
    }

    @Override
    protected void onContentClick(View view) {
        openWatchImageActivity(view);
    }

//    @Override
//    protected View.OnClickListener onContentClick() {
//        return this::openWatchImageActivity;
//    }

    /**
     * 消息图片预览 - 增加左右滑动预览功能
     */
    private void openWatchImageActivity(View view) {
        if (type == 0){
            return;
        }
//        List<TioMsg> data = getAdapter().getData();
//        int size;
//        if (data == null || (size = data.size()) == 0) return;
//
//        // 获取所有 "图片消息"
//        ArrayList<TioMsg> imgMsgs = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            TioMsg msg = data.get(i);
//            if (msg.getMsgType() == TioMsgType.image) {
//                Object contentObj = msg.getContentObj();
//                if (contentObj instanceof InnerMsgImage) {
//                    imgMsgs.add(msg);
//                }
//            }
//        }
//
//        // 获取 "图片集合"
//        // 获取当前 "图片" 所处的位置
//        ArrayList<String> imgUrls = new ArrayList<>(imgMsgs.size());
//        int imgUrlsIndex = 0;
//        int currImgPosition = 0;
//        for (int i = 0, imgMsgSize = imgMsgs.size(); i < imgMsgSize; i++) {
//            TioMsg msg = imgMsgs.get(i);
//            if (msg.getId() != null && msg.getId().equals(getMessage().getId())) {
//                currImgPosition = imgUrlsIndex;
//            }
//            InnerMsgImage imgMsg = (InnerMsgImage) msg.getContentObj();
//            imgUrls.add(imgUrlsIndex++, imgMsg.url);
//        }

        // 显示图片
        if (faceEmotion != null) {
            TioImageBrowser.getInstance().showPic(view, 0, new String[]{faceEmotion});
        }
    }

    @Override
    public boolean isCollect() {
        return type == 1;
    }

    @Override
    public String getPicUrl() {
        return faceEmotion;
    }

    @Override
    public int[] getPicWH() {
        return wh;
    }
}
