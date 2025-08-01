package com.tiocloud.chat.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.watayouxiang.imclient.utils.MD5Utils;
import com.watayouxiang.permission.TaoPermissionUtils;
import com.watayouxiang.permission.dialog.AppSettingsDialog;
import com.watayouxiang.permission.helper.TaoActivityPermissionHelper;
import com.watayouxiang.permission.helper.TaoPermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/5/22
 * desc :
 */
public class ActivityPermissionHelper {

    @NonNull
    private final Activity mActivity;
    @NonNull
    private final TaoActivityPermissionHelper mPermissionHelper;
    @NonNull
    private final List<String> mPermissions;
    @Nullable
    private final OnPermissionListener mListener;
    private static final String PREF_NAME = "permission_prefs";
    private static final String KEY_PERMISSION_DENIED = "permission_denied";

    public ActivityPermissionHelper(@NonNull Activity activity, @NonNull List<String> permissions, @Nullable OnPermissionListener listener) {
        mActivity = activity;
        mPermissions = permissions;
        mListener = listener;

        mPermissionHelper = new TaoActivityPermissionHelper(activity);
        mPermissionHelper.setPermissionListener(new TaoPermissionListener() {
            @Override
            public void onGranted() {
                if (mListener != null) {
                    mListener.onGranted();
                }
            }

            @Override
            public void onDenied(@NonNull List<String> deniedPermissions) {
                if (mListener != null) {
                    mListener.onDenied(deniedPermissions);
                }
            }

            @Override
            public void onDisabled(@NonNull List<String> disabledPermissions, @NonNull List<String> deniedPermissions) {
                new AppSettingsDialog.Builder(activity)
                        .build()
                        .show();
            }
        });
    }

    /**
     * 开始申请权限
     */
    public void requestPermissions() {
        if (isPermissionDenied()) {
            // 用户之前拒绝过权限，不立即请求
            return;
        }
        // 显示权限使用目的对话框
        showPermissionPurposeDialog();
    }

    /**
     * 显示权限使用目的对话框
     */
    private void showPermissionPurposeDialog() {
        StringBuilder message = new StringBuilder("我们需要以下权限来提供更好的服务：\n");
        for (String permission : mPermissions) {
            switch (permission) {
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    message.append("- 精确位置权限：用于精准显示附近的人和提供高精度基于位置的服务，如导航等。\n");
                    break;
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    message.append("- 粗略位置权限：用于大致显示附近的人和提供基础基于位置的服务，如附近商家推荐。\n");
                    break;
                case Manifest.permission.CAMERA:
                    message.append("- 相机权限：用于拍照、视频通话、扫描二维码等功能。\n");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    message.append("- 录音权限：用于语音通话、录制语音消息等功能。\n");
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    message.append("- 读取外部存储权限：用于读取本地图片、视频和文件，以便在应用中使用。\n");
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    message.append("- 写入外部存储权限：用于保存图片、视频和文件到本地设备。\n");
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    message.append("- 读取手机状态权限：用于设备识别、防止恶意攻击，保障账户安全。\n");
                    break;
                case Manifest.permission.CALL_PHONE:
                    message.append("- 拨打电话权限：用于在应用内直接拨打电话。\n");
                    break;
                case Manifest.permission.VIBRATE:
                    message.append("- 振动权限：用于在收到消息、来电等场景时提供振动提醒。\n");
                    break;
            }
        }
        new AlertDialog.Builder(mActivity)
                .setTitle("权限申请")
                .setMessage(message.toString())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 执行权限请求逻辑
                        mPermissionHelper.requestPermissions(mPermissions);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onDenied(mPermissions);
                        }
                        SharedPreferences prefs = mActivity.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
                        prefs.edit().putBoolean(KEY_PERMISSION_DENIED, true).apply();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private boolean isPermissionDenied() {
        SharedPreferences prefs = mActivity.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        return prefs.getBoolean(KEY_PERMISSION_DENIED, false);
    }

    /**
     * 必须调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean allGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            SharedPreferences prefs = mActivity.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
            prefs.edit().putBoolean(KEY_PERMISSION_DENIED, true).apply();
        }
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 必须调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            List<String> deniedPermissions = TaoPermissionUtils.filterDeniedPermissions(mActivity, mPermissions);
            if (deniedPermissions.isEmpty()) {
                if (mListener != null) {
                    mListener.onGranted();
                }
            } else {
                if (mListener != null) {
                    mListener.onDenied(deniedPermissions);
                }
            }
        }
    }

    public interface OnPermissionListener {
        /**
         * 所有权限已同意
         */
        void onGranted();

        /**
         * 权限被拒绝（被拒绝权限、被禁用权限）
         *
         * @param deniedPermissions 被拒绝的权限列表（不为空，长度大于0）
         */
        void onDenied(@NonNull List<String> deniedPermissions);
    }
}