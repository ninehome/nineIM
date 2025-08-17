package com.watayouxiang.qrcode.feature.qrcode_decoder.mvp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.VibrateUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.watayouxiang.androidutils.engine.EasyPhotosEngine;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.util.UrlUtil;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.qrcode.R;
import com.watayouxiang.qrcode.feature.qrcode_decoder.QRCodeHandler;

import java.util.ArrayList;
import java.util.Locale;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class Presenter extends Contract.Presenter {

    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }

    @Override
    public void init(Activity activity) {
        boolean isGranted = PermissionUtils.isGranted(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!isGranted) {
            TioToast.showShort(activity.getString(R.string.havenoquanxian));
            activity.finish();
            return;
        }
        getView().resetUI();
        initQRCodeView(activity);
    }

    // ====================================================================================
    // 从图库中选取图片
    // ====================================================================================

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    @Override
    public void chooseQRCodeFromGallery(Activity activity) {
        EasyPhotos.createAlbum(activity, false, true, EasyPhotosEngine.getInstance())
                .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .setCount(1)
                .setVideo(false)
                .setGif(false)
                .start(REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            // 容错处理
            if (data == null) return;
            // 返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            // 返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
            boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
            TioLogger.i(String.valueOf(resultPhotos));

            // 容错处理
            if (resultPhotos == null || resultPhotos.size() == 0) {
                return;
            }

            // 判断类型
            Photo photo = resultPhotos.get(0);
            if (UrlUtil.isImageSuffix(photo.path)) {
                // 普通图片
                // 识别二维码
                getView().getZXingView().decodeQRCode(photo.path);
            } else if (UrlUtil.isGifSuffix(photo.path)) {
                // gif
                TioLogger.d("不能是Gif");
            } else {
                // 视频
                TioLogger.d("不能是视频");
            }
        }
    }

    // ====================================================================================
    // 初始化 QRCodeView
    // ====================================================================================

    private Boolean mIsBrightnessDark = null;
    private boolean mIsOpenFlashlight = false;

    private void initQRCodeView(Activity activity) {
        // 默认不显示
        TextView tvFlashlight = getView().getTvFlashlight();
        tvFlashlight.setVisibility(View.GONE);

        // 设置代理
        ZXingView zxingview = getView().getZXingView();
        zxingview.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                TioLogger.d(String.format(Locale.getDefault(), activity.getString(R.string.qrcode_look_s), result));
                // 震动反馈
                VibrateUtils.vibrate(200);
                // 处理扫描结果
                QRCodeHandler.handleQRCode(result, activity, zxingview);
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {
                if (mIsBrightnessDark == null || mIsBrightnessDark != isDark) {
                    // 记录状态
                    mIsBrightnessDark = isDark;
                    // 环境光线发生变化
                    TioLogger.d("摄像头环境亮度发生变化 isDark: " + isDark);
                    // 通知光线变化
                    onNotifyChanged_Flashlight_BrightnessDark(activity);
                }
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                TioLogger.e("打开相机出错");
                TioToast.showShort(activity.getString(R.string.open_error));
            }
        });
    }

    private void onNotifyChanged_Flashlight_BrightnessDark(Activity activity) {
        ZXingView zxingview = getView().getZXingView();
        TextView tvFlashlight = getView().getTvFlashlight();
        if (mIsOpenFlashlight) {
            // 闪光灯打开状态
            tvFlashlight.setText(activity.getString(R.string.closeshanguangdeng));
            tvFlashlight.setOnClickListener(view -> {
                zxingview.closeFlashlight();
                mIsOpenFlashlight = false;
                tvFlashlight.setVisibility(View.VISIBLE);
                onNotifyChanged_Flashlight_BrightnessDark(activity);
            });
        } else {
            // 闪光灯关闭状态
            tvFlashlight.setText(activity.getString(R.string.openshanguangdeng));
            tvFlashlight.setOnClickListener(view -> {
                zxingview.openFlashlight();
                mIsOpenFlashlight = true;
                tvFlashlight.setVisibility(View.VISIBLE);
                onNotifyChanged_Flashlight_BrightnessDark(activity);
            });
        }
        if (mIsBrightnessDark != null && mIsBrightnessDark) {
            // 光线暗
            tvFlashlight.setVisibility(View.VISIBLE);
        } else {
            // 光线充足
            if (mIsOpenFlashlight) {
                // 闪光灯打开状态
                tvFlashlight.setVisibility(View.VISIBLE);
            } else {
                // 闪光灯关闭状态
                tvFlashlight.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onStart() {
        ZXingView zxingview = getView().getZXingView();
        // 打开后置摄像头开始预览，但是并未开始识别
        zxingview.startCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        // 开始识别
        zxingview.startSpot();
        // 显示扫描框
        zxingview.showScanRect();
    }

    @Override
    public void onStop() {
        ZXingView zxingview = getView().getZXingView();
        // 关闭摄像头预览，并且隐藏扫描框
        zxingview.stopCamera();
    }

    @Override
    public void onDestroy() {
        ZXingView zxingview = getView().getZXingView();
        // 销毁二维码扫描控件
        zxingview.onDestroy();
    }

    // ====================================================================================
    // 权限检测
    // ====================================================================================

    public static void checkPermission(OnCheckPermissionCallback callback, Context context) {
        // 显示权限用途解释对话框
        new AlertDialog.Builder(context)
                .setTitle("权限申请")
                .setMessage("需要相机权限以扫描二维码，存储权限以从相册选择二维码图片")
                .setPositiveButton("确定", (dialog, which) -> {
                    // 用户确认后请求权限
                    PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                            .rationale((activity, shouldRequest) -> shouldRequest.again(true))
                            .callback((isAllGranted, granted, deniedForever, denied) -> {
                                if (isAllGranted) {
                                    if (callback != null) {
                                        callback.onPermissionAllGranted();
                                    }
                                } else {
                                    if (!deniedForever.isEmpty()) {
                                        PermissionUtils.launchAppDetailsSettings();
                                    }
                                    TioToast.showShort(context.getString(R.string.havenoquanxian));
                                }
                            })
                            .request();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public interface OnCheckPermissionCallback {
        void onPermissionAllGranted();
    }
}