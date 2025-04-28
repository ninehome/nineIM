package com.tiocloud.chat.feature.session.common.action.model.base;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.session.common.SessionFragment;
import com.tiocloud.chat.mvp.upload.UploadContract;
import com.tiocloud.chat.mvp.upload.UploadPresenter;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.permission.dialog.AppSettingsDialog;
import com.watayouxiang.permission.helper.TaoFragmentPermissionHelper;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 */
public abstract class BaseUploadAction extends BaseAction implements UploadContract.View {

    public transient SessionFragment fragment;
//    public transient Activity activity;
    private transient UploadPresenter uploadPresenter;
    private TaoFragmentPermissionHelper permissionHelper;

    public BaseUploadAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    public UploadPresenter getUploadPresenter() {
        if (uploadPresenter == null) {
            uploadPresenter = new UploadPresenter(this);
        }
        return uploadPresenter;
    }

    public TaoFragmentPermissionHelper getPermissionHelper() {
        if (permissionHelper == null) {
            permissionHelper = new TaoFragmentPermissionHelper(fragment);
        }
        return permissionHelper;
    }

    // ====================================================================================
    // onActivityResult
    // ====================================================================================

    /**
     * 1、在子类中调用startActivityForResult时，requestCode必须用makeRequestCode封装一遍，否则不能再onActivityResult中收到结果。
     * 2、requestCode仅能使用最低8位。
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // 权限弹窗回调
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // ====================================================================================
    // 资源释放
    // ====================================================================================

    public void release() {
        LogUtils.d("zlb===release");
        fragment = null;
        if (uploadPresenter != null) {
            uploadPresenter.detachView();
            uploadPresenter = null;
        }
        permissionHelper = null;
    }

    @Nullable
    @Override
    public Activity getActivity() {
        if (fragment != null) {
            return fragment.getActivity();
        }
        return null;
    }
}
