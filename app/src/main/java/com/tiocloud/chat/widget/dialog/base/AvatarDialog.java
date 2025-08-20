package com.tiocloud.chat.widget.dialog.base;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.huantansheng.easyphotos.EasyPhotos;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.engine.EasyPhotosEngine;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;

import java.io.File;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 头像修改弹窗
 */
public class AvatarDialog extends BaseDialog implements View.OnClickListener {

    private final static int REQ_CODE_IMAGE_GIF = 1333;// 选择图片、gif

    private View tv_takePhoto;
    private View tv_pickPhoto;
    private View tv_cancel;
    private final TioCallback<Void> mCallback;
    private Activity activity;

    /**
     * 剪切后图像文件
     */
    private Uri mDestination;

    public AvatarDialog(final Context context, TioCallback<Void> callback) {
        super(context);
        mCallback = callback;
        setAnimation(R.style.tio_bottom_dialog_anim);
        setFullScreenWidth();
        setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_avatar, null));
        initViews();
    }

    private void initViews() {
        mDestination = Uri.fromFile(new File(getContext().getCacheDir(), "cropImage.jpeg"));
        tv_takePhoto = findViewById(R.id.tv_takePhoto);
        tv_pickPhoto = findViewById(R.id.tv_pickPhoto);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_takePhoto.setOnClickListener(this);
        tv_pickPhoto.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TioHttpClient.cancel(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            dismiss();
        } else if (v == tv_takePhoto) {
            // 拍照
            if (activity == null) return;
            EasyPhotos.createCamera(activity, true)
                    .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                    .start(REQ_CODE_IMAGE_GIF);
            dismiss();
        } else if (v == tv_pickPhoto) {
            // 选择图片
            if (activity == null) return;
            EasyPhotos.createAlbum(activity, false, true, EasyPhotosEngine.getInstance())
                    .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                    .setPuzzleMenu(false)
                    .setCleanMenu(false)
                    .setCount(1)
                    .setVideo(false)
                    .setGif(true)
                    .start(REQ_CODE_IMAGE_GIF);
            dismiss();
        }
    }

    // ====================================================================================
    // 头像上传
    // ====================================================================================

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
