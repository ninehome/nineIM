package com.tiocloud.chat.test.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.watayouxiang.demoshell.ListActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.permission.TaoPermissionUtils;
import com.watayouxiang.permission.dialog.AppSettingsDialog;
import com.watayouxiang.permission.helper.TaoActivityPermissionHelper;
import com.watayouxiang.permission.helper.TaoPermissionListener;

import java.util.Collections;
import java.util.List;

public class PermissionTestActivity extends ListActivity {

    private List<String> mPermissions = Collections.singletonList(Manifest.permission.READ_PHONE_STATE);
    private TaoActivityPermissionHelper mPermissionHelper = new TaoActivityPermissionHelper(this);

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mPermissionHelper.setPermissionListener(new TaoPermissionListener() {
            @Override
            public void onGranted() {
                notifyGranted();
            }

            @Override
            public void onDenied(@NonNull List<String> deniedPermissions) {
                new AlertDialog.Builder(PermissionTestActivity.this)
                        .setMessage("无法获取相关权限，请开启权限。")
                        .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPermissionHelper.requestPermissions(mPermissions);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }

            @Override
            public void onDisabled(@NonNull List<String> disabledPermissions, @NonNull List<String> deniedPermissions) {
                new AppSettingsDialog.Builder(PermissionTestActivity.this)
                        .setRationale("无法获取相关权限，请前往应用设置开启权限。")
                        .setPositiveButton("去开启")
                        .build()
                        .show();
            }
        });
    }

    private void notifyGranted() {
        new AlertDialog.Builder(PermissionTestActivity.this)
                .setMessage("权限申请成功")
                .setPositiveButton("确定", null)
                .create()
                .show();
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addSection("建议申请 " + mPermissions.toString() + " 权限，否则服务端将获取不到 \"运营商\" 和 \"IMEI\" 信息。")
                .addClick("开始申请权限", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPermissionHelper.requestPermissions(mPermissions);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (TaoPermissionUtils.filterDeniedPermissions(PermissionTestActivity.this, mPermissions).isEmpty()) {
                notifyGranted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPermissionHelper = null;
    }
}
