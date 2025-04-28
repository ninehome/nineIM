package com.watayouxiang.qrcode.feature.qrcode_decoder;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.util.BrowserUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.utils.HttpUrlUtils;
import com.watayouxiang.qrcode.QRCodeBridge;
import com.watayouxiang.qrcode.R;
import com.watayouxiang.qrcode.TioQRCode;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/31
 *     desc   : 二维码处理器
 * </pre>
 */
public class QRCodeHandler {

    // https://a.app.qq.com/o/simple.jsp?pkgname=com.tiocloud.chat&uid=xxx
    // https://a.app.qq.com/o/simple.jsp?pkgname=com.tiocloud.chat&g=xxx&applyuid=xxx
    public static final String QR_CODE_BASE_URL = "https://a.app.xx.com/o/simple.jsp";
    public static final String appPackageName = AppUtils.getAppPackageName();

    public static final String KEY_PKG_NAME = "pkgname";
    public static final String KEY_UID = "uid";
    public static final String KEY_GROUP_ID = "g";
    public static final String KEY_APPLY_UID = "applyuid";

    /**
     * 处理扫描结果
     *
     * @param result    扫描结果
     * @param activity  Activity
     * @param zxingview ZXingView
     */
    public static void handleQRCode(String result, Activity activity, ZXingView zxingview) {
        if (result == null) {
            TioToast.showShort(activity.getString(R.string.shibieshibai));
            zxingview.startSpot();// 开始识别
            return;
        }

        Map<String, String> tioParams = getTioParams(result);
        if (!tioParams.isEmpty()) {
            handleTioQRCode(tioParams, activity, zxingview);
        } else {
            handleOtherQRCode(activity, result);
        }
    }

    // ====================================================================================
    // 其他二维码
    // ====================================================================================

    private static void handleOtherQRCode(Activity activity, String result) {
        BrowserUtils.openOsBrowser(activity, result);
        activity.finish();
    }

    // ====================================================================================
    // 谭聊二维码
    // ====================================================================================

    /**
     * 获取谭聊参数
     *
     * @param qrCodeUrl 二维码
     * @return 是谭聊二维码，返回参数；否则返回空集合
     */
    private static Map<String, String> getTioParams(@NonNull String qrCodeUrl) {
        if (qrCodeUrl.startsWith(QR_CODE_BASE_URL)) {
            Map<String, String> map = HttpUrlUtils.urlSplit(qrCodeUrl);
            String pkgname = map.get(KEY_PKG_NAME);
            if (true || StringUtils.equals(pkgname, appPackageName)) {
                String uid = map.get(KEY_UID);
                String groupId = map.get(KEY_GROUP_ID);
                String applyUid = map.get(KEY_APPLY_UID);
                if (uid != null) {
                    // 个人名片
                    return map;
                } else if (groupId != null && applyUid != null) {
                    // 群名片
                    return map;
                }
            }
        }
        return new HashMap<>(0);
    }

    /**
     * 处理谭聊二维码
     *
     * @param params    参数集合
     * @param activity  Activity
     * @param zxingview ZXingView
     */
    private static void handleTioQRCode(Map<String, String> params, Activity activity, ZXingView zxingview) {
        String uid = params.get(KEY_UID);
        String groupId = params.get(KEY_GROUP_ID);
        String applyUid = params.get(KEY_APPLY_UID);
        if (uid != null) {
            // 个人名片
            handleTioUserQRCode(activity, uid);
        } else if (groupId != null && applyUid != null) {
            // 群名片
            handleTioGroupQRCode(activity, groupId, applyUid);
        }
    }

    private static void handleTioGroupQRCode(Activity activity, String groupId, String applyUid) {
        TioQRCode.getBridge().openGroupCard(activity, groupId, applyUid, new QRCodeBridge.OpenGroupCardListener() {
            @Override
            public void onOpenGroupCardSuccess() {
                activity.finish();
            }

            @Override
            public void onOpenGroupCardError() {
                activity.finish();
            }
        });
    }

    private static void handleTioUserQRCode(Activity activity, String uid) {
        TioQRCode.getBridge().openP2PCard(activity, uid);
        activity.finish();
    }
}
