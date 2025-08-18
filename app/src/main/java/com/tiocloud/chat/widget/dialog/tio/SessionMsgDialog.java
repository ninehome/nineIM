package com.tiocloud.chat.widget.dialog.tio;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.share.msg.ShareMsgActivity;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.TioDialog;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.callback.TioFileCallback;
import com.watayouxiang.httpclient.model.request.CollectEmotionReq;
import com.watayouxiang.httpclient.model.request.MsgOperReq;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;

import java.io.File;
import java.text.NumberFormat;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/25
 *     desc   :
 * </pre>
 */
public class SessionMsgDialog extends TioDialog {

    private final Activity activity;

    public SessionMsgDialog(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected int getDialogContentId() {
        return R.layout.tio_session_msg_dialog;
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();
        initCopyView();
        initWithdrawView();
        initDeleteView();
        initForwardView();
        initCollectView();
        initComplaintView();
        initMultiChooseView();
        initSaveMediaView();
    }

    // ====================================================================================
    // 举报
    // ====================================================================================

    private String complaint_mids;
    private String complaint_chatLinkId;
    private boolean complaint_enableComplaint;

    public SessionMsgDialog setComplaintData(String chatLinkId, String mids, TioMsgType msgType) {
        this.complaint_mids = mids;
        this.complaint_chatLinkId = chatLinkId;
        this.complaint_enableComplaint = enableComplaint(msgType);
        return this;
    }

    private boolean enableComplaint(TioMsgType msgType) {
        if (msgType == null) return false;
        switch (msgType) {
            case call:
            case redPaper:
                return false;
            default:
                return true;
        }
    }

    private void initComplaintView() {
        TextView tv_complaint = findViewById(R.id.tv_complaint);
        if (complaint_mids != null && complaint_chatLinkId != null && complaint_enableComplaint) {
            tv_complaint.setVisibility(View.VISIBLE);
            tv_complaint.setOnClickListener(v -> reqComplaint());
        } else {
            tv_complaint.setVisibility(View.GONE);
        }
    }

    private void reqComplaint() {
        if (complaint_mids != null && complaint_chatLinkId != null) {
            MsgOperReq complaint = MsgOperReq.complaint(complaint_chatLinkId, complaint_mids);
            complaint.setCancelTag(this);
            complaint.get(new TioCallback<String>() {
                @Override
                public void onTioSuccess(String s) {
                    TioToast.showShort(getContext().getString(R.string.jubaochenggong));
                    dismiss();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
        }
    }

    // ====================================================================================
    // 消息转发
    // ====================================================================================

    private String forward_chatlinkid;
    private String forward_mids;

    public SessionMsgDialog setForwardData(String forward_chatlinkid, String forward_mids) {
        this.forward_chatlinkid = forward_chatlinkid;
        this.forward_mids = forward_mids;
        return this;
    }


    /**
     * @return 是否支持消息转发
     */
    private boolean enableForward(TioMsg msg) {
        TioMsgType msgType = msg.getMsgType();
        if (msgType != null) {
            switch (msgType) {
                case call:
                case tip:
                case audio:
                case unknown:
                case redPaper:
                    return false;
                case card:
                    // 群名片不支持转发
                    InnerMsgCard card = (InnerMsgCard) msg.getContentObj();
                    return card != null && card.cardtype == 1;
                default:
                    return true;
            }
        }
        return false;
    }

    private boolean enableCollect(TioMsg msg, boolean isCollect){
        TioMsgType msgType = msg.getMsgType();
        if (msgType != null){
            switch (msgType){
                case image:
                    return true;
                case faceEmotion:
                    return isCollect;
                default:
                    return false;
            }
        }
        return false;
    }

    public SessionMsgDialog setForwardData(String forward_chatlinkid, String forward_mids, TioMsg msg) {
        boolean enableForward = enableForward(msg);
        if (enableForward) {
            this.setForwardData(forward_chatlinkid, forward_mids);
        }
        return this;
    }

    //收藏数据
    int[] picWH;
    String picUrl;

    public SessionMsgDialog setCollectData(int[] picWH, String picUrl, boolean isCollect, TioMsg msg){
        if (!TioConfig.OpenCloseConfig.collectFaceEmotionEnable()){
            return this;
        }
        if (enableCollect(msg, isCollect)){
            this.picWH = picWH;
            this.picUrl = picUrl;
        }
        return this;
    }

    private void initForwardView() {
        TextView tv_forward = findViewById(R.id.tv_forward);
        if (forward_chatlinkid != null && forward_mids != null) {
            tv_forward.setVisibility(View.VISIBLE);
            tv_forward.setOnClickListener(view -> {
                ShareMsgActivity.start(activity, forward_chatlinkid, forward_mids);
                dismiss();
            });
        } else {
            tv_forward.setVisibility(View.GONE);
        }
    }

    private TextView tv_multi_choose;
    private void initMultiChooseView(){
        tv_multi_choose = findViewById(R.id.tv_multi_choose);
    }

    public View getMutiChooseView(){
        return tv_multi_choose;
    }

    private void initCollectView() {
        TextView tv_collect = findViewById(R.id.tv_collect);
        if (picWH != null && picUrl != null ) {
            tv_collect.setVisibility(View.VISIBLE);
            tv_collect.setOnClickListener(view -> {
                CollectEmotionReq collectEmotionReq = new CollectEmotionReq(picUrl, picWH[0], picWH[1]);
                collectEmotionReq.setCancelTag(this);
                collectEmotionReq.post(new TioCallback<String>() {
                    @Override
                    public void onTioSuccess(String s) {
                        ToastUtils.showShort(getContext().getString(R.string.has_add_zidingyibiaoqing));
                        TioConfig.emotionList = null;
                    }

                    @Override
                    public void onTioError(String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
                dismiss();
            });
        } else {
            tv_collect.setVisibility(View.GONE);
        }
    }

    // ====================================================================================
    // 删除消息
    // ====================================================================================

    private String delete_chatlinkid;
    private String delete_mids;

    public SessionMsgDialog setDeleteData(String chatlinkid, String mids) {
        this.delete_chatlinkid = chatlinkid;
        this.delete_mids = mids;
        return this;
    }

    private void initDeleteView() {
        TextView tv_delete = findViewById(R.id.tv_delete);
        if (delete_chatlinkid != null && delete_mids != null) {
            tv_delete.setVisibility(View.VISIBLE);
            tv_delete.setOnClickListener(view -> postDeleteReq());
        } else {
            tv_delete.setVisibility(View.GONE);
        }
    }

    private void postDeleteReq() {
        if (delete_chatlinkid != null && delete_mids != null) {
            MsgOperReq msgOperReq = new MsgOperReq(delete_chatlinkid, delete_mids, "1");
            msgOperReq.setCancelTag(this);
            msgOperReq.post(new TioCallback<String>() {
                @Override
                public void onTioSuccess(String s) {
                    dismiss();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
        }
    }

    // ====================================================================================
    // 撤回消息
    // ====================================================================================

    private String withdraw_chatlinkid;
    private String withdraw_mids;

    public SessionMsgDialog setWithdrawData(boolean isUpManager, String chatlinkid, String mids, TioMsg tioMsg, TioMsgType msgType) {

        boolean withdraw_enable = isEnableWithdraw(msgType);
        if ((tioMsg.isSendMsg() || isUpManager) && withdraw_enable) {
            this.withdraw_chatlinkid = chatlinkid;
            this.withdraw_mids = mids;
        }
        return this;
    }

    private boolean isEnableWithdraw(TioMsgType msgType) {
        if (msgType == TioMsgType.redPaper) {
            return false;
        }
        return true;
    }

    private void initWithdrawView() {
        TextView tv_withdraw = findViewById(R.id.tv_withdraw);
        if (withdraw_chatlinkid != null && withdraw_mids != null) {
            tv_withdraw.setVisibility(View.VISIBLE);
            tv_withdraw.setOnClickListener(view -> postWithdrawReq());
        } else {
            tv_withdraw.setVisibility(View.GONE);
        }
    }

    private void postWithdrawReq() {
        if (withdraw_chatlinkid != null && withdraw_mids != null) {
            MsgOperReq msgOperReq = new MsgOperReq(withdraw_chatlinkid, withdraw_mids, "9");
            msgOperReq.setCancelTag(this);
            msgOperReq.post(new TioCallback<String>() {
                @Override
                public void onTioSuccess(String s) {
                    dismiss();
                }

                @Override
                public void onTioError(String msg) {
                    TioToast.showShort(msg);
                }
            });
        }
    }

    // ====================================================================================
    // 复制消息
    // ====================================================================================

    private String copy_text;

    public SessionMsgDialog setCopyData(String text) {
        this.copy_text = text;
        return this;
    }

    private void initCopyView() {
        TextView tv_copy = findViewById(R.id.tv_copy);
        if (copy_text != null) {
            tv_copy.setVisibility(View.VISIBLE);
            tv_copy.setOnClickListener(view -> {
                StringUtil.copyText(copy_text);
                dismiss();
            });
        } else {
            tv_copy.setVisibility(View.GONE);
        }
    }

    // ====================================================================================
    // 保存图片/视频到本地相册
    // ====================================================================================
    private Boolean ifMedia = false;
    private Object msgMediaContent;
    private TioMsgType mediaMsgType;
    public SessionMsgDialog setSaveMediaData(Object content, TioMsgType msgType) {
        if (msgType == TioMsgType.image || msgType == TioMsgType.video) {
            ifMedia = true;
            mediaMsgType = msgType;
            msgMediaContent = content;
        }
        return this;
    }

    private void initSaveMediaView() {
        TextView tvSaveMedia = findViewById(R.id.tv_save_media);
        if (ifMedia) {
            tvSaveMedia.setVisibility(View.VISIBLE);
            tvSaveMedia.setOnClickListener(v -> {
                dismiss();
                if (msgMediaContent != null) {
                    mediaPermissionCheck(() -> {
                        if (msgMediaContent instanceof InnerMsgImage) { //图片消息
                            downloadFile(((InnerMsgImage) msgMediaContent).url);
                        } else if (msgMediaContent instanceof InnerMsgVideo) {
                            downloadFile(((InnerMsgVideo) msgMediaContent).url);
                        }
                    });
                }
            });
        } else {
            tvSaveMedia.setVisibility(View.GONE);
        }
    }

    /**
     * 权限校验
     * @param runnable
     */
    private void mediaPermissionCheck(Runnable runnable) {
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                runnable.run();
            }

            @Override
            public void onDenied() {
                ToastUtils.showShort("存储权限获取失败！");
            }
        }).request();
    }

    private void downloadFile(String url) {
        //公共目录
        File publicMediaDir = Environment.getExternalStoragePublicDirectory(mediaMsgType == TioMsgType.image ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);
        //自己的文件夹
        File appPicturesDir = new File(publicMediaDir, AppUtils.getAppName());
        if (!appPicturesDir.exists()) {
            appPicturesDir.mkdirs();
        }

        OkGo.<File>get(HttpCache.getResUrl(url)).execute(new TioFileCallback(appPicturesDir.getAbsolutePath(), getFileNameFromUrl(url)) {
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                SingletonProgressDialog.show_unCancel(activity, "保存中...");
            }

            @Override
            public void onSuccess(Response<File> response) {
                notifyMediaScanner(response.body(), () -> TioToast.showShort("保存完成"));
            }

            @Override
            public void onError(Response<File> response) {
                TioToast.showShort("保存失败");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                SingletonProgressDialog.dismiss();
            }
        });
    }

    public void notifyMediaScanner(File file, Runnable runnable) {
        MediaScannerConnection.scanFile(activity, new String[]{file.getAbsolutePath()}, new String[]{getMimeType(file)}, (path, uri) -> {
            LogUtils.e("已扫描: " + path);
            runnable.run();
        });
    }

    private String getMimeType(File file) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName().toLowerCase());
        return ext != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext) : "image/jpeg";
    }

    public static String getFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "unknown_file";
        }

        int queryIndex = url.indexOf('?');
        String baseUrl = (queryIndex == -1) ? url : url.substring(0, queryIndex);

        int lastSlashIndex = baseUrl.lastIndexOf('/');

        if (lastSlashIndex >= 0 && lastSlashIndex < baseUrl.length() - 1) {
            return baseUrl.substring(lastSlashIndex + 1);
        }

        return "unknown_file";
    }
}