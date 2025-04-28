package com.tiocloud.chat.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.watayouxiang.imclient.utils.MD5Utils;
import com.watayouxiang.permission.TaoPermissionUtils;
import com.watayouxiang.permission.dialog.AppSettingsDialog;
import com.watayouxiang.permission.helper.TaoActivityPermissionHelper;
import com.watayouxiang.permission.helper.TaoPermissionListener;

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
        mPermissionHelper.requestPermissions(mPermissions);
    }

    /**
     * 必须调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
