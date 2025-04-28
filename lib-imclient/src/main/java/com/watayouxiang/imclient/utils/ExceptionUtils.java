package com.watayouxiang.imclient.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    /**
     * 获取错误信息
     *
     * @param throwable 错误
     * @return 错误信息
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) return null;
        String throwableMsg;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        throwableMsg = sw.toString();
        pw.close();
        return throwableMsg;
    }
}
