package com.tiocloud.chat.feature.share.msg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.chat.BuildConfig;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.player.VideoPlayerActivity;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.viewmodel.RedPaperViewModel;
import com.tiocloud.chat.feature.share.msg.mvp.ShareMsgContract;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.util.FileUtil;
import com.tiocloud.chat.util.MoonUtil;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.util.TioImageBrowser;
import com.tiocloud.chat.widget.emotion.emoji.EmojiView;
import com.tiocloud.chat.yanxun.map.MapActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.audiorecord.TioAudioBubbleUtils;
import com.watayouxiang.audiorecord.TioAudioPlayer;
import com.watayouxiang.db.dao.RedPacketCrud;
import com.watayouxiang.db.dao.TransAmountCrud;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.db.table.TransAmountStatusTable;
import com.watayouxiang.httpclient.callback.TioCallbackImpl;
import com.watayouxiang.httpclient.callback.TioFileCallback;
import com.watayouxiang.httpclient.model.MsgHistotyEntity;
import com.watayouxiang.httpclient.model.request.CheckCardJoinGroupReq;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.httpclient.utils.JsonUtils;
import com.watayouxiang.imclient.model.HangUpType;
import com.watayouxiang.imclient.model.body.wx.TransHistoryMsgResp;
import com.watayouxiang.imclient.model.body.wx.msg.FileIconType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgAudio;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFaceEmotion;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgLocation;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgTransAmount;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;
import com.watayouxiang.wallet.feature.paperdetail.PaperDetailActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;
import com.watayouxiang.wallet.widget.RedPaperDialog;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.huantansheng.easyphotos.constant.Type.VIDEO;
import static com.sina.weibo.sdk.constant.WBConstants.Msg.IMAGE;

