package com.watayouxiang.androidutils.recyclerview;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.watayouxiang.androidutils.R;

public final class MsgFetchLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.enhance_msg_fetch_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
