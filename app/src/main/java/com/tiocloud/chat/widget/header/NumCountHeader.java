package com.tiocloud.chat.widget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.HeaderNumCountBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/10
 *     desc   : 数量统计的 Header
 * </pre>
 */
public class NumCountHeader extends RelativeLayout {

    private HeaderNumCountBinding binding;

    public NumCountHeader(Context context) {
        this(context, null);
    }

    public NumCountHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumCountHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.header_num_count, this, true);
        resetViews();
    }

    private void resetViews() {
        binding.tvCenterTxt.setText("");
    }

    public void setCenterText(CharSequence text) {
        binding.tvCenterTxt.setText(text);
    }
}
