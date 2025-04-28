package com.watayouxiang.androidutils.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.databinding.TitlebarMainBinding;


/**
 * author : TaoWang
 * date : 2020/2/27
 * desc : 标题栏
 */
public class WtTitleBar extends RelativeLayout {

    private final TitlebarMainBinding binding;
    // 标题
    private final String tvTitle_text;
    private final int tvTitle_textColor;
    // 返回按钮
    private int ivBack_src = 0;
    private Drawable ivBack_drawable = null;
    private boolean ivBack_hide;
    // 背景色
    private final int backgroundColor;
    // 右侧图片
    private final Drawable ivRight_src;
    // 右侧文字
    private final String tvRight_text;
    private final int tvRight_textColor;
    private final Drawable tvRight_background;
    private final boolean tvRight_enable;

    private final boolean spinnerRight_show;

    public WtTitleBar(Context context) {
        this(context, null);
    }

    public WtTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WtTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // TypedArray
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WtTitleBar);

        // 是否为浅色模式（默认浅色模式）
        boolean isLightMode = a.getBoolean(R.styleable.WtTitleBar_tb_isLightMode, true);
        // 背景色
        int defaultBackgroundColor = isLightMode ? Color.WHITE : getResources().getColor(R.color.blue_4c94ff);
        backgroundColor = a.getColor(R.styleable.WtTitleBar_tb_background, defaultBackgroundColor);
        // 左侧返回图片
        ivBack_drawable = a.getDrawable(R.styleable.WtTitleBar_tb_ivBack_src);
        if (ivBack_drawable == null) {
            ivBack_src = isLightMode ? R.drawable.btn_return_black : R.drawable.btn_return_white;
        }
        ivBack_hide = a.getBoolean(R.styleable.WtTitleBar_tb_ivBack_hide, false);
        // 标题
        tvTitle_text = a.getString(R.styleable.WtTitleBar_tb_tvTitle_text);
        tvTitle_textColor = isLightMode ? getResources().getColor(R.color.gray_333333) : getResources().getColor(R.color.white);
        // 右侧图片
        ivRight_src = a.getDrawable(R.styleable.WtTitleBar_tb_ivRight_src);
        // 右侧文字
        tvRight_text = a.getString(R.styleable.WtTitleBar_tb_tvRight_text);
        tvRight_textColor = a.getColor(R.styleable.WtTitleBar_tb_tvRight_textColor, getResources().getColor(R.color.black));
        tvRight_background = a.getDrawable(R.styleable.WtTitleBar_tb_tvRight_background);
        tvRight_enable = a.getBoolean(R.styleable.WtTitleBar_tb_tvRight_enable, true);
        // 右侧 spinner
        spinnerRight_show = a.getBoolean(R.styleable.WtTitleBar_tb_spinnerRight_show, false);

        a.recycle();

        // ui binding
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.titlebar_main, this, true);
        resetUI();
        initUI();
    }

    private void initUI() {
        // 背景色
        setBackgroundColor(backgroundColor);
        // 标题
        if (tvTitle_text != null) {
            binding.tvTitle.setText(tvTitle_text);
        }
        binding.tvTitle.setTextColor(tvTitle_textColor);
        // 左侧返回键
        if (ivBack_drawable != null) {
            binding.ivBack.setImageDrawable(ivBack_drawable);
        } else if (ivBack_src != 0) {
            binding.ivBack.setImageResource(ivBack_src);
        }
        binding.ivBack.setOnClickListener(view -> {
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) context).onBackPressed();
            }
        });
        if (ivBack_hide) {
            binding.ivBack.setVisibility(INVISIBLE);
        } else {
            binding.ivBack.setVisibility(VISIBLE);
        }
        // 右侧文字
        if (tvRight_text != null) {
            binding.tvRight.setVisibility(VISIBLE);
            binding.tvRight.setText(tvRight_text);
            binding.tvRight.setTextColor(tvRight_textColor);
            binding.tvRight.setBackground(tvRight_background);
            binding.tvRight.setEnabled(tvRight_enable);
        } else {
            binding.tvRight.setVisibility(GONE);
        }
        // 右侧图片
        if (ivRight_src != null) {
            binding.ivRight.setVisibility(VISIBLE);
            binding.ivRight.setImageDrawable(ivRight_src);
        } else {
            binding.ivRight.setVisibility(GONE);
        }
        // 右侧 spinner
        if (spinnerRight_show) {
            binding.spinnerRight.setVisibility(VISIBLE);
        } else {
            binding.spinnerRight.setVisibility(GONE);
        }
    }

    private void resetUI() {
        // 返回键
        binding.ivBack.setVisibility(VISIBLE);
        // 标题
        binding.tvTitle.setVisibility(VISIBLE);
        binding.tvTitle.setText("");
        // 右侧容器
        binding.flRight.setVisibility(VISIBLE);
        // 右侧文字
        binding.tvRight.setVisibility(GONE);
        // 右侧图片
        binding.ivRight.setVisibility(GONE);
        // 右侧 spinner
        binding.spinnerRight.setVisibility(GONE);
    }

    public ImageView getIvRight2(){
        return binding.ivRight2;
    }

    // ====================================================================================
    // 标题
    // ====================================================================================

    public void setTitle(@NonNull CharSequence title) {
        if (binding == null) return;
        binding.tvTitle.setText(title);
    }

    public void setTitle(@NonNull String title) {
        if (binding == null) return;
        binding.tvTitle.setText(title);
    }

    public void setTitle(@StringRes int titleId) {
        setTitle(getResources().getString(titleId));
    }

    // ====================================================================================
    // 右侧图片
    // ====================================================================================

    public ImageView getIvRight() {
        return binding.ivRight;
    }

    // ====================================================================================
    // 右侧文字
    // ====================================================================================

    public TextView getTvRight() {
        return binding.tvRight;
    }

    // ====================================================================================
    // 右侧 spinner
    // ====================================================================================

    // Spinner控件详解:
    // https://blog.csdn.net/q4878802/article/details/50775002
    // 修改Android中Spinner的显示及下拉样式的四种方法:
    // https://blog.csdn.net/badboy007/article/details/19034609?locationNum=7
    public Spinner getSpinnerRight() {
        return binding.spinnerRight;
    }

    public void setSpinnerRight_data(String[] items) {
        Context context = binding.spinnerRight.getContext();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, R.layout.tio_simple_spinner_item_white, items);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRight.setAdapter(spinnerAdapter);
    }

    public void setSpinnerRight_onItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener listener) {
        binding.spinnerRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener != null) {
                    listener.onItemSelected(adapterView, view, i, l);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (listener != null) {
                    listener.onNothingSelected(adapterView);
                }
            }
        });
    }

    // ====================================================================================
    // 返回按钮
    // ====================================================================================

    public ImageView getIvBack() {
        return binding.ivBack;
    }

}
