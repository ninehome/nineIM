package com.watayouxiang.webrtclib.log;

import android.content.Context;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/6/1
 * desc :
 */
public class LogTool {
    // ====================================================================================
    // createRtcEventLogOutputFile
    // ====================================================================================

    private static final String RTCEVENTLOG_OUTPUT_DIR_NAME = "rtc_event_log";

    public static File createRtcEventLogOutputFile(Context appContext) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmm_ss", Locale.getDefault());
        Date date = new Date();
        final String outputFileName = "event_log_" + dateFormat.format(date) + ".log";
        return new File(appContext.getDir(RTCEVENTLOG_OUTPUT_DIR_NAME, Context.MODE_PRIVATE), outputFileName);
    }
}
