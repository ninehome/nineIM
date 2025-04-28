package com.watayouxiang.wallet.feature.redpacket.feature.receive;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGrabRedPacketListReq;
import com.watayouxiang.httpclient.model.request.PayGrabRedPacketStatReq;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketListResp;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketStatResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter.PacketItem;
import com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter.ReceiveModel;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class ReceiveViewModel extends ViewModel {

    private int pageNumber;

    public void refreshData(ReceiveFragment fragment) {
        getGrabRedPacketList(fragment, 1);
        getGrabRedPacketStat(fragment);
    }

    private void getGrabRedPacketStat(ReceiveFragment fragment) {
        RedPacketActivity activity = fragment.getRedPacketActivity();
        String period = activity.getPeriod();

        PayGrabRedPacketStatReq grabRedPacketStatReq = new PayGrabRedPacketStatReq(period);
        grabRedPacketStatReq.setCancelTag(this);
        grabRedPacketStatReq.get(new TioCallback<PayGrabRedPacketStatResp>() {
            @Override
            public void onTioSuccess(PayGrabRedPacketStatResp resp) {
                fragment.onHeaderInfoResp(resp);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    public void loadMoreData(ReceiveFragment fragment) {
        getGrabRedPacketList(fragment, ++pageNumber);
    }

    private void getGrabRedPacketList(ReceiveFragment fragment, int pageNumber) {
        this.pageNumber = pageNumber;
        RedPacketActivity activity = fragment.getRedPacketActivity();
        String period = activity.getPeriod();

        PayGrabRedPacketListReq grabRedPacketListReq = new PayGrabRedPacketListReq(String.valueOf(pageNumber), period);
        grabRedPacketListReq.setCancelTag(activity);
        grabRedPacketListReq.get(new TioCallback<PayGrabRedPacketListResp>() {
            @Override
            public void onTioSuccess(PayGrabRedPacketListResp resp) {
                List<PayGrabRedPacketListResp.ListBean> list = resp.getList();
                int size = list.size();
                boolean firstPage = resp.isFirstPage();
                boolean lastPage = resp.isLastPage();

                List<PacketItem> items = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    PayGrabRedPacketListResp.ListBean bean = list.get(i);
                    PacketItem item = new PacketItem();
                    item.setTitleTxt(StringUtils.null2Length0(bean.getNick()));
                    item.setSubtitleTxt(StringUtils.null2Length0(bean.getCreatetime()));
                    item.setMoneyInfoTxt(MoneyUtils.fen2yuan(bean.getMoney() + "") + "元");
                    item.setPin(bean.getMode() == 22);
                    item.setAvatarUrl(HttpCache.getResUrl(bean.getAvatar()));
                    items.add(item);
                }
                List<ReceiveModel> models = items2Models(items);

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

    private List<ReceiveModel> items2Models(List<PacketItem> packetItems) {
        int size = packetItems.size();
        List<ReceiveModel> models = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            PacketItem packetItem = packetItems.get(i);
            ReceiveModel receiveModel = new ReceiveModel(packetItem);
            models.add(receiveModel);
        }
        return models;
    }
}
