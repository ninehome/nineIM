package com.sensetime.liveness.silent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import com.sensetime.liveness.silent.interfaces.FaceCallBack;
import com.sensetime.liveness.silent.interfaces.FaceCallBackFlag;
import com.sensetime.sample.common.R;
import com.sensetime.senseid.sdk.liveness.silent.OnLivenessListener;
import com.sensetime.senseid.sdk.liveness.silent.SilentLivenessApi;
import com.sensetime.senseid.sdk.liveness.silent.common.type.PixelFormat;
import com.sensetime.senseid.sdk.liveness.silent.common.type.ResultCode;
import com.sensetime.senseid.sdk.liveness.silent.common.util.ImageUtil;
import com.sensetime.senseid.sdk.liveness.silent.type.FaceDistance;
import com.sensetime.senseid.sdk.liveness.silent.type.FaceOcclusion;
import com.sensetime.senseid.sdk.liveness.silent.type.FaceState;
import com.sensetime.senseid.sdk.liveness.silent.type.OcclusionState;

import java.util.List;

public class SilentLivenessDialog extends AbstractSilentLivenessDialog {

    private Bitmap  mBitmap = null;
    private OnLivenessListener mLivenessListener = new OnLivenessListener() {

        private long mLastStatusUpdateTime;

        @Override
        public void onInitialized() {
            mInputData = true;
        }

        @Override
        public void onStatusUpdate(final int faceState, final FaceOcclusion faceOcclusion, final int faceDistance) {
            if (SystemClock.elapsedRealtime() - this.mLastStatusUpdateTime < 300) {
                return;
            }

            if (faceState == FaceState.MISSED) {
                mNoteTextView.setText(R.string.common_tracking_missed);
                mNoticeImage.setImageResource(R.drawable.common_ic_notice_silent);
            } else if (faceDistance == FaceDistance.TOO_CLOSE) {
                mNoteTextView.setText(R.string.common_face_too_close);
                mNoticeImage.setImageResource(R.drawable.common_ic_closeto_silent);
            } else if (faceState == FaceState.OUT_OF_BOUND) {
                mNoteTextView.setText(R.string.common_tracking_out_of_bound);
                mNoticeImage.setImageResource(R.drawable.common_ic_notice_silent);
            } else if (faceState == FaceState.OCCLUSION) {
                final StringBuilder builder = new StringBuilder();
                boolean needComma = false;
                if (faceOcclusion.getBrowOcclusionState() == OcclusionState.OCCLUSION) {
                    builder.append(mContext.getString(R.string.common_tracking_covered_brow));
                    needComma = true;
                }
                if (faceOcclusion.getEyeOcclusionState() == OcclusionState.OCCLUSION) {
                    builder.append(needComma ? "、" : "");
                    builder.append(mContext.getString(R.string.common_tracking_covered_eye));
                    needComma = true;
                }
                if (faceOcclusion.getNoseOcclusionState() == OcclusionState.OCCLUSION) {
                    builder.append(needComma ? "、" : "");
                    builder.append(mContext.getString(R.string.common_tracking_covered_nose));
                    needComma = true;
                }
                if (faceOcclusion.getMouthOcclusionState() == OcclusionState.OCCLUSION) {
                    builder.append(needComma ? "、" : "");
                    builder.append(mContext.getString(R.string.common_tracking_covered_mouth));
                }

                mNoteTextView.setText(
                        mContext.getString(R.string.common_tracking_covered, builder.toString()));
                mNoticeImage.setImageResource(R.drawable.common_ic_notice_silent);
            } else if (faceDistance == FaceDistance.TOO_FAR) {
                mNoteTextView.setText(R.string.common_face_too_far);
                mNoticeImage.setImageResource(R.drawable.common_ic_faraway_silent);
            } else {
                mNoteTextView.setText(R.string.common_detecting);
                mNoticeImage.setImageResource(R.drawable.common_ic_detection_silent);
            }

            this.mLastStatusUpdateTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void onError(ResultCode resultCode) {
            mInputData = false;
            Toast.makeText(SilentLivenessDialog.this.getApplicationContext(),errorMessage(ActivityUtils.convertResultCode(resultCode)),Toast.LENGTH_SHORT).show();

            if (FaceActivityCallBack.mFaceCallBack==null){
                return;
            }
            FaceActivityCallBack.mFaceCallBack.callback(FaceCallBackFlag.INIT_FAIL,errorMessage(ActivityUtils.convertResultCode(resultCode)),null);
            finish();
        }

        @Override
        public void onDetectOver(ResultCode resultCode, byte[] result, List imageData, Rect faceRect) {
            mInputData = false;
            List<byte[]> imageResult = (List<byte[]>) imageData;

            if (imageResult != null && !imageResult.isEmpty()) {
                ImageUtil.saveBitmapToFile(
                        BitmapFactory.decodeByteArray(imageResult.get(0), 0, imageResult.get(0).length), FILE_IMAGE);
            }

            switch (resultCode) {
                case OK:
                    if (imageResult != null && !imageResult.isEmpty() && faceRect != null) {
                        SilentLivenessImageHolder.setImageData(imageResult.get(0), faceRect);
                        if (FaceActivityCallBack.mFaceCallBack==null){
                            return;
                        }
                        FaceActivityCallBack.mFaceCallBack.callback(FaceCallBackFlag.SUCCESS,"",FILE_IMAGE);
                    }
                    break;
                default:
//                    Log.e("test","default");
                    if (FaceActivityCallBack.mFaceCallBack==null){
                        return;
                    }
                    FaceActivityCallBack.mFaceCallBack.callback(FaceCallBackFlag.FAIL,"未能识别人脸",null);

                    break;
            }
            finish();
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        SilentLivenessApi.init(mContext, FILES_PATH + LICENSE_FILE_NAME,
                FILES_PATH + MODEL_FILE_NAME, mLivenessListener);
        SilentLivenessApi.setFaceDistanceRate(0.4F, 0.8F);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (!this.mInputData) {
            return;
        }
        int limitRectWidth = mCameraPreviewView.getWidth();
        int limitRectHeight = mCameraPreviewView.getHeight();

        ViewParent parent = mCameraPreviewView.getParent();

        if (parent != null) {
            limitRectWidth = ((View) parent).getWidth();
            limitRectHeight = ((View) parent).getHeight();
        }

        final Rect rect =
                new Rect(limitRectWidth / 6, limitRectHeight / 6, limitRectWidth / 6 * 5, limitRectHeight / 6 * 5);

        SilentLivenessApi.inputData(data, PixelFormat.NV21, this.mSenseCamera.getPreviewSize(),
                this.mCameraPreviewView.convertViewRectToPicture(rect), true, mSenseCamera.getRotationDegrees());
    }

    private String errorMessage(int resultCode) {

        int messageCode = -1;

        switch (resultCode){
            case Activity.RESULT_CANCELED:
            case ActivityUtils.RESULT_CODE_CAMERA_ERROR:
            case ActivityUtils.RESULT_CODE_NO_PERMISSIONS:
                messageCode = R.string.txt_error_canceled;
                break;
            case ActivityUtils.RESULT_CODE_TIMEOUT:
                messageCode = R.string.txt_error_timeout;
                break;
            case ActivityUtils.RESULT_CODE_MODEL_FILE_NOT_FOUND:
                messageCode = R.string.txt_error_model_not_found;
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_FILE_NOT_FOUND:
                messageCode = R.string.txt_error_license_not_found;
                break;
            case ActivityUtils.RESULT_CODE_CHECK_LICENSE_FAIL:
            case ActivityUtils.RESULT_CODE_SDK_VERSION_MISMATCH:
            case ActivityUtils.RESULT_CODE_PLATFORM_NOTSUPPORTED:
                messageCode = R.string.txt_error_license;
                break;
            case ActivityUtils.RESULT_CODE_CHECK_MODEL_FAIL:
                messageCode = R.string.txt_error_model;
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_EXPIRE:
                messageCode = R.string.txt_error_license_expire;
                break;
            case ActivityUtils.RESULT_CODE_LICENSE_PACKAGE_NAME_MISMATCH:
                messageCode = R.string.txt_error_license_package_name;
                break;
            case ActivityUtils.RESULT_CODE_WRONG_STATE:
                messageCode = R.string.txt_error_state;
                break;
            case ActivityUtils.RESULT_CODE_ERROR_API_KEY_SECRET:
                messageCode = R.string.error_api_key_secret;
                break;
            case ActivityUtils.RESULT_CODE_SERVER:
                messageCode = R.string.error_server;
                break;
            case ActivityUtils.RESULT_CODE_NETWORK_TIMEOUT:
                messageCode = R.string.txt_error_network_timeout;
                break;
            case ActivityUtils.RESULT_CODE_DETECT_FAIL:
                messageCode = R.string.txt_detect_fail;
                break;
            case ActivityUtils.RESULT_CODE_INVALID_ARGUMENTS:
                messageCode = R.string.invalid_arguments;
                break;
        }

        if (messageCode==-1){
            return "未知错误:-1";
        }else {
            return mContext.getString(messageCode);
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public static class FaceActivityCallBack{
        public static FaceCallBack mFaceCallBack;

    }
}