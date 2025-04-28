package com.tiocloud.chat.feature.session.common.adapter.viewholder.viewmodel;

import android.app.Activity;

import com.tiocloud.chat.feature.session.common.adapter.viewholder.MsgRedPaperViewHolder;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.RedPacketCrud;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayGrabRedPacketReq;
import com.watayouxiang.httpclient.model.request.PayRedStatusReq;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketResp;
import com.watayouxiang.httpclient.model.response.PayRedStatusResp;
import com.watayouxiang.wallet.feature.open.OpenWalletActivity;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class RedPaperViewModel {

    public void getRedStatus(String serialNumber, MsgRedPaperViewHolder holder, boolean showDialog) {
        PayRedStatusReq payRedStatusReq = new PayRedStatusReq(serialNumber);
        Activity activity = holder.getActivity();
        if (activity == null) {
            TioToast.showShort("获取不到 activity");
            return;
        }
        payRedStatusReq.setCancelTag(activity);
        payRedStatusReq.get(new TioCallback<PayRedStatusResp>() {
            @Override
            public void onTioSuccess(PayRedStatusResp payRedStatusResp) {
                int openflag = payRedStatusResp.getOpenflag();
                String grabstatus = payRedStatusResp.getGrabstatus();
                String redstatus = payRedStatusResp.getRedstatus();

                RedPacketStatusTable redPacketStatusTable = RedPacketCrud.queryByRedId(Long.parseLong(serialNumber));
                if (redPacketStatusTable == null){
                    redPacketStatusTable = new RedPacketStatusTable();
                    redPacketStatusTable.setRedId(Long.parseLong(serialNumber));
                    redPacketStatusTable.setRedStatus(grabstatus);
                    RedPacketCrud.insert(redPacketStatusTable);
                }else {
                    redPacketStatusTable.setRedStatus(grabstatus);
                    RedPacketCrud.update(redPacketStatusTable);
                }

                if (openflag == 2) {
                    // 未开户，跳转开户页
                    OpenWalletActivity.start(activity);
                } else if (openflag == 1) {
                    // 开户
                    if ("SUCCESS".equals(grabstatus)) {
                        // 自己已抢
                        holder.onRedStatusResp(1, showDialog);
                    } else if ("INIT".equals(grabstatus)) {
                        // 自己未抢
                        if ("SUCCESS".equals(redstatus)) {
                            // 红包已抢完
                            holder.onRedStatusResp(2, showDialog);
                        } else if ("TIMEOUT".equals(redstatus)) {
                            // 24小时超时
                            holder.onRedStatusResp(3, showDialog);
                        } else if ("SEND".equals(redstatus)) {
                            // 抢红包中
                            holder.onRedStatusResp(4, showDialog);
                        } else {
                            TioToast.showShort("redstatus = " + redstatus);
                        }
                    } else {
                        TioToast.showShort("grabstatus = " + grabstatus);
                    }
                } else {
                    TioToast.showShort("openflag = " + openflag);
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    public void getGrabRedPacket(MsgRedPaperViewHolder holder, String serialNumber) {
        String chatLinkId = holder.getAdapter().getChatLinkId();
        if (chatLinkId == null) {
            TioToast.showShort("获取不到 chatLinkId");
            return;
        }
        Activity activity = holder.getActivity();
        if (activity == null) {
            TioToast.showShort("获取不到 activity");
            return;
        }
        PayGrabRedPacketReq payGrabRedPacketReq = new PayGrabRedPacketReq(serialNumber, chatLinkId);
        payGrabRedPacketReq.setCancelTag(activity);
        payGrabRedPacketReq.get(new TioCallback<PayGrabRedPacketResp>() {
            @Override
            public void onTioSuccess(PayGrabRedPacketResp payGrabRedPacketResp) {
                holder.onGrabRedPacketResp(payGrabRedPacketResp);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
                holder.dismiss();
            }
        });
    }
}
