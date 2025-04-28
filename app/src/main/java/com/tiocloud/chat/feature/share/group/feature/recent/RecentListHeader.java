package com.tiocloud.chat.feature.share.group.feature.recent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.HeaderShareGroupRecentBinding;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/10
 *     desc   : 数量统计的 Header
 * </pre>
 */
public class RecentListHeader extends RelativeLayout {

    private HeaderShareGroupRecentBinding binding;

    public RecentListHeader(Context context) {
        this(context, null);
    }

    public RecentListHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentListHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.header_share_group_recent, this, true);
    }
}
