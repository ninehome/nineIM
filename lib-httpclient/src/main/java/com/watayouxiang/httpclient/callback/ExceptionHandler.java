package com.watayouxiang.httpclient.callback;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.model.BaseResp;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/23
 *     desc   :
 * </pre>
 */
class ExceptionHandler {
    /**
     * 获取异常信息
     */
    public static <Data> String handleException(Response<BaseResp<Data>> response) {
        // 没有错误
        Throwable e = response.getException();
        if (e == null) return "没有错误";

        String msg = "未知错误-:"+e.getMessage();
        if (e instanceof HttpException) {
            int code = response.code();
            if (code != -1) {
                msg = code + "错误";
            } else {
                msg = "网络错误";
            }
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            msg = "无网络";
        } else if (e instanceof ConnectTimeoutException
                || e instanceof java.net.SocketTimeoutException) {
            msg = "连接超时";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            msg = "证书验证异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            msg = "解析异常";
            e.printStackTrace();
//            if (UtilsBridge.isSDCardEnableByEnvironment()
//                    && Utils.getApp().getExternalFilesDir(null) != null)
//                dirPath = Utils.getApp().getExternalFilesDir(null) + FILE_SEP + "crash" + FILE_SEP;
//            else {
//                dirPath = Utils.getApp().getFilesDir() + FILE_SEP + "crash" + FILE_SEP;
//            }
//            final String time = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
//            CrashUtils.CrashInfo info = new CrashUtils.CrashInfo(time, e);
//            final String crashFile = dirPath + time + ".txt";
//            UtilsBridge.writeFileFromString(crashFile, info.toString(), true);
//
//            if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
//                DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
//            }
        }
        return msg;
    }
}