public class HistoryMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TEXT = 1;;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_AUDIO = 3;
    public static final int TYPE_VIDEO = 4;
    public static final int TYPE_FILE = 5;
    public static final int TYPE_TIP = 6;
    public static final int TYPE_CARD = 8;
    public static final int TYPE_CALL = 9;
    public static final int TYPE_READPAPER = 10;
    public static final int TYPE_RECEIVE_READPAPER = 11;
    public static final int TYPE_LOCATION = 14;
    public static final int TYPE_FACE_EMOTION = 15;
    public static final int TYPE_TRANS_AMOUT = 16;
    public static final int TYPE_TRANS_MESSAGE = 17;
    public static final int TYPE_UNKONW = -1;

    private Context mContext;
    private List<MsgHistotyEntity> mDatas = new ArrayList<>();

    public HistoryMsgAdapter(Context context, List<MsgHistotyEntity> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setNewDatas(List<MsgHistotyEntity> datas){
        this.mDatas = datas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_history_msg,null,false);
        FrameLayout frameLayout = root.findViewById(R.id.content_fl);
        switch (viewType){
            case TYPE_TEXT:
                View.inflate(root.getContext(),R.layout.message_item_text,frameLayout);
                return new TextHolder(root);
            case TYPE_IMAGE:
                View.inflate(root.getContext(),R.layout.tio_msg_item_picture,frameLayout);
                return new ImageHolder(root);
            case TYPE_AUDIO:
                View.inflate(root.getContext(),R.layout.tio_msg_item_audio,frameLayout);
                return new AudioHolder(root);
            case TYPE_VIDEO:
                View.inflate(root.getContext(),R.layout.tio_msg_item_video,frameLayout);
                return new VideoHolder(root);
            case TYPE_FILE:
                View.inflate(root.getContext(),R.layout.tio_msg_item_file,frameLayout);
                return new FileHolder(root);
            case TYPE_CARD:
                View.inflate(root.getContext(),R.layout.tio_msg_item_card,frameLayout);
                return new CardHolder(root);
            case TYPE_TIP:
                View.inflate(root.getContext(),R.layout.message_item_notification,frameLayout);
                TipHolder tipHolder =  new TipHolder(root);
                root.setTag(tipHolder);
                return tipHolder;
            case TYPE_CALL:
                View.inflate(root.getContext(),R.layout.tio_msg_item_call,frameLayout);
                return new TipHolder(root);
            case TYPE_READPAPER:
                View.inflate(root.getContext(),R.layout.wallet_redpaper_msg,frameLayout);
                return new RedPaperHolder(root);
            case TYPE_RECEIVE_READPAPER:
                View.inflate(root.getContext(),R.layout.wallet_redpaper_msg_receive,frameLayout);
                return new ReceiveRedPaperHolder(root);
            case TYPE_LOCATION:
                View.inflate(root.getContext(),R.layout.tio_msg_item_location,frameLayout);
                return new LocationHolder(root);
            case TYPE_FACE_EMOTION:
                View.inflate(root.getContext(),R.layout.tio_msg_item_face_emotion,frameLayout);
                return new EmotionHolder(root);
            case TYPE_TRANS_AMOUT:
                View.inflate(root.getContext(),R.layout.trans_amount_msg,frameLayout);
                return new TransAmoutHolder(root);
            case TYPE_TRANS_MESSAGE:
                View.inflate(root.getContext(),R.layout.multi_message_history,frameLayout);
                return new TransMessageHolder(root);
            case TYPE_UNKONW:
                View.inflate(root.getContext(),R.layout.message_item_unknown,frameLayout);
                return new UnknownHolder(root);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgHistotyEntity msgHistotyEntity = mDatas.get(position);
        String json = msgHistotyEntity.getText();
        int viewType = getItemViewType(position);

        switch (viewType){
            case TYPE_TEXT:
                TextHolder textHolder = (TextHolder) holder;
                textHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                String content = msgHistotyEntity.getText();
                textHolder.bodyTextView.setGravity(Gravity.LEFT);
                if (TioConfig.OpenCloseConfig.isUliao() && content.startsWith("uliao://")){
                    textHolder.bodyTextView.setText(Html.fromHtml("<u>"+content+"</u>"));
                }else {
                    MoonUtil.identifyFaceExpression(textHolder.bodyTextView, content, ImageSpan.ALIGN_BOTTOM);
                }
                enterInfo(textHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_IMAGE:
                ImageHolder imageHolder = (ImageHolder) holder;
                InnerMsgImage data = new Gson().fromJson(json,InnerMsgImage.class);
                imageHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                if (data == null) return;
                String mOriginalImgUrl = data.url;
                if (TioConfig.OpenCloseConfig.limitChatPicHeight()){
                    int screenHeight = ScreenUtils.getScreenHeight();
                    if (data.height > screenHeight/4){
                        imageHolder.msgImageView.load_msg_pic_limit(data.coverurl, (screenHeight/4)*data.width/data.height, (screenHeight/4));
                    }else {
                        imageHolder.msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
                    }
                }else {
                    imageHolder.msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
                }
                enterInfo(imageHolder.tiv_avatar,msgHistotyEntity.getUid());
                imageHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TioImageBrowser.getInstance().showPic(v, data.url);
                    }
                });
                break;
            case TYPE_AUDIO:
                InnerMsgAudio innerMsgAudio = new Gson().fromJson(json,InnerMsgAudio.class);
                AudioHolder audioHolder = (AudioHolder) holder;
                audioHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                audioHolder.ll_right.setVisibility(View.GONE);
                audioHolder.ll_left.setVisibility(View.VISIBLE);
                int audioImageId = R.drawable.gif_voice_left;
                audioHolder.image_left.setBackgroundResource(audioImageId);

                AnimationDrawable animationDrawable =
                        (AnimationDrawable) audioHolder.image_left.getBackground();
                // 语音时长
                audioHolder.tv_left.setText(String.format(Locale.getDefault(), "%d''",
                        innerMsgAudio.seconds));
                // 气泡长度
                TioAudioBubbleUtils.setAudioBubbleWidth(audioHolder.container, innerMsgAudio.seconds);
                // 初始化播放器
                TioAudioPlayer.getInstance().init(new TioAudioPlayer.OnPlayerListener() {
                    @Override
                    public void onWtPlayerStart() {
                        animationDrawable.start();
                    }

                    @Override
                    public void onWtPlayerStop() {
                        animationDrawable.stop();
                        animationDrawable.selectDrawable(0);
                    }
                }, msgHistotyEntity.getId());

                enterInfo(audioHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;

            case TYPE_VIDEO:
                VideoHolder videoHolder = (VideoHolder) holder;
                InnerMsgVideo innerMsgVideo = new Gson().fromJson(json,InnerMsgVideo.class);
                videoHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                if (innerMsgVideo == null) return;
                String mVideoUrl = innerMsgVideo.url;
                videoHolder.msgImageView.load_msg_pic(innerMsgVideo.coverurl, innerMsgVideo.width, innerMsgVideo.height);
                videoHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mVideoUrl != null) {
                            VideoPlayerActivity.start(mContext, HttpCache.getResUrl(mVideoUrl));
                        }
                    }
                });
                enterInfo(videoHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_FILE:
                FileHolder fileHolder = (FileHolder) holder;
                InnerMsgFile innerMsgFile = new Gson().fromJson(json,InnerMsgFile.class);
                fileHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                if (innerMsgFile == null) {
                    fileHolder.tv_fileName.setText("");
                    fileHolder.tv_fileSize.setText("");
                    fileHolder.iv_fileIcon.setImageResource(R.drawable.tio_file_icon_other);
                    return;
                }
                fileHolder.tv_fileName.setText(StringUtil.nonNull(innerMsgFile.filename));
                fileHolder.tv_fileSize.setText(FileUtil.formatFileSize(innerMsgFile.size));
                fileHolder.iv_fileIcon.setImageResource(getFileIconId(innerMsgFile.fileicontype));
                fileHolder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDownloadDialog(mContext,innerMsgFile);
                    }
                });
                enterInfo(fileHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_CARD:
                CardHolder cardHolder = (CardHolder) holder;
                InnerMsgCard innerMsgCard = new Gson().fromJson(json,InnerMsgCard.class);
                if (innerMsgCard == null) return;
                cardHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                // 名片头像
                cardHolder.hiv_avatar.load_msg_card(innerMsgCard.bizavatar);
                // 名片类型
                if (innerMsgCard.cardtype == 1) {
                    cardHolder.tv_cardType.setText("个人名片");
                    cardHolder.iv_cardType.setImageResource(R.drawable.ic_user_card_type_session);
                } else if (innerMsgCard.cardtype == 2) {
                    cardHolder.tv_cardType.setText("群名片");
                    cardHolder.iv_cardType.setImageResource(R.drawable.ic_group_card_type_session);
                }
                // 名片名称
                cardHolder.tv_usrName.setText(StringUtil.nonNull(innerMsgCard.bizname));
                cardHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (innerMsgCard == null) return;
                        String bizid = innerMsgCard.bizid;
                        if (innerMsgCard.cardtype == 1) {// 个人名片
                            UserDetailActivity.start(mContext, bizid);
                        } else if (innerMsgCard.cardtype == 2) {// 群名片
                            new CheckCardJoinGroupReq(bizid, String.valueOf(innerMsgCard.shareFromUid)).setCancelTag(this).get(new TioCallbackImpl<Integer>() {
                                @Override
                                public void onTioSuccess(Integer integer) {
                                    if (integer == 1) {

                                    } else if (integer == 2) {
                                        // 未进群

                                    } else {
                                        // 未知状态
                                        TioToast.showShort("unknown resp: " + integer);

                                    }
                                }

                                @Override
                                public void onTioError(String msg) {
                                    TioToast.showShort(msg);
                                }
                            });
                        }
                    }
                });
                enterInfo(cardHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_CALL:
                CallHolder callHolder = (CallHolder) holder;
                InnerMsgCall innerMsgCall = new Gson().fromJson(json,InnerMsgCall.class);
                callHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                if (innerMsgCall == null) return;
                setTvDrawable(innerMsgCall,callHolder);
                setTvText(innerMsgCall,callHolder);
                enterInfo(callHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_TIP:
                TipHolder tipHolder = (TipHolder) holder;
                tipHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
//                tipHolder.notificationTextView.setText(msgHistotyEntity.getText());
                tipHolder.notificationTextView.setText("");
                enterInfo(tipHolder.tiv_avatar,msgHistotyEntity.getAvatar());
                break;
            case TYPE_READPAPER:
                RedPaperHolder redPaperHolder = (RedPaperHolder) holder;
                InnerMsgRed innerMsgRed = new Gson().fromJson(json,InnerMsgRed.class);
                if (innerMsgRed == null) return;
                redPaperHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (innerMsgRed == null) return;
                        if (!ClickUtils.isViewSingleClick(v)) return;
//                        TioMsg message = getMessage();
//                        boolean groupMsg = message.isGroupMsg();
//                        boolean p2PMsg = message.isP2PMsg();
//                        boolean sendMsg = message.isSendMsg();

//                        if (p2PMsg) {
//                            // 私聊
//                            if (sendMsg) {
//                                // 自己发的红包
//                                // 跳转红包详情页
//                                PaperDetailActivity.start(mContext, innerMsgRed.serialnumber);
//                            } else {
//                                // 别人发的红包
//                                // 查询红包状
////                                viewModel.getRedStatus(innerMsgRed.serialnumber, this, true);
//                            }
//                        } else if (groupMsg) {
//                            // 群聊
//                            // 查询红包状
////                            viewModel.getRedStatus(innerMsgRed.serialnumber, this, true);
//                        } else {
//                            TioToast.showShort("未知会话类型");
//                        }
                    }
                });
                initUI(redPaperHolder,innerMsgRed);
                break;
            case TYPE_RECEIVE_READPAPER:
                ReceiveRedPaperHolder receiveRedPaperHolder = (ReceiveRedPaperHolder) holder;
                SpanUtils.with(receiveRedPaperHolder.tv_txt)
                        .append(mContext.getString(R.string.xiaosheng))
                        .setForegroundColor(Color.parseColor("#FFB7B7B7"))
                        .append(mContext.getString(R.string.red_package))
                        .setForegroundColor(Color.parseColor("#FF5E5E"))
                        .create();
                break;
            case TYPE_LOCATION:
                LocationHolder locationHolder = (LocationHolder) holder;
                InnerMsgLocation innerMsgLocation = new Gson().fromJson(json,InnerMsgLocation.class);
                if (innerMsgLocation == null) return;
                try {
                    locationHolder.msgImageView.load(innerMsgLocation.url);
                    locationHolder.textView.setText(innerMsgLocation.address);
                }catch (Exception e){
                    e.printStackTrace();
                }
                locationHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWatchImageActivity(v,innerMsgLocation);
                    }
                });
                break;
            case TYPE_FACE_EMOTION:
                EmotionHolder emotionHolder = (EmotionHolder) holder;
                InnerMsgFaceEmotion innerMsgFaceEmotion = new Gson().fromJson(json,InnerMsgFaceEmotion.class);
                emotionHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                if (innerMsgFaceEmotion == null) return;
                String faceEmotion = innerMsgFaceEmotion.emotion;
