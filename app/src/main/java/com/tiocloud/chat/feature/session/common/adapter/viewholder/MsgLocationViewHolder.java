package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioP2PErrorMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.mvp.addfriend.AddFriendContract;
import com.tiocloud.chat.util.TioImageBrowser;
import com.tiocloud.chat.yanxun.group.apply.InviteVerifyActivity;
import com.tiocloud.chat.yanxun.map.MapActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.httpclient.model.response.AddFriendResp;
import com.watayouxiang.httpclient.model.response.FriendApplyResp;
import com.watayouxiang.imclient.model.body.wx.WxFriendErrorNtf;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgGroupApply;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : Tip类型消息
 */
public class MsgLocationViewHolder extends MsgBaseViewHolder {
    private TioImageView msgImageView;
    private TextView textView;
    private String mOriginalImgUrl;

    private InnerMsgLocation data;

    public MsgLocationViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_location;
    }

    @Override
    protected void inflateContent() {
        msgImageView = findViewById(R.id.msgImageView);
        textView = findViewById(R.id.msg_address);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        data = (InnerMsgLocation) getMessage().getContentObj();
        if (data == null) return;
        try {
            msgImageView.load(data.url);
            textView.setText(data.address);
        }catch (Exception e){
            e.printStackTrace();
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
        if (data == null){
            return;
        }
        String lat = data.lat;
        String lng = data.lng;
        String address = data.address;
        double mLatitude, mLongitude;
        try {
            mLatitude = Double.parseDouble(lat);
            mLongitude = Double.parseDouble(lng);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        if (mLatitude != 0 && mLongitude != 0) {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.putExtra("latitude", mLatitude);
            intent.putExtra("longitude", mLongitude);
            intent.putExtra("address", address);
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), getContext().getString(R.string.location_info_no_exist), Toast.LENGTH_SHORT).show();
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
//
//        // 显示图片
//        if (mOriginalImgUrl != null) {
//            TioImageBrowser.getInstance().showPic(view, currImgPosition, imgUrls.toArray(new String[]{}));
//        }
    }
}
