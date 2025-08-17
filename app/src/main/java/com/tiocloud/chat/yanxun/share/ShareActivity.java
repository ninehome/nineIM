package com.tiocloud.chat.yanxun.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.widget.dialog.base.ShowSaveDialog;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserCurrReq;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

import java.io.File;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ShareActivity extends TioActivity {

    ImageView ivQrCode;
    TextView tvInviteCode;
    TioImageView ivAvatar;

    public static void start(Context context) {
        Intent starter = new Intent(context, ShareActivity.class);
        context.startActivity(starter);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_share);

        ivQrCode = findViewById(R.id.iv_qrcode);
        tvInviteCode = findViewById(R.id.tv_invitecode);
        ivAvatar = findViewById(R.id.iv_avatar);

        if (TioAccount.inviteEnable){
        }else {
            findViewById(R.id.tv2).setVisibility(View.GONE);
            findViewById(R.id.tv_invitecode).setVisibility(View.GONE);
            findViewById(R.id.cv).setVisibility(View.INVISIBLE);
        }
        initData();
        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowSaveDialog showSaveDialog = new ShowSaveDialog(ShareActivity.this){
                    @Override
                    protected void onClick(ShowSaveDialog groupOperDialog, View v) {
                        groupOperDialog.dismiss();
                        if (v.getId() == R.id.tv_save){
                            save();
                        }
                    }
                };
                showSaveDialog.show();
            }
        });
    }

    private void initData() {
        new UserCurrReq().setCancelTag(this).get(new TioCallback<UserCurrResp>() {
            @Override
            public void onTioSuccess(UserCurrResp userCurrResp) {
                if (userCurrResp == null){
                    return;
                }
                tvInviteCode.setText(/*String.valueOf(TioDBPreferences.getCurrUid())*/userCurrResp.invitecode);
//                ivAvatar.tio_roundAvatar(userCurrResp.avatar);
                ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Bitmap>() {
                    @Override
                    public Bitmap doInBackground() throws Throwable {
//                Bitmap logoBitmap = BitmapFactory.decodeResource(Utils.getApp().getResources(), R.drawable.ic_launcher);
                        return QRCodeEncoder.syncEncodeQRCode(
                                String.format(TioConfig.share_url, userCurrResp.id, userCurrResp.invitecode),
                                BGAQRCodeUtil.dp2px(Utils.getApp(), 227),
                                Color.BLACK,
                                Color.TRANSPARENT,
                                null);
                    }

                    @Override
                    public void onSuccess(Bitmap result) {
                        if (result == null) {
                            TioToast.showShort(getString(R.string.qrcode_creat_fail));
                            return;
                        }
                        ivQrCode.setImageBitmap(result);
//                getView().onMyQRCodeGet(result);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        super.onFail(t);
                        TioToast.showShort(getString(R.string.qrcode_creat_fail));
                    }
                });
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }


    public void save() {
//        view.setVisibility(View.GONE);
        Bitmap createFromViewBitmap = QMUIDrawableHelper.createBitmapFromView(ivQrCode.getRootView());
        File file = ImageUtils.save2Album(createFromViewBitmap, Bitmap.CompressFormat.PNG);
        if (file != null) {
            TioToast.showShort(getString(R.string.save_abulm_success));
        } else {
            TioToast.showShort(getString(R.string.download_fail));
        }
//        view.setVisibility(View.VISIBLE);
    }
}
