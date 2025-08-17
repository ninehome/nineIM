package com.watayouxiang.httpclient.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.blankj.utilcode.util.Utils;

import java.math.BigDecimal;

public class DeviceUtils {
    /**
     * 获取运营商
     * 需要权限：{@link android.Manifest.permission#READ_PHONE_STATE}
     *
     * @param context 上下文
     * @return 运营商
     */
    @SuppressLint("MissingPermission")
    public static String getOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = null;
        try {
            operator = telephonyManager.getSubscriberId();
        } catch (Exception ignored) {
        }
        if (operator != null) {
            if (operator.startsWith("46000") || operator.startsWith("46002") || operator.startsWith("46007")) {
                operator = "中国移动";
            } else if (operator.startsWith("46001") || operator.startsWith("46006")) {
                operator = "中国联通";
            } else if (operator.startsWith("46003")) {
                operator = "中国电信";
            } else {
                operator = "unknown";
            }
        }
        return operator;
    }

    /**
     * 获取手机分辨率
     *
     * @param context 上下文
     * @return 手机分辨率
     */
    public static String getResolution(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return width + "," + height;
    }

    /**
     * 获得IMEI
     * 需要权限：{@link android.Manifest.permission#READ_PHONE_STATE}
     *
     * @param context 上下文
     * @return IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String Imei = null;
        try {
            Imei = telephonyManager.getDeviceId();
        } catch (Exception ignored) {
        }
        return Imei;
    }

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String getAppVersion(Context context) {
        String appVersion = null;
        try {
            appVersion = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return appVersion;
    }

    /**
     * 获得手机型号
     *
     * @return 手机型号
     */
    public static String getDeviceInfo() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 获取屏幕英寸大小
     *
     * @param context Context
     * @return 屏幕英寸大小
     */
    public static String getSize(Context context) {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return null;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (dm == null) return null;

        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        //保留1位小数
        return BigDecimal.valueOf(Math.sqrt(x + y))
                .setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue() + "";
    }
}