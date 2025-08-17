package com.watayouxiang.wallet.yanxun.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ScanRechargeReq;
import com.watayouxiang.httpclient.model.response.CommonResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

/**
 * 银行卡充值弹窗
 */
public class ScanRechargeBandDialog extends Dialog {
    private ImageView mCloseIv;
    private TextView mTipTv;
    private TextView mConfirmTv, mCancelTv;
    private TextView mTv1, mTv2, mTv3;
    private ImageView mIv1, mIv2, mIv3;

    private Activity mContext;
    private String name, card, bandName;
    private String money;

    public ScanRechargeBandDialog(Activity context, String name, String card, String bandName, String money) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.name = name;
        this.card = card;
        this.bandName = bandName;
        this.money = money;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scan_rechrage_band);
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
        mTv1 = findViewById(R.id.tv1);
        mTv2 = findViewById(R.id.tv2);
        mTv3 = findViewById(R.id.tv3);
        mIv1 = findViewById(R.id.iv1);
        mIv2 = findViewById(R.id.iv2);
        mIv3 = findViewById(R.id.iv3);
    }

    private void initData() {
        mTipTv.setText(mContext.getString(R.string.scan_recharge_band_tip1
                , String.valueOf(TioDBPreferences.getCurrUid())));
        mTv1.setText(name);
        mTv2.setText(card);
        mTv3.setText(bandName);
    }

    private void initEvent() {
        mCloseIv.setOnClickListener(v -> dismiss());
        mIv1.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("Label", name);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtils.showShort(mContext.getString(R.string.tip_copied_to_clipboard));
            }
        });
        mIv2.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("Label", card);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtils.showShort(mContext.getString(R.string.tip_copied_to_clipboard));
            }
        });
        mIv3.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("Label", bandName);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtils.showShort(mContext.getString(R.string.tip_copied_to_clipboard));
            }
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
                        ScanRechargeReq scanRechargeReq = new ScanRechargeReq(3, (int)(Float.parseFloat(money)*100));
                        scanRechargeReq.setCancelTag(this);
                        scanRechargeReq.get(new TioCallback<String>() {
                            @Override
                            public void onTioSuccess(String commonResp) {
                                ToastUtils.showShort(mContext.getString(R.string.wait_server_notify));
                                selectionFrame.dismiss();
                                ScanRechargeBandDialog.this.dismiss();
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
//                        params.put("type", String.valueOf(3));// 支付方式 1.微信 2.支付宝 3.银行卡
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
//                                            com.sk.weichat.ui.dialog.money.ScanRechargeBandDialog.this.dismiss();
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
