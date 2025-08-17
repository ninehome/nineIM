package com.watayouxiang.qrcode.feature.qrcode_group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.listener.OnTioClickListener;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.qrcode.R;
import com.watayouxiang.qrcode.databinding.ActivityQrcodeGroupBinding;
import com.watayouxiang.qrcode.feature.qrcode_decoder.QRCodeDecoderActivity;
import com.watayouxiang.qrcode.feature.qrcode_group.mvp.Contract;
import com.watayouxiang.qrcode.feature.qrcode_group.mvp.Presenter;

import java.io.File;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/17
 *     desc   : 群二维码
 * </pre>
 */
public class GroupQRCodeActivity extends EasyActivity<ActivityQrcodeGroupBinding> implements Contract.View {

    private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private Presenter presenter;

    public static void start(Context context, String groupId) {
        Presenter.checkPermission(context,() -> {
            Intent starter = new Intent(context, GroupQRCodeActivity.class);
            starter.putExtra(KEY_GROUP_ID, groupId);
            context.startActivity(starter);
        });
    }

    private String getGroupId() {
        return getIntent().getStringExtra(KEY_GROUP_ID);
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected Integer getStatusBarColor() {
        return Color.TRANSPARENT;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#498FF6");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qrcode_group;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this);
        presenter.init(getGroupId(),this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void resetUI() {
        binding.titleBar.getIvRight().setOnClickListener(view -> {
            if (ClickUtils.isViewSingleClick(view)) {
                QRCodeDecoderActivity.start(GroupQRCodeActivity.this);
            }
        });
        binding.ivAvatar.tio_roundAvatar(null);
        binding.tvNick.setText("");
        binding.tvTip.setText(getString(R.string.scan_add_cahat));
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
        binding.tvShare.setOnClickListener(new OnTioClickListener() {
            @Override
            public void onSingleClick(View view) {
                presenter.showShareDialog(getActivity(), binding.clQrcode);
            }
        });
    }

    @Override
    public void onGroupInfoResp(GroupInfoResp resp) {
        GroupInfoResp.Group group = resp.group;
        if (group == null) return;
        binding.ivAvatar.tio_roundAvatar(HttpCache.getResUrl(group.avatar));
        binding.myAvator.loadStatic(HttpCache.getResUrl(group.avatar));
        binding.tvNick.setText(StringUtils.null2Length0(group.name));
    }

    @Override
    public void onGroupQRCodeGet(Bitmap result) {
        binding.ivMyQRCode.setImageBitmap(result);
    }
}
