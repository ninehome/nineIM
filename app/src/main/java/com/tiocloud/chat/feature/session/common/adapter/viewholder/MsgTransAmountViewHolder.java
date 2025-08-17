package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.viewmodel.RedPaperViewModel;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.RedPacketCrud;
import com.watayouxiang.db.dao.TransAmountCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.db.table.TransAmountStatusTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.TransAmountDetailReq;
import com.watayouxiang.httpclient.model.request.TransAmountPayReq;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketResp;
import com.watayouxiang.httpclient.model.response.TransDetailResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgTransAmount;
import com.watayouxiang.wallet.feature.paperdetail.PaperDetailActivity;
import com.watayouxiang.wallet.feature.trans_amount.TransAmountActivity;
import com.watayouxiang.wallet.feature.trans_amount.TransHasRecieverActivity;
import com.watayouxiang.wallet.feature.trans_amount.TransRecieverActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;
import com.watayouxiang.wallet.widget.OpenRedPaperDialog;
import com.watayouxiang.wallet.widget.RedPaperDialog;
import com.watayouxiang.wallet.widget.RedPaperVo;

import java.util.Map;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/10
 *     desc   :
 * </pre>
 */
public class MsgTransAmountViewHolder extends MsgBaseViewHolder {

    private ConstraintLayout cl_container;
    private TextView tv_title;
    private TextView tv_subtitle;

    private TextView tvRemark;

    private Long transId;

    private int transType;

    @Nullable
    private InnerMsgTransAmount transAmount;
    private final RedPaperViewModel viewModel = new RedPaperViewModel();
    private RedPaperDialog redPaperDialog;

    public MsgTransAmountViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.trans_amount_msg;
    }

    @Override
    protected void inflateContent() {
        cl_container = findViewById(R.id.cl_container);
        tv_title = findViewById(R.id.tv_title);
        tv_subtitle = findViewById(R.id.tv_subtitle);
        tvRemark = findViewById(R.id.tv_remark);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        transAmount = (InnerMsgTransAmount) getMessage().getContentObj();
        if (transAmount == null) return;
        transId = transAmount.id;
        if (transAmount.type != null){
            transType = transAmount.type;
        }
        checkBox.setVisibility(View.GONE);
        initUI();
    }

    private void initUI() {
        tvRemark.setText(getContext().getString(R.string.app_name)+getContext().getString(R.string.zhuanzhnag));
        TransAmountStatusTable transAmountStatusTable = TransAmountCrud.queryByRedId(transAmount.id);

        tv_title.setText("￥"+MoneyUtils.fen2yuan(String.valueOf(transAmount.amount)));
//        tv_subtitle.setText(transAmount.remark);
        Integer status = null;
        if (transAmountStatusTable == null){
            status = transAmount.status;
        }else {
            status = transAmountStatusTable.getTransStatus();
        }
        if (getMessage().isSendMsg()){
            if (transType == 1){
                if (status == 1){
                    tv_subtitle.setText(getContext().getString(R.string.you_send_a_zhuanzhang));
                }else if (status == 2){
                    tv_subtitle.setText(getContext().getString(R.string.accepted));
                }else if (status == 4 || status == 5){
                    tv_subtitle.setText(getContext().getString(R.string.backed));
                }
            }else if (transType == 2){
                if (status == 2){
                    tv_subtitle.setText(getContext().getString(R.string.yishoukuan));
                }else if (status == 4 || status == 5){
                    tv_subtitle.setText(getContext().getString(R.string.yituihuan));
                }
            }
        }else {
            if (transType == 1){
                if (status == 1){
                    tv_subtitle.setText(getContext().getString(R.string.qingshoukuan));
                }else if (status == 2){
                    tv_subtitle.setText(getContext().getString(R.string.accepted));
                }else if (status == 4 || status == 5){
                    tv_subtitle.setText(getContext().getString(R.string.backed));
                }
            }else if (transType == 2){
                if (status == 2){
                    tv_subtitle.setText(getContext().getString(R.string.yishoukuan));
                }else if (status == 4 || status == 5){
                    tv_subtitle.setText(getContext().getString(R.string.yibeituihuan));
                }
            }
        }
//        if (getMessage().isSendMsg()){
//            if (status == 1){
//                tv_subtitle.setText("你发起了一笔转账");
//            }else if (status == 2){
//                tv_subtitle.setText("已被接收");
//            }else if (status == 4 || status == 5){
//                tv_subtitle.setText("已退还");
//            }else {
//                tv_subtitle.setText("无效状态");
//            }
//        }
//        if (status == 1){
//            tv_subtitle.setText("待收款");
//        }else if (status == 2){
//            tv_subtitle.setText("已收款");
//        }else if (status == 4 || status == 5){
//            tv_subtitle.setText("已退还");
//        }else {
//            tv_subtitle.setText("无效状态");
//        }
        if (status == 1){
            cl_container.setSelected(false);
        }else {
            cl_container.setSelected(true);
        }
//        RedPacketStatusTable redPacketStatusTable = RedPacketCrud.queryByRedId(Long.parseLong(msgRed.serialnumber));
//
//        tv_title.setText(StringUtils.null2Length0(msgRed.text));
////        String status = msgRed.status;
//        String status = null;
//        if (redPacketStatusTable == null){
//            status = msgRed.status;
//        }else {
//            status = redPacketStatusTable.getRedStatus();
//        }
    }

    @Override
    protected void onContentClick(View view) {

        if (transId == null){
            return;
        }
//                if (getMessage().isSendMsg()){
//                    return;
//                }
//                if (transType != 1){
//                    return;
//                }
        TransAmountDetailReq transAmountDetailReq = new TransAmountDetailReq(transId);
        transAmountDetailReq.setCancelTag(this);
        transAmountDetailReq.post(new TioCallback<TransDetailResp>() {
            @Override
            public void onTioSuccess(TransDetailResp transDetailResp) {
                int status = transDetailResp.status;
                TransAmountStatusTable transAmountStatusTable = TransAmountCrud.queryByRedId(transAmount.id);
                if (transAmountStatusTable == null){
                    transAmountStatusTable = new TransAmountStatusTable();
                    transAmountStatusTable.setTransId(transAmount.id);
                    transAmountStatusTable.setTransStatus(status);
                    TransAmountCrud.insert(transAmountStatusTable);
                }else {
                    transAmountStatusTable.setTransStatus(status);
                    TransAmountCrud.update(transAmountStatusTable);
                }
                if (status == 1){
                    //未收款，去收款界面
                    if (transType == 1 && !getMessage().isSendMsg()){
                        TransRecieverActivity.setTransAmountRecieverListener(new TransRecieverActivity.TransAmountRecieverListener() {
                            @Override
                            public void back(int flag) {
                                initUI();
                            }
                        });
                        TransRecieverActivity.start(getActivity(), transDetailResp, getMessage().getChatLinkId());
                    }

                }else if (status == 2){
                    //已收款，去已收款界面
                    if (transType == 1 && !getMessage().isSendMsg()){
                        TransHasRecieverActivity.start(getActivity(), transDetailResp, getMessage().getChatLinkId());
                    }
                }else if (status == 4 || status == 5){
                    //已退还
//                            ToastUtils.showShort("该转账已退还");
                }else {
//                            ToastUtils.showShort("无效状态");
                }
                initUI();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }
}
