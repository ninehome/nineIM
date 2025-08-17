package com.watayouxiang.webrtclib.tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author : TaoWang
 * date : 2020/6/1
 * desc :
 */
public class ScreenTool {

    @TargetApi(17)
    public static @Nullable
    DisplayMetrics getDisplayMetrics(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics;
        }
        return null;
    }

}
