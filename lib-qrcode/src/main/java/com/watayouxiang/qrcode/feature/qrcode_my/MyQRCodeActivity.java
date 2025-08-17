package com.watayouxiang.qrcode.feature.qrcode_my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.listener.OnTioClickListener;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.qrcode.R;
import com.watayouxiang.qrcode.databinding.ActivityQrcodeMyBinding;
import com.watayouxiang.qrcode.feature.qrcode_decoder.QRCodeDecoderActivity;
import com.watayouxiang.qrcode.feature.qrcode_my.mvp.Contract;
import com.watayouxiang.qrcode.feature.qrcode_my.mvp.Presenter;

import java.io.File;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/09
 *     desc   : 我的二维码
 * </pre>
 */
public class MyQRCodeActivity extends EasyActivity<ActivityQrcodeMyBinding> implements Contract.View {

    private Presenter presenter;

    public static void start(Context context) {
        Presenter.checkPermission(context,() -> {
            Intent starter = new Intent(context, MyQRCodeActivity.class);
            context.startActivity(starter);
        });
    }

    @Override
    protected View getStatusBarHolder() {
//        return binding.statusBar;
        return null;
    }

    @Override
    protected Integer getStatusBarColor() {
//        return Color.parseColor("#DBEAFF");
        return Color.WHITE;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qrcode_my;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        presenter = new Presenter(this);
        presenter.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void resetUI() {
        binding.titleBar.getIvRight().setOnClickListener(view -> {
            if (ClickUtils.isViewSingleClick(view)) {
                QRCodeDecoderActivity.start(MyQRCodeActivity.this);
            }
        });
        binding.ivAvatar.tio_roundAvatar(null);
        binding.tvNick.setText("");
        binding.tvTip.setText(getString(R.string.use)+getString(R.string.app_name)+getString(R.string.appsaoyisao));
        binding.tvDownload.setOnClickListener(new OnTioClickListener() {
            @Override
            public void onSingleClick(View view) {
                File png = presenter.saveQRCode2Album(binding.clQrcode);
                if (png != null) {
                    TioToast.showShort(getString(R.string.save_abulm_success));
                } else {
                    TioToast.showShort(getString(R.string.download_fail));
                }
            }
        });
    }

    @Override
    public void onUserCurrResp(UserCurrResp resp) {
        binding.myAvator.load(resp.avatar);
        binding.ivAvatar.tio_roundAvatar(HttpCache.getResUrl(resp.avatar));
        binding.tvNick.setText(StringUtils.null2Length0(resp.nick));
        binding.tvGedahao.setText(getString(R.string.app_name)+getString(R.string.hao)+"："+resp.id);
    }

    @Override
    public void onMyQRCodeGet(Bitmap result) {
        binding.ivMyQRCode.setImageBitmap(result);
    }
}
