package com.tiocloud.chat.feature.session.common.panel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.proxy.MsgListProxy;
import com.watayouxiang.androidutils.util.TioLogger;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/04
 *     desc   :
 * </pre>
 */
class NewMsgTipHelper {
    private final RecyclerView recyclerView;
    private final MsgListProxy msgListProxy;
    private final TextView tvNewMsgTip;
    private boolean autoScroll2Bottom = false;

    public NewMsgTipHelper(RecyclerView recyclerView, MsgListProxy msgListProxy, TextView tvNewMsgTip) {
        this.recyclerView = recyclerView;
        this.msgListProxy = msgListProxy;
        this.tvNewMsgTip = tvNewMsgTip;

        initUI();
    }

    private void initUI() {
        tvNewMsgTip.setOnClickListener(v -> {
            tvNewMsgTip.setVisibility(View.GONE);
            msgListProxy.scrollToBottom();
        });
        tvNewMsgTip.setVisibility(View.GONE);

        // 监听列表滚动
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 已经在底部了，隐藏新消息提示
                    if (isScrollBottom(recyclerView)) {
                        tvNewMsgTip.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 判断 RecyclerView 是否滚动到底部
     */
    private boolean isScrollBottom(RecyclerView recyclerView) {
        // 垂直方向的判断
        // 判断是否滑动到底部: recyclerView.canScrollVertically(1); 返回false表示不能往上滑动，即代表到底部了；
        // 判断是否滑动到顶部: recyclerView.canScrollVertically(-1); 返回false表示不能往下滑动，即代表到顶部了；
        return !recyclerView.canScrollVertically(1);
    }

    /**
     * 数据变化前
     */
    public void onBeforeDataChanged() {
        TioLogger.d(String.format(Locale.getDefault(), "onBeforeDataChanged: autoScroll = %s", autoScroll2Bottom));
        // 如果"在最后一行"，则开启"自动滚动到底部"
        // 如果"不在最后一行"，则关闭"自动滚动到底部"
        autoScroll2Bottom = isScrollBottom(recyclerView);
    }

    /**
     * 数据变化后
     *
     * @param msg 消息
     */
    public void onAfterDataChanged(TioMsg msg) {
        TioLogger.d(String.format(Locale.getDefault(), "onAfterDataChanged: autoScroll = %s", autoScroll2Bottom));
        if (autoScroll2Bottom) {
            // 如果开启了 "自动滚动到底部"，则 "滚动到最后一行"
            msgListProxy.scrollToBottom();
        } else {
            // 来消息 && 没有"自动滚动到底部" && 不是自己发的消息
            // 那么说明是新消息，则显示"新消息提示"
            if (msg != null && !msg.isSendMsg()) {
                tvNewMsgTip.setVisibility(View.VISIBLE);
            }
        }
    }
}
