package com.watayouxiang.wallet.widget.keyboard;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ToastUtils;
import com.ehking.crypt.core.java.utils.Md5Util;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGetWalletInfoReq;
import com.watayouxiang.httpclient.model.request.PayOpenWalletReq;
import com.watayouxiang.httpclient.model.response.PayGetWalletInfoResp;
import com.watayouxiang.imclient.utils.MD5Utils;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.TioWallet;

public class InputPwdUtils {
    public static void input(Activity context, Integer amount, OnEncryPasswordInputFinish onEncryPasswordInputFinish){
        hideSoftKeyboard(context);
        PopEnterPassword popEnterPassword = new PopEnterPassword(context);
        popEnterPassword.setTitle(context.getString(R.string.qingshuruzhifumima));
        popEnterPassword.setAmount(amount);
        popEnterPassword.setOnPasswordInputFinish(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                long t = System.currentTimeMillis();
                onEncryPasswordInputFinish.pwd(MD5Utils.getMd5(MD5Utils.getMd5(password)+t+ TioWallet.KEY), t);
            }
        });
        // 显示窗口
        popEnterPassword.showAtLocation(context.getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static String tmpPwd;
    public static void setPwdP(Activity activity, boolean first, boolean isOpen, OpenCallback openCallback){
        hideSoftKeyboard(activity);
        PopEnterPassword popEnterPassword = new PopEnterPassword(activity);
        if (first){
            tmpPwd = "";
            popEnterPassword.setTitle(activity.getString(R.string.qingshezhizhifumima));
        }else {
            popEnterPassword.setTitle(activity.getString(R.string.qingzaicishuruzhifumima));
        }
        popEnterPassword.setOnPasswordInputFinish(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                if (first){
                    tmpPwd = password;
                    setPwdP(activity, false, isOpen, openCallback);
                }else {
                    if (!password.equals(tmpPwd)){
                        ToastUtils.showShort(activity.getString(R.string.liangcimimabuyizhi));
                        setPwdP(activity, true, isOpen, openCallback);
                    }else {
                        if (isOpen){
                            openRequest(activity,MD5Utils.getMd5(password), openCallback);
                        }else {
                            openCallback.result(true, MD5Utils.getMd5(password));
                        }
                    }
                }
            }
        });
        // 显示窗口
        popEnterPassword.showAtLocation(activity.getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    private static void openRequest(Activity activity,String passwd, OpenCallback openCallback) {
        PayOpenWalletReq payGetWalletInfoReq = new PayOpenWalletReq();
        payGetWalletInfoReq.setPasswd(passwd);
        payGetWalletInfoReq.post(new TioCallback<PayGetWalletInfoResp>() {
            @Override
            public void onTioSuccess(PayGetWalletInfoResp payGetWalletInfoResp) {
                if (payGetWalletInfoResp == null){
                    openCallback.result(false, activity.getString(R.string.caozuoshibai));
                    return;
                }
                TioDBPreferences.saveCurrMoney(payGetWalletInfoResp.getAmount());
                TioDBPreferences.setOpenWalletFlag(true);
                openCallback.result(true, activity.getString(R.string.kaifuchenggong));
            }

            @Override
            public void onTioError(String msg) {
                openCallback.result(false, msg);
            }
        });

    }

}