//        msgImageView.load_msg_pic(data.coverurl, data.width, data.height);
                Integer type = innerMsgFaceEmotion.type;

                if (type.intValue() == 0){
                    int resId = EmojiView.Gifs.textMapId(faceEmotion);
                    int[] wh = getWH(resId);
                    emotionHolder.msgImageView.setWH(wh[0], wh[1], 1.3f, SizeUtils.dp2px(100));
                    // 设置边框
                    emotionHolder.msgImageView.setBorder(Color.WHITE, 1);
                    emotionHolder.msgImageView.load(resId, true);
                }else {
                    int[] wh = new int[]{innerMsgFaceEmotion.width, innerMsgFaceEmotion.height};
                    int dw, dh;
                    dw = innerMsgFaceEmotion.width==0?SizeUtils.dp2px(80):innerMsgFaceEmotion.width;
                    dh = innerMsgFaceEmotion.height==0?SizeUtils.dp2px(70):innerMsgFaceEmotion.height;
                    if (TioConfig.OpenCloseConfig.limitChatPicHeight() && dh > ScreenUtils.getScreenHeight()/4){
                        int screenHeight = ScreenUtils.getScreenHeight();
                        dw = (screenHeight/4)*dw/dh;
                        dh = (screenHeight/4);
                    }
                    emotionHolder.msgImageView.setWH(dw, dh, 1.0f, SizeUtils.dp2px(160));
                    // 设置边框
//            msgImageView.setBorder(Color.WHITE, 1);
                    emotionHolder.msgImageView.load(faceEmotion, true);
                }
                enterInfo(emotionHolder.tiv_avatar,msgHistotyEntity.getUid());
                break;
            case TYPE_TRANS_AMOUT:
                TransAmoutHolder transAmoutHolder = (TransAmoutHolder) holder;
                InnerMsgTransAmount innerMsgTransAmount = new Gson().fromJson(json,InnerMsgTransAmount.class);
                if (innerMsgTransAmount == null) return;
                transId = innerMsgTransAmount.id;

                if (innerMsgTransAmount.type != null){
                    transType = innerMsgTransAmount.type;
                }
                initUI(transAmoutHolder,innerMsgTransAmount);
                break;
            case TYPE_TRANS_MESSAGE:
                TransMessageHolder transMessageHolder = (TransMessageHolder) holder;
                TransHistoryMsgResp transHistoryMsgResp = new Gson().fromJson(json, TransHistoryMsgResp.class);
                transMessageHolder.tiv_avatar.load(msgHistotyEntity.getAvatar());
                transMessageHolder.tv_title.setText(transHistoryMsgResp.getTitle());
                if(transHistoryMsgResp.getContent().size() == 1){
                    transMessageHolder.message1.setText(transHistoryMsgResp.getContent().get(0).getNick()+": "+
                            transHistoryMsgResp.getContent().get(0).getText());
                    transMessageHolder.message2.setVisibility(View.GONE);
                    transMessageHolder.message3.setVisibility(View.GONE);
                }else if(transHistoryMsgResp.getContent().size() == 2){
                    transMessageHolder.message1.setText(transHistoryMsgResp.getContent().get(0).getNick()+": "+transHistoryMsgResp.getContent().get(0).getText());
                    transMessageHolder.message2.setText(transHistoryMsgResp.getContent().get(1).getNick()+": "+transHistoryMsgResp.getContent().get(1).getText());
                    transMessageHolder.message3.setVisibility(View.GONE);
                }else if(transHistoryMsgResp.getContent().size() == 3){
                    transMessageHolder.message1.setText(transHistoryMsgResp.getContent().get(0).getNick()+": "+transHistoryMsgResp.getContent().get(0).getText());
                    transMessageHolder.message2.setText(transHistoryMsgResp.getContent().get(1).getNick()+": "+transHistoryMsgResp.getContent().get(1).getText());
                    transMessageHolder.message3.setText(transHistoryMsgResp.getContent().get(2).getNick()+": "+transHistoryMsgResp.getContent().get(2).getText());
                }
                enterInfo(transMessageHolder.tiv_avatar,msgHistotyEntity.getUid());
                transMessageHolder.content_fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryMsgActivity.start(mContext,transHistoryMsgResp.getFromChatMode(),
                                transHistoryMsgResp.getMsgIds(),transHistoryMsgResp.getTitle());
                    }
                });

                break;
            case TYPE_UNKONW:

        }

    }

    private void initUI(RedPaperHolder redPaperHolder,InnerMsgRed innerMsgRed) {
        redPaperHolder.tvRemark.setText(mContext.getResources().getString(R.string.app_name)+
                mContext.getString(R.string.red_package));
        RedPacketStatusTable redPacketStatusTable = RedPacketCrud.queryByRedId(Long.parseLong(innerMsgRed.serialnumber));

        redPaperHolder.tv_title.setText(StringUtils.null2Length0(innerMsgRed.text));
//        String status = msgRed.status;
        String status = null;
        if (redPacketStatusTable == null){
            status = innerMsgRed.status;
        }else {
            status = redPacketStatusTable.getRedStatus();
        }

        if ("SUCCESS".equals(status)) {
            // 已抢完
            setRedStatus(2,redPaperHolder);
        } else if ("TIMEOUT".equals(status)) {
            // 24小时超时
            setRedStatus(3,redPaperHolder);
        } else if ("SEND".equals(status)) {
            // 抢红包中
            setRedStatus(4,redPaperHolder);
        } else {
            // 其他状态则认为是 "可抢红包"
            setRedStatus(4,redPaperHolder);
        }
    }

    private void setRedStatus(int status,RedPaperHolder redPaperHolder) {
        if (status == 1) {
            // 已领取
            redPaperHolder.cl_container.setSelected(true);
            redPaperHolder.tv_subtitle.setVisibility(View.VISIBLE);
            redPaperHolder.tv_subtitle.setText(mContext.getString(R.string.geted));
        } else if (status == 2) {
            // 已抢完
            redPaperHolder.cl_container.setSelected(true);
            redPaperHolder.tv_subtitle.setVisibility(View.VISIBLE);
            redPaperHolder.tv_subtitle.setText(mContext.getString(R.string.geted_finnish));
        } else if (status == 3) {
            // 24小时超时
            redPaperHolder.cl_container.setSelected(true);
            redPaperHolder.tv_subtitle.setVisibility(View.VISIBLE);
            redPaperHolder.tv_subtitle.setText(mContext.getString(R.string.overdated));
        } else if (status == 4) {
            // 抢红包中
            redPaperHolder.cl_container.setSelected(false);
            redPaperHolder.tv_subtitle.setVisibility(View.GONE);
        }
    }

    long transId;
    int transType;
    private void initUI(TransAmoutHolder transAmoutHolder,InnerMsgTransAmount transAmount) {
        transAmoutHolder.tvRemark.setText(mContext.getResources().getString(R.string.app_name) +
                mContext.getString(R.string.zhuanzhnag));
        TransAmountStatusTable transAmountStatusTable = TransAmountCrud.queryByRedId(transAmount.id);

        transAmoutHolder.tv_title.setText("￥" + MoneyUtils.fen2yuan(String.valueOf(transAmount.amount)));
//        tv_subtitle.setText(transAmount.remark);
        Integer status = null;
        if (transAmountStatusTable == null) {
            status = transAmount.status;
        } else {
            status = transAmountStatusTable.getTransStatus();
        }
        if (transAmount.isSendMsg) {
            if (transType == 1) {
                if (status == 1) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.you_send_a_zhuanzhang)
                            );
                } else if (status == 2) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.accepted));
                } else if (status == 4 || status == 5) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.backed));
                }
            } else if (transType == 2) {
                if (status == 2) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.yishoukuan));
                } else if (status == 4 || status == 5) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.yituihuan));
                }
            }
        } else {
            if (transType == 1) {
                if (status == 1) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.qingshoukuan));
                } else if (status == 2) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.accepted));
                } else if (status == 4 || status == 5) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.yibeituihuan));
                }
            } else if (transType == 2) {
                if (status == 2) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.yishoukuan));
                } else if (status == 4 || status == 5) {
                    transAmoutHolder.tv_subtitle.setText(mContext.getString(R.string.yibeituihuan));
                }
            }
        }
