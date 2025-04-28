package com.tiocloud.chat.widget.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.SpanUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.databinding.TioHomeTitleBarBinding;
import com.tiocloud.chat.feature.search.curr.SearchActivity;
import com.tiocloud.chat.widget.popupwindow.HomePopupWindow;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class HomeTitleBar extends RelativeLayout implements View.OnClickListener {

    private final TioHomeTitleBarBinding binding;
    private String title;

    public HomeTitleBar(Context context) {
        this(context, null);
    }

    public HomeTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        if (TioConfig.OpenCloseConfig.hideRightTop()){
            binding.ivMore.setVisibility(GONE);
        }
    }

    public HomeTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tio_home_title_bar, this, true);
        binding.ivSearch.setOnClickListener(this);
        binding.ivMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivMore) {
            showHomePopupWindow(v);
        } else if (v == binding.ivSearch) {
            SearchActivity.start(v.getContext());
        }
    }

    // ====================================================================================
    // window
    // ====================================================================================

    private HomePopupWindow homePopupWindow;

    private void showHomePopupWindow(View v) {
        if (homePopupWindow == null) {
            homePopupWindow = new HomePopupWindow(v);
        }
        homePopupWindow.show();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(@NonNull String title) {
        if (binding == null) return;

        this.title = title;
        SpanUtils.with(binding.tvTitle)
                .append(title).setFontSize((int) getResources().getDimension(R.dimen.sp_24))
                .create();
    }

    public void setTitle(int titleId) {
        this.setTitle(getResources().getString(titleId));
    }

    /**
     * 追加标题信息
     *
     * @param append 追加信息，为空则复原标题
     */
    public void setAppendTitle(@Nullable String append) {
        if (binding == null) return;
        if (append == null){
            binding.tvSubtitle.setVisibility(GONE);
        }else {
            binding.tvSubtitle.setVisibility(VISIBLE);
            binding.tvSubtitle.setText(String.format("(%s)", append));
        }
//        if (title == null) return;
//
//        if (append == null) {
//            SpanUtils.with(binding.tvTitle)
//                    .append(title).setFontSize((int) getResources().getDimension(R.dimen.sp_24))
//                    .create();
//        } else {
//            SpanUtils.with(binding.tvTitle)
//                    .append(title).setFontSize((int) getResources().getDimension(R.dimen.sp_24))
//                    .append("(").setFontSize((int) getResources().getDimension(R.dimen.sp_15)).setVerticalAlign(SpanUtils.ALIGN_BOTTOM)
//                    .append(append).setFontSize((int) getResources().getDimension(R.dimen.sp_17)).setVerticalAlign(SpanUtils.ALIGN_BOTTOM)
//                    .append(")").setFontSize((int) getResources().getDimension(R.dimen.sp_15)).setVerticalAlign(SpanUtils.ALIGN_CENTER)
//                    .create();
//        }
    }

    public void hideRight(){
        binding.ivMore.setVisibility(GONE);
    }
}
