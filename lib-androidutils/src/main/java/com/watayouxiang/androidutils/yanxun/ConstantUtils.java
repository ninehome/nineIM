package com.watayouxiang.androidutils.yanxun;

import android.content.Context;

import com.watayouxiang.androidutils.R;

public class ConstantUtils {
    public static boolean checkCode = false;

    public static boolean checkEmail = false;

    public static Context context;

//    public static List<ConfigResp.Website> websites = new ArrayList<>();

    /**
     *
     * @param type 1注册 2-登录
     * @return
     */
    public static String getAccountInputHint(int type){
        String appName = context.getString(R.string.huo)+context.getResources().getString(R.string.my_name)+context.getString(R.string.hao);
        if (context != null){
            appName = context.getString(R.string.huo)+context.getResources().getString(R.string.my_name)+context.getString(R.string.hao);
        }
        if (type == 1){
            appName = "";
        }
        if (checkEmail){
            return context.getString(R.string.input_email)+appName;
        }
        return checkCode ? context.getString(R.string.please_input_mobile)+appName:context.getString(R.string.account_zucheng)+appName;
    }

    public static String getCodeLoginVisible(){
        return checkCode ? "visible" : "invisible";
    }
}
