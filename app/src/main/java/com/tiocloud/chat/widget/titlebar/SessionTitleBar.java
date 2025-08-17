package com.tiocloud.chat.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TitlebarSessionBinding;
import com.tiocloud.chat.util.StringUtil;

/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 标题栏
 */
public class SessionTitleBar extends RelativeLayout {

    private final TitlebarSessionBinding binding;
    private final String title;
    private final int color;

    public SessionTitleBar(Context context) {
        this(context, null);
    }

    public SessionTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SessionTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SessionTitleBar);
        title = a.getString(R.styleable.SessionTitleBar_stb_title);
        color = a.getColor(R.styleable.SessionTitleBar_stb_background, Color.WHITE);
        a.recycle();

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.titlebar_session, this, true);
        resetUI();
        initUI();
    }

    private void initUI() {
        setBackgroundColor(color);
        if (title != null) {
            setTitle(title);
        }
        binding.ivBack.setOnClickListener(view -> {
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });
    }

    private void resetUI() {
        binding.ivBack.setVisibility(VISIBLE);
        binding.ivMore.setVisibility(VISIBLE);
        binding.tvTitle.setText("");
        binding.tvSubtitle.setText("");
    }

    public void showInputStatus(){
        binding.rel2.setVisibility(VISIBLE);
        setOnlineStatus(false, false, "");
        binding.tvInputStatus.setVisibility(VISIBLE);
    }

    public void hideInputStatus(){
        binding.tvInputStatus.setVisibility(GONE);
        binding.tvOutlineStatus.setVisibility(VISIBLE);
    }

    public void setOnlineStatus(boolean visbile, boolean isOnline, String offlineTime){
        if (visbile){
            binding.rel2.setVisibility(VISIBLE);
            hideInputStatus();
            binding.tvOutlineStatus.setVisibility(VISIBLE);
            if (isOnline){
//                binding.tvOutlineStatus.setBackgroundResource(R.drawable.dot_online);
                binding.tvOutlineStatus.setText(getContext().getString(R.string.zaixian));
                binding.tvOutlineStatus.setTextColor(getResources().getColor(R.color.theme_color));
            }else {
//                binding.tvOutlineStatus.setBackgroundResource(R.drawable.dot_offline_bg);
                binding.tvOutlineStatus.setText(offlineTime);
                binding.tvOutlineStatus.setTextColor(getResources().getColor(R.color.gray_999999));
            }
        }else {
            binding.tvOutlineStatus.setVisibility(GONE);
        }
    }


    // ====================================================================================
    // title
    // ====================================================================================

    public void setTitle(@Nullable CharSequence title) {
        if (binding == null) return;
        binding.tvTitle.setText(StringUtil.nonNull(title));
    }

    public void setTitle(@StringRes int titleId) {
        setTitle(getResources().getString(titleId));
    }

    // ====================================================================================
    // subtitle
    // ====================================================================================

    public void setSubtitle(@Nullable CharSequence subtitle) {
        if (binding == null) return;
        binding.tvSubtitle.setText(StringUtil.nonNull(subtitle));
    }

    public void setSubtitle(@StringRes int titleId) {
        setSubtitle(getResources().getString(titleId));
    }

    // ====================================================================================
    // moreBtn
    // ====================================================================================

    public ImageView getMoreBtn() {
        if (binding != null) {
            return binding.ivMore;
        }
        return null;
    }

}
