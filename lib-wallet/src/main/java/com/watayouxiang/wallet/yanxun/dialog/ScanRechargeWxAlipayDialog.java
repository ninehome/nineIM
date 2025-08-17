package com.watayouxiang.wallet.yanxun.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ehking.sdk.wepay.utlis.ToastUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ScanRechargeReq;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.utisl.FileUtil;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

/**
 * 支付宝、微信二维码弹窗
 */
public class ScanRechargeWxAlipayDialog extends Dialog {
    private ImageView mCloseIv;
    private TextView mTipTv;
    private TextView mConfirmTv, mCancelTv;
    private TioImageView mCodeIv;
    private TextView mSaveTv;

    private Activity mContext;
    private int type;
    private String money, url;
//    private Bitmap bitmap;

    public ScanRechargeWxAlipayDialog(Activity context, int type, String money, String url) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.type = type;
        this.money = money;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scan_rechrage_wx_alipay);
        initView();
        initData();
        initEvent();

        // 屏蔽back键
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.9);
            window.setAttributes(lp);
            window.setGravity(Gravity.CENTER);
        }
    }

    private void initView() {
        mCloseIv = findViewById(R.id.close_iv);
        mTipTv = findViewById(R.id.tip_tv);
        mConfirmTv = findViewById(R.id.confirm);
        mCancelTv = findViewById(R.id.cancel);
        mCodeIv = findViewById(R.id.code_iv);
        mSaveTv = findViewById(R.id.save_tv);
    }

    private void initData() {
        mTipTv.setText(mContext.getString(R.string.scan_recharge_wx_alipay_tip1
                , type == 1 ? mContext.getString(R.string.wechat) : mContext.getString(R.string.alipay)
                , money
                , String.valueOf(TioDBPreferences.getCurrUid())));
        mCodeIv.load(url);
//        ImageLoadHelper.loadBitmapDontAnimateWithPlaceHolder(
//                mContext,
//                url,
//                R.drawable.avatar_normal,
//                R.drawable.avatar_normal,
//                b -> {
//                    bitmap = b;
//                    mCodeIv.setImageBitmap(bitmap);
//                }, e -> {
//                    // 图片加载失败
//                }
//        );
    }

    private void initEvent() {
        mCloseIv.setOnClickListener(v -> dismiss());
        mSaveTv.setOnClickListener(v -> {
                Bitmap bitmap = mCodeIv.getBitmap();
                FileUtil.saveImageToGallery2(mContext, bitmap, true);
        });
        mTipTv.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("Label", String.valueOf(TioDBPreferences.getCurrUid()));
                clipboardManager.setPrimaryClip(clipData);
                ToastUtils.showShort(mContext.getString(R.string.label_communication) + mContext.getString(R.string.tip_copied_to_clipboard));
            }
        });
        mConfirmTv.setOnClickListener(v -> recharge());
        mCancelTv.setOnClickListener(v -> dismiss());
    }

    private void recharge() {
        SelectionFrame selectionFrame = new SelectionFrame(mContext);
        selectionFrame.setSomething(null, mContext.getString(R.string.already_pay_ask),
                null, mContext.getString(R.string.already_pay), new SelectionFrame.OnSelectionFrameClickListener() {
                    @Override
                    public void cancelClick() {

                    }

                    @Override
                    public void confirmClick() {
                        ScanRechargeReq scanRechargeReq = new ScanRechargeReq(type, (int)(Float.parseFloat(money)*100));
                        scanRechargeReq.setCancelTag(this);
                        scanRechargeReq.get(new TioCallback<String>() {
                            @Override
                            public void onTioSuccess(String commonResp) {
                                ToastUtils.showShort(mContext.getString(R.string.wait_server_notify));
                                selectionFrame.dismiss();
                                ScanRechargeWxAlipayDialog.this.dismiss();
                                mContext.finish();
                            }

                            @Override
                            public void onTioError(String msg) {
                                ToastUtils.showShort(msg);
                            }
                        });
//                        DialogHelper.showDefaulteMessageProgressDialog(mContext);
//                        Map<String, String> params = new HashMap<>();
//                        params.put("money", money);
//                        params.put("type", String.valueOf(type));// 支付方式 1.微信 2.支付宝 3.银行卡
//
//                        HttpUtils.get().url(CoreManager.requireConfig(mContext).MANUAL_PAY_RECHARGE)
//                                .params(params)
//                                .build()
//                                .execute(new BaseCallback<ScanRecharge>(ScanRecharge.class) {
//
//                                    @Override
//                                    public void onResponse(ObjectResult<ScanRecharge> result) {
//                                        DialogHelper.dismissProgressDialog();
//                                        if (Result.checkSuccess(mContext, result)) {
//                                            ToastUtil.showToast(mContext, mContext.getString(R.string.wait_server_notify));
//                                            selectionFrame.dismiss();
//                                            com.sk.weichat.ui.dialog.money.ScanRechargeWxAlipayDialog.this.dismiss();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(Call call, Exception e) {
//                                        DialogHelper.dismissProgressDialog();
//                                        ToastUtil.showErrorNet(mContext);
//                                    }
//                                });
                    }
                });
        selectionFrame.setCancelable(false);
        selectionFrame.setAutoDismiss(false);
        selectionFrame.show();
    }
}
