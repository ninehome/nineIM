package com.sensetime.liveness.silent;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sensetime.liveness.silent.interfaces.FaceCallBackFlag;
import com.sensetime.liveness.silent.ui.camera.SenseCamera;
import com.sensetime.liveness.silent.ui.camera.DialogSenseCameraPreview;
import com.sensetime.sample.common.R;
import com.sensetime.senseid.sdk.liveness.silent.SilentLivenessApi;
import com.sensetime.senseid.sdk.liveness.silent.common.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;


abstract class AbstractSilentLivenessDialog extends Activity
        implements Camera.PreviewCallback, DialogSenseCameraPreview.StartListener {
    public static final String FILES_PATH = Environment.getExternalStorageDirectory().getPath() + "/sensetime/";
    public static final String LICENSE_FILE_NAME = "SenseID_Liveness_Silent.lic";
    public static final String MODEL_FILE_NAME = "SenseID_Silent_Liveness.model";
    public static final String FILE_IMAGE = FILES_PATH + "silent_liveness/silent_liveness_image.jpg";

    protected ImageView mNoticeImage = null;
    protected View mLoadingView = null;
    protected TextView mNoteTextView = null;

    protected DialogSenseCameraPreview mCameraPreviewView = null;
    protected SenseCamera mSenseCamera;

    protected boolean mInputData = false;

    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (!checkPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.e("Permission","权限不足");
            return;
        }

        setContentView(R.layout.common_activity_liveness_silent_dialog);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;


        mNoteTextView = (TextView) findViewById(R.id.linkface_txt_note);
        mNoteTextView.setText(R.string.common_tracking_missed);
        mLoadingView = findViewById(R.id.pb_loading);
        mNoticeImage = (ImageView) findViewById(R.id.img_notice);
        mNoteTextView.setText(R.string.common_tracking_missed);
        mNoticeImage.setImageResource(R.drawable.common_ic_notice_silent);

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SilentLivenessDialog.FaceActivityCallBack.mFaceCallBack.callback(FaceCallBackFlag.CANCEL,"",null);
            }
        });
        mCameraPreviewView = (DialogSenseCameraPreview) findViewById(R.id.camera_preview);
        this.mCameraPreviewView.setStartListener(this);
        mSenseCamera = new SenseCamera.Builder(mContext).setFacing(SenseCamera.CAMERA_FACING_FRONT)
//                .setRequestedPreviewSize(480, 640)
//                .setRequestedPreviewSize(200, 200)
                .build();

        File dir = new File(FILES_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File image = new File(FILE_IMAGE);
        if (image.exists()) {
            //noinspection ResultOfMethodCallIgnored
            image.delete();
        }

        FileUtil.copyAssetsToFile(mContext, LICENSE_FILE_NAME, FILES_PATH + LICENSE_FILE_NAME);
        FileUtil.copyAssetsToFile(mContext, MODEL_FILE_NAME, FILES_PATH + MODEL_FILE_NAME);



    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            this.mCameraPreviewView.start(this.mSenseCamera);
            this.mSenseCamera.setOnPreviewFrameCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        this.mInputData = false;

        SilentLivenessApi.cancel();

        mLoadingView.setVisibility(View.GONE);

        this.mCameraPreviewView.stop();
        this.mCameraPreviewView.release();
    }



    @Override
    public void onFail() {
        finish();
    }

    protected boolean checkPermission(String... permissions) {
        if (permissions == null || permissions.length < 1) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String permission : permissions) {
            if (mContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public  int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