//        if (getMessage().isSendMsg()){
//            if (status == 1){
//                tv_subtitle.setText("你发起了一笔转账");
//            }else if (status == 2){
//                tv_subtitle.setText("已被接收");
//            }else if (status == 4 || status == 5){
//                tv_subtitle.setText("已退还");
//            }else {
//                tv_subtitle.setText("无效状态");
//            }
//        }
//        if (status == 1){
//            tv_subtitle.setText("待收款");
//        }else if (status == 2){
//            tv_subtitle.setText("已收款");
//        }else if (status == 4 || status == 5){
//            tv_subtitle.setText("已退还");
//        }else {
//            tv_subtitle.setText("无效状态");
//        }
        if (status == 1) {
            transAmoutHolder.cl_container.setSelected(false);
        } else {
            transAmoutHolder.cl_container.setSelected(true);
        }
    }

    private void openWatchImageActivity(View view,InnerMsgLocation data) {
        if (data == null) {
            return;
        }
        String lat = data.lat;
        String lng = data.lng;
        String address = data.address;
        double mLatitude, mLongitude;
        try {
            mLatitude = Double.parseDouble(lat);
            mLongitude = Double.parseDouble(lng);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (mLatitude != 0 && mLongitude != 0) {
            Intent intent = new Intent(mContext, MapActivity.class);
            intent.putExtra("latitude", mLatitude);
            intent.putExtra("longitude", mLongitude);
            intent.putExtra("address", address);
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.location_info_no_exist), Toast.LENGTH_SHORT).show();
        }
    }

    private void enterInfo(View view,String uid){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailActivity.start(mContext, uid,false, false);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
//        return Integer.parseInt(mDatas.get(position).getCt());
        MsgHistotyEntity msgHistotyEntity = mDatas.get(position);
        if ("1".equals(msgHistotyEntity.getSendbysys())) {
            return TioMsgType.tip.getValue();
        }
        InnerMsgType contentType = InnerMsgType.valueOf(Integer.parseInt(msgHistotyEntity.getContenttype()));
        if (contentType == null) {
            return TioMsgType.unknown.getValue();
        }
        switch (contentType) {
            case VIDEO:
                return TioMsgType.video.getValue();
            case IMAGE:
                return TioMsgType.image.getValue();
            case AUDIO:
                return TioMsgType.audio.getValue();
            case TEXT:
                return TioMsgType.text.getValue();
            case FILE:
                return TioMsgType.file.getValue();
            case CARD:
                return TioMsgType.card.getValue();
            case BLOG:
                return TioMsgType.blog.getValue();
            case CALL_AUDIO:
            case CALL_VIDEO:
                return TioMsgType.call.getValue();
            case RED_PAPER:
                return TioMsgType.redPaper.getValue();
            case GROUP_APPLY:
                return TioMsgType.groupApply.getValue();
            case LOCATION:
                return TioMsgType.location.getValue();
            case FACE_EMOTION:
                return TioMsgType.faceEmotion.getValue();
            case TRANS_Message:
                return TioMsgType.transMessage.getValue();
            default:
                return TioMsgType.unknown.getValue();
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0:mDatas.size();
    }

    public class TextHolder extends RecyclerView.ViewHolder {

        private TextView bodyTextView;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public TextHolder(View itemView) {
            super(itemView);
            bodyTextView = itemView.findViewById(R.id.tv_message);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    private int[] getWH(int resId){

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //只请求图片宽高，不解析图片像素(请求图片属性但不申请内存，解析bitmap对象，该对象不占内存)
        opts.inJustDecodeBounds = true;
        //String path = Environment.getExternalStorageDirectory() + "/dog.jpg";
        BitmapFactory.decodeResource(mContext.getResources(), resId, opts);
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;
        return new int[]{imageWidth, imageHeight};
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private TioImageView msgImageView;
        private String mOriginalImgUrl;
        private InnerMsgImage data;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            msgImageView = itemView.findViewById(R.id.msgImageView);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class AudioHolder extends RecyclerView.ViewHolder {

        private FrameLayout container;
        private InnerMsgAudio audio;
        private ImageView image;
        private TextView tv;
        private TextView tv_left;
        private TextView tv_right;
        private ImageView image_left;
        private ImageView image_right;
        private LinearLayout ll_left;
        private LinearLayout ll_right;
        private AnimationDrawable animationDrawable;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public AudioHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.fl_container);
            image_left = itemView.findViewById(R.id.image_left);
            image_right = itemView.findViewById(R.id.image_right);
            tv_left = itemView.findViewById(R.id.tv_left);
            tv_right = itemView.findViewById(R.id.tv_right);
            ll_left = itemView.findViewById(R.id.ll_left);
            ll_right = itemView.findViewById(R.id.ll_right);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private TioImageView msgImageView;
        private String mVideoUrl;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            msgImageView = itemView.findViewById(R.id.msgImageView);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class FileHolder extends RecyclerView.ViewHolder {

        private ImageView iv_fileIcon;
        private TextView tv_fileName;
        private TextView tv_fileSize;
        private InnerMsgFile msgFile;
        private View container;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;


        public FileHolder(@NonNull View itemView) {
            super(itemView);
            iv_fileIcon = itemView.findViewById(R.id.iv_fileIcon);
            tv_fileName = itemView.findViewById(R.id.tv_fileName);
            tv_fileSize = itemView.findViewById(R.id.tv_fileSize);
            container = itemView.findViewById(R.id.container);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        private TioImageView hiv_avatar;
        private TextView tv_cardType;
        private ImageView iv_cardType;
        private TextView tv_usrName;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            hiv_avatar = itemView.findViewById(R.id.hiv_avatar);
            tv_cardType = itemView.findViewById(R.id.tv_cardType);
            iv_cardType = itemView.findViewById(R.id.iv_cardType);
            tv_usrName = itemView.findViewById(R.id.tv_usrName);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class CallHolder extends RecyclerView.ViewHolder {

        private TextView bodyTextView;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public CallHolder(@NonNull View itemView) {
            super(itemView);
            bodyTextView = itemView.findViewById(R.id.tv_message);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }


    public class RedPaperHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout cl_container;
        private TextView tv_title;
        private TextView tv_subtitle;

        private TextView tvRemark;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        @Nullable
        private InnerMsgRed msgRed;
        private final RedPaperViewModel viewModel = new RedPaperViewModel();
        private RedPaperDialog redPaperDialog;

        public RedPaperHolder(@NonNull View itemView) {
            super(itemView);
            cl_container = itemView.findViewById(R.id.cl_container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_subtitle = itemView.findViewById(R.id.tv_subtitle);
            tvRemark = itemView.findViewById(R.id.tv_remark);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }
    public class ReceiveRedPaperHolder extends RecyclerView.ViewHolder {

        private TextView tv_txt;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public ReceiveRedPaperHolder(@NonNull View itemView) {
            super(itemView);
            tv_txt = itemView.findViewById(R.id.tv_txt);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class TipHolder extends RecyclerView.ViewHolder {

        private TextView notificationTextView;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public TipHolder(@NonNull View itemView) {
            super(itemView);
            notificationTextView = itemView.findViewById(R.id.message_item_notification_label);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }
    public class LocationHolder extends RecyclerView.ViewHolder {

        private TioImageView msgImageView;
        private TextView textView;
        private String mOriginalImgUrl;

        private InnerMsgLocation data;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            msgImageView = itemView.findViewById(R.id.msgImageView);
            textView = itemView.findViewById(R.id.msg_address);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }
    public class EmotionHolder extends RecyclerView.ViewHolder {

        private TioImageView msgImageView;
        private String faceEmotion;
        private Integer type;
        private int[] wh;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public EmotionHolder(@NonNull View itemView) {
            super(itemView);
            msgImageView = itemView.findViewById(R.id.msgImageView);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class TransAmoutHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout cl_container;
        private TextView tv_title;
        private TextView tv_subtitle;

        private TextView tvRemark;

        private Long transId;

        private int transType;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        @Nullable
        private InnerMsgTransAmount transAmount;

        public TransAmoutHolder(@NonNull View itemView) {
            super(itemView);
            cl_container = itemView.findViewById(R.id.cl_container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_subtitle = itemView.findViewById(R.id.tv_subtitle);
            tvRemark = itemView.findViewById(R.id.tv_remark);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class TransMessageHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView message1;
        private TextView message2;
        private TextView message3;
        private TioImageView tiv_avatar;
        private FrameLayout content_fl;

        public TransMessageHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            message1 = itemView.findViewById(R.id.message1);
            message2 = itemView.findViewById(R.id.message2);
            message3 = itemView.findViewById(R.id.message3);
            tiv_avatar = itemView.findViewById(R.id.tiv_avatar);
            content_fl = itemView.findViewById(R.id.content_fl);
        }
    }

    public class UnknownHolder extends RecyclerView.ViewHolder {

        public TextView unSupportDesc;

        public UnknownHolder(@NonNull View itemView) {
            super(itemView);
            unSupportDesc = itemView.findViewById(R.id.tv_known_msg);
        }
    }


    private int getFileIconId(int fileicontype) {
        int fileIconId;
        switch (fileicontype) {
            case FileIconType.PDF:
                fileIconId = R.drawable.tio_file_icon_pdf;
                break;
            case FileIconType.TXT:
                fileIconId = R.drawable.tio_file_icon_txt;
                break;
            case FileIconType.DOC:
                fileIconId = R.drawable.tio_file_icon_doc;
                break;
            case FileIconType.XLS:
                fileIconId = R.drawable.tio_file_icon_xls;
                break;
            case FileIconType.PPT:
                fileIconId = R.drawable.tio_file_icon_ppt;
                break;
            case FileIconType.APK:
                fileIconId = R.drawable.tio_file_icon_apk;
                break;
            case FileIconType.IMG:
                fileIconId = R.drawable.tio_file_icon_img;
                break;
            case FileIconType.ZIP:
                fileIconId = R.drawable.tio_file_icon_zip;
                break;
            case FileIconType.VIDEO:
                fileIconId = R.drawable.tio_file_icon_video;
                break;
            case FileIconType.AUDIO:
                fileIconId = R.drawable.tio_file_icon_audio;
                break;
            default:
                fileIconId = R.drawable.tio_file_icon_other;
                break;
        }
        return fileIconId;
    }

    private void showDownloadDialog(Context context,InnerMsgFile innerMsgFile) {
        if (innerMsgFile == null) return;
        new EasyOperDialog.Builder(String.format(Locale.getDefault(), "下载 %s 吗？", innerMsgFile.filename))
                .setPositiveBtnTxt("下载")
                .setNegativeBtnTxt("取消")
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        downloadFile(innerMsgFile);
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(context);
    }

    private void downloadFile(InnerMsgFile innerMsgFile) {
        if (innerMsgFile == null) return;
//        getAdapter().getDownloadPresenter().downloadWithTip(innerMsgFile.url, mContext);
        OkGo.<File>get(HttpCache.getResUrl(innerMsgFile.url))
                .execute(new TioFileCallback() {
                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        if (mContext != null) {
                            SingletonProgressDialog.show_unCancel(mContext, "下载中...");
                        }
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        TioToast.showShort("下载完成");
                        File file = response.body();
                        try {
                            openFile(mContext, file);
                        }catch (Exception e){
                            e.printStackTrace();
                            ToastUtils.showShort("无法打开文件");
                        }

                    }

                    @Override
                    public void onError(Response<File> response) {
                        TioToast.showShort("下载失败");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        TioLogger.d("下载进度：" + progress);

                        String downloadLength = Formatter.formatFileSize(Utils.getApp(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(Utils.getApp(), progress.totalSize);
                        TioLogger.d("DownloadSize：" + downloadLength + "/" + totalLength);

                        String speed = Formatter.formatFileSize(Utils.getApp(), progress.speed);
                        TioLogger.d("NetSpeed：" + String.format("%s/s", speed));

                        NumberFormat numberFormat = NumberFormat.getPercentInstance();
                        numberFormat.setMinimumFractionDigits(2);
                        TioLogger.d("Progress：" + numberFormat.format(progress.fraction));
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        SingletonProgressDialog.dismiss();
                    }
                });
    }

    private void openFile(Context context, File docFile){
        Intent in = new Intent("android.intent.action.VIEW");
        in.addCategory("android.intent.category.DEFAULT");
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context,  BuildConfig.APPLICATION_ID + ".fileprovider", docFile);
            // 给目标应用一个临时授权
            in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(docFile);
        }
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.setDataAndType(data, getMimeTypeFromFile(docFile));
        context.startActivity(in);
    }

    private static String getMimeTypeFromFile(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex > 0) {
            //获取文件的后缀名
            String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
            //在MIME和文件类型的匹配表中找到对应的MIME类型。
            HashMap<String, String> map = MyMimeMap.getMimeMap();
            if (!TextUtils.isEmpty(end) && map.keySet().contains(end)) {
                type = map.get(end);
            }
        }
        return type;
    }

    static class MyMimeMap {
        private static final HashMap<String, String> mapSimple = new HashMap<>();
        /**
         *  常用"文件扩展名—MIME类型"匹配表。
         *  注意，此表并不全，也并不是唯一的，就像有人喜欢用浏览器打开TXT一样，你可以根据自己的爱好自定义。
         */
        public static HashMap<String, String> getMimeMap() {
            if (mapSimple.size() == 0) {
                mapSimple.put(".3gp", "video/3gpp");
                mapSimple.put(".apk", "application/vnd.android.package-archive");
                mapSimple.put(".asf", "video/x-ms-asf");
                mapSimple.put(".avi", "video/x-msvideo");
                mapSimple.put(".bin", "application/octet-stream");
                mapSimple.put(".bmp", "image/bmp");
                mapSimple.put(".c", "text/plain");
                mapSimple.put(".chm", "application/x-chm");
                mapSimple.put(".class", "application/octet-stream");
                mapSimple.put(".conf", "text/plain");
                mapSimple.put(".cpp", "text/plain");
                mapSimple.put(".doc", "application/msword");
                mapSimple.put(".docx", "application/msword");
                mapSimple.put(".exe", "application/octet-stream");
                mapSimple.put(".gif", "image/gif");
                mapSimple.put(".gtar", "application/x-gtar");
                mapSimple.put(".gz", "application/x-gzip");
                mapSimple.put(".h", "text/plain");
                mapSimple.put(".htm", "text/html");
                mapSimple.put(".html", "text/html");
                mapSimple.put(".jar", "application/java-archive");
                mapSimple.put(".java", "text/plain");
                mapSimple.put(".jpeg", "image/jpeg");
                mapSimple.put(".jpg", "image/jpeg");
                mapSimple.put(".js", "application/x-javascript");
                mapSimple.put(".log", "text/plain");
                mapSimple.put(".m3u", "audio/x-mpegurl");
                mapSimple.put(".m4a", "audio/mp4a-latm");
                mapSimple.put(".m4b", "audio/mp4a-latm");
                mapSimple.put(".m4p", "audio/mp4a-latm");
                mapSimple.put(".m4u", "video/vnd.mpegurl");
                mapSimple.put(".m4v", "video/x-m4v");
                mapSimple.put(".mov", "video/quicktime");
                mapSimple.put(".mp2", "audio/x-mpeg");
                mapSimple.put(".mp3", "audio/x-mpeg");
                mapSimple.put(".mp4", "video/mp4");
                mapSimple.put(".mpc", "application/vnd.mpohun.certificate");
                mapSimple.put(".mpe", "video/mpeg");
                mapSimple.put(".mpeg", "video/mpeg");
                mapSimple.put(".mpg", "video/mpeg");
                mapSimple.put(".mpg4", "video/mp4");
                mapSimple.put(".mpga", "audio/mpeg");
                mapSimple.put(".msg", "application/vnd.ms-outlook");
                mapSimple.put(".ogg", "audio/ogg");
                mapSimple.put(".pdf", "application/pdf");
                mapSimple.put(".png", "image/png");
                mapSimple.put(".pps", "application/vnd.ms-powerpoint");
                mapSimple.put(".ppt", "application/vnd.ms-powerpoint");
                mapSimple.put(".pptx", "application/vnd.ms-powerpoint");
                mapSimple.put(".prop", "text/plain");
                mapSimple.put(".rar", "application/x-rar-compressed");
                mapSimple.put(".rc", "text/plain");
                mapSimple.put(".rmvb", "audio/x-pn-realaudio");
                mapSimple.put(".rtf", "application/rtf");
                mapSimple.put(".sh", "text/plain");
                mapSimple.put(".tar", "application/x-tar");
                mapSimple.put(".tgz", "application/x-compressed");
                mapSimple.put(".txt", "text/plain");
                mapSimple.put(".wav", "audio/x-wav");
                mapSimple.put(".wma", "audio/x-ms-wma");
                mapSimple.put(".wmv", "audio/x-ms-wmv");
                mapSimple.put(".wps", "application/vnd.ms-works");
                mapSimple.put(".xml", "text/plain");
                mapSimple.put(".xls", "application/vnd.ms-excel");
                mapSimple.put(".xlsx", "application/vnd.ms-excel");
                mapSimple.put(".z", "application/x-compress");
                mapSimple.put(".zip", "application/zip");
                mapSimple.put("", "*/*");
            }
            return mapSimple;
        }
    }

    private void setTvText(InnerMsgCall call,CallHolder callHolder) {
        if (call == null) return;
        String showText = null;
        HangUpType hangupType = call.getHangupType();
        if (hangupType != null) {
            int callType = call.calltype;
            if (call.calltype == 11) {
                callType = 1;
            } else if (call.calltype == 10) {
                callType = 2;
            }
            showText = hangupType.getShowText(false, call.duration, callType);
        }
        callHolder.bodyTextView.setText(StringUtil.nonNull(showText));
    }

    private void setTvDrawable(InnerMsgCall call,CallHolder callHolder) {
        if (call == null) return;
        Drawable drawable = null;
        if (call.calltype == 10) {
            // 视频
            drawable = ResourceUtils.getDrawable(R.drawable.icon_im_video02);
        } else if (call.calltype == 11) {
            // 音频
            drawable = ResourceUtils.getDrawable(R.drawable.icon_im_call);
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        if (drawable != null) {
            callHolder.bodyTextView.setCompoundDrawablePadding(SizeUtils.dp2px(4));
        }
    }
}
