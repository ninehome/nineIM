package com.huantansheng.easyphotos.utils.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.huantansheng.easyphotos.constant.Code;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时权限工具类
 * Created by huan on 2017/7/27.
 */

public class PermissionUtil {

    public interface PermissionCallBack {
        void onSuccess();

        void onShouldShow();

        void onFailed();
    }

    /**
     * 检查并请求权限，同时说明权限使用目的
     */
    public static boolean checkAndRequestPermissionsInActivity(Activity cxt,
                                                               String... checkPermissions) {
        boolean isHas = true;
        List<String> permissions = new ArrayList<>();
        int size = checkPermissions.length;
        for (int i = 0; i < size; i++) {
            String checkPermission = checkPermissions[i];
            if (PermissionChecker.checkSelfPermission(cxt, checkPermission) != PermissionChecker.PERMISSION_GRANTED) {
                isHas = false;
                permissions.add(checkPermission);
            }
        }
        if (!isHas) {
            String[] p = permissions.toArray(new String[permissions.size()]);
            showPermissionPurposeDialog(cxt, Code.REQUEST_PERMISSION, p);
        }
        return isHas;
    }

    /**
     * 显示权限使用目的对话框
     */
    private static void showPermissionPurposeDialog(final Activity cxt, final int requestCode, final String... permissions) {
        StringBuilder message = new StringBuilder("我们需要以下权限来提供更好的服务：\n");
        for (String permission : permissions) {
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
        new AlertDialog.Builder(cxt)
               .setTitle("权限申请")
               .setMessage(message.toString())
               .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissionsInActivity(cxt, requestCode, permissions);
                    }
                })
               .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 可添加拒绝后的处理逻辑
                    }
                })
               .setCancelable(false)
               .show();
    }

    private static void requestPermissionsInActivity(Activity cxt, int requestCode,
                                                     String... permissions) {
        ActivityCompat.requestPermissions(cxt, permissions, requestCode);
    }

    public static void onPermissionResult(Activity cxt, @NonNull String[] permissions,
                                          @NonNull int[] grantResults,
                                          PermissionCallBack listener) {
        int length = grantResults.length;
        List<Integer> positions = new ArrayList<>();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    positions.add(i);
                }
            }
        }
        // 使用 isEmpty() 检查集合是否为空
        if (positions.isEmpty()) { 
            listener.onSuccess();
            return;
        }
        progressNoPermission(cxt, listener, permissions, positions, 0);
    }

    private static void progressNoPermission(Activity cxt, PermissionCallBack listener,
                                             String[] permissions, List<Integer> positions, int i) {
        int index = positions.get(i);
        if (ActivityCompat.shouldShowRequestPermissionRationale(cxt, permissions[index])) {
            listener.onShouldShow();
            return;
        }
        if (i < positions.size() - 1) {
            i++;
            progressNoPermission(cxt, listener, permissions, positions, i);
            return;
        }
        listener.onFailed();
    }
}
