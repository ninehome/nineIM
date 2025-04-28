package com.watayouxiang.wallet.feature.redpacket.feature.send;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PaySendRedPacketListReq;
import com.watayouxiang.httpclient.model.request.PaySendRedPacketStatReq;
import com.watayouxiang.httpclient.model.response.PaySendRedPacketListResp;
import com.watayouxiang.httpclient.model.response.PaySendRedPacketStatResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.redpacket.feature.send.adapter.PacketItem;
import com.watayouxiang.wallet.feature.redpacket.feature.send.adapter.SendModel;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class SendViewModel extends ViewModel {
    private int pageNumber;

    public void refreshData(SendFragment fragment) {
        getSendRedPacketList(fragment, 1);
        getSendRedPacketStat(fragment);
    }

    public void loadMoreData(SendFragment fragment) {
        getSendRedPacketList(fragment, ++pageNumber);
    }

    private void getSendRedPacketStat(SendFragment fragment) {
        RedPacketActivity activity = fragment.getRedPacketActivity();
        String period = activity.getPeriod();

        PaySendRedPacketStatReq sendRedPacketStatReq = new PaySendRedPacketStatReq(period);
        sendRedPacketStatReq.setCancelTag(this);
        sendRedPacketStatReq.get(new TioCallback<PaySendRedPacketStatResp>() {
            @Override
            public void onTioSuccess(PaySendRedPacketStatResp resp) {
                fragment.onHeaderInfoResp(resp);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private void getSendRedPacketList(SendFragment fragment, int pageNumber) {
        this.pageNumber = pageNumber;
        RedPacketActivity activity = fragment.getRedPacketActivity();
        String period = activity.getPeriod();

        PaySendRedPacketListReq sendRedPacketListReq = new PaySendRedPacketListReq(pageNumber + "", period);
        sendRedPacketListReq.setCancelTag(fragment.getRedPacketActivity());
        sendRedPacketListReq.get(new TioCallback<PaySendRedPacketListResp>() {
            @Override
            public void onTioSuccess(PaySendRedPacketListResp resp) {
                List<PaySendRedPacketListResp.ListBean> list = resp.getList();
                int size = list.size();
                boolean firstPage = resp.isFirstPage();
                boolean lastPage = resp.isLastPage();

                ArrayList<PacketItem> items = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    PaySendRedPacketListResp.ListBean bean = list.get(i);

                    PacketItem item = new PacketItem();
                    item.setTitleTxt(bean.getMode() == 22 ? activity.getString(R.string.pingrenpin) : activity.getString(R.string.putonghongbao));
                    item.setSubtitleTxt(StringUtils.null2Length0(bean.getCreatetime()));
                    item.setAmountInfoTxt(String.format(Locale.getDefault(), activity.getString(R.string.ge2), bean.getNum()-bean.getRemainnum(), bean.getNum()));
                    item.setMoneyInfoTxt(String.format(Locale.getDefault(), activity.getString(R.string.yuan2), MoneyUtils.fen2yuan(bean.getMoney() + "")));
                    item.setTimeoutRed(bean.getStatus().intValue() == 4);
                    items.add(item);
                }
                List<SendModel> models = items2Models(items);

                if (firstPage) {
                    fragment.setRefreshData(models);
                    fragment.binding.swipeRefreshLayout.setRefreshing(false);
                } else {
                    fragment.listAdapter.addData(models);
                }
                if (lastPage) {
                    fragment.listAdapter.loadMoreEnd();
                } else {
                    fragment.listAdapter.loadMoreComplete();
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
                if (pageNumber <= 1) {
                    fragment.binding.swipeRefreshLayout.setRefreshing(false);
                } else {
                    fragment.listAdapter.loadMoreFail();
                }
            }
        });
    }

    private List<SendModel> items2Models(List<PacketItem> packetItems) {
        List<SendModel> models = new ArrayList<>();
        for (int i = 0; i < packetItems.size(); i++) {
            PacketItem packetItem = packetItems.get(i);
            SendModel sendModel = new SendModel(packetItem);
            models.add(sendModel);
        }
        return models;
    }
}
