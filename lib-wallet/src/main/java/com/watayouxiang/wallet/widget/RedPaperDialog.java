package com.watayouxiang.wallet.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.dialog2.BaseBindingDialog;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRedpaperDialogBinding;
import com.watayouxiang.wallet.feature.paperdetail.PaperDetailActivity;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/11
 *     desc   : 未打开的红包
 * </pre>
 */
public class RedPaperDialog extends BaseBindingDialog<WalletRedpaperDialogBinding> {

    public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");
    public final ObservableField<String> gift = new ObservableField<>("恭喜发财，大吉大利");
    public final ObservableField<Boolean> showDetail = new ObservableField<>(true);
    private final RedPaperVo redPaperVo;

    private OnRedPaperListener onRedPaperListener;

    public RedPaperDialog(@NonNull Context context, RedPaperVo redPaperVo) {
        super(context);
        setAnimation(R.style.tio_dialog_anim);
        this.redPaperVo = redPaperVo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setView(this);
        if (redPaperVo == null) return;

        binding.ivAvatar.loadBorderCircle(redPaperVo.avatar, Color.parseColor("#FFF9AD55"), 1);
        fromInfo.set(String.format(Locale.getDefault(), getContext().getString(R.string.dehongbao), redPaperVo.name));
        gift.set(redPaperVo.gift);
        showDetail.set(redPaperVo.isSendMsg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.wallet_redpaper_dialog;
    }

    public void clickOpen(View v) {
        if (ClickUtils.isViewSingleClick(v)) {
            if (onRedPaperListener != null) {
                onRedPaperListener.onReceiveRedPaper();
            }
        }
    }

    public void clickCancel(View v) {
        dismiss();
    }

    public void clickShowDetail(View v) {
        if (ClickUtils.isViewSingleClick(v)) {
            if (redPaperVo != null) {
                PaperDetailActivity.start(v.getContext(), redPaperVo.serialNumber);
            }
            dismiss();
        }
    }

    public void setOnRedPaperListener(OnRedPaperListener l) {
        onRedPaperListener = l;
    }

    public interface OnRedPaperListener {
        void onReceiveRedPaper();
    }
}
