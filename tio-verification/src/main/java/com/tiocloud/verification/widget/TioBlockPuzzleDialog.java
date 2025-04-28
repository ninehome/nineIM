package com.tiocloud.verification.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.tiocloud.verification.R;
import com.tiocloud.verification.model.Point;
import com.tiocloud.verification.utils.AESUtil;
import com.tiocloud.verification.utils.ImageUtil;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.AnjiCaptchaCheckReq;
import com.watayouxiang.httpclient.model.request.AnjiCaptchaGetReq;
import com.watayouxiang.httpclient.model.response.AnjiCaptchaCheckResp;
import com.watayouxiang.httpclient.model.response.AnjiCaptchaGetResp;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class TioBlockPuzzleDialog extends Dialog {

    private final Context mContext;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private DragImageView dragView;
    private String token;
    private String key;

    public TioBlockPuzzleDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        setContentView(R.layout.vercode_dialog_block_puzzle_tio);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ViewGroup.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 9 / 10;
        getWindow().setAttributes((WindowManager.LayoutParams) lp);
        setCanceledOnTouchOutside(false);//点击外部Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        loadCaptcha();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TioHttpClient.cancel(TioBlockPuzzleDialog.this);
    }

    private void initView() {
        TextView tvDelete = findViewById(R.id.tv_delete);
        ImageView tvRefresh = findViewById(R.id.tv_refresh);
        dragView = findViewById(R.id.dragView);

        tvDelete.setOnClickListener(v -> dismiss());
        tvRefresh.setOnClickListener(v -> loadCaptcha());

        Bitmap bitmap = ImageUtil.getBitmap(getContext(), R.drawable.vercode_bg_default);
        dragView.setUp(bitmap, bitmap);
        dragView.setSBUnMove(false);
        dragView.hideFlashImage();
    }

    private void loadCaptcha() {
        new AnjiCaptchaGetReq().setCancelTag(TioBlockPuzzleDialog.this).post(new TioCallback<AnjiCaptchaGetResp>() {
            @Override
            public void onTioSuccess(AnjiCaptchaGetResp data) {
                String baseImageBase64 = data.getOriginalImageBase64();//背景图片
                String slideImageBase64 = data.getJigsawImageBase64();//滑动图片
                token = data.getToken();
                key = data.getSecretKey();

                dragView.setUp(ImageUtil.base64ToBitmap(baseImageBase64), ImageUtil.base64ToBitmap(slideImageBase64));
                dragView.setSBUnMove(true);
                dragView.setDragListenner(position -> checkCaptcha(position));
            }

            @Override
            public void onTioError(String msg) {
                dragView.setSBUnMove(false);
            }
        });
    }

    private void checkCaptcha(double sliderXMoved) {
        Point point = new Point();
        point.setY(5.0);
        point.setX(sliderXMoved);
        String pointStr = new Gson().toJson(point);

        new AnjiCaptchaCheckReq(token, AESUtil.encode(pointStr, key)).setCancelTag(TioBlockPuzzleDialog.this)
                .post(new TioCallback<AnjiCaptchaCheckResp>() {
                    @Override
                    public void onTioSuccess(AnjiCaptchaCheckResp data) {
                        dragView.ok();

                        if (mOnResultsListener != null) {
                            String result = token + "---" + pointStr;
                            mOnResultsListener.onResultsClick(AESUtil.encode(result, key));
                        }

                        handler.postDelayed(() -> dismiss(), 1500);
                    }

                    @Override
                    public void onTioError(String msg) {
                        dragView.fail();
                        //刷新验证码
                        loadCaptcha();
                    }
                });
    }

    private OnResultsListener mOnResultsListener;

    public interface OnResultsListener {
        void onResultsClick(String result);
    }

    public void setOnResultsListener(OnResultsListener mOnResultsListener) {
        this.mOnResultsListener = mOnResultsListener;
    }
}
