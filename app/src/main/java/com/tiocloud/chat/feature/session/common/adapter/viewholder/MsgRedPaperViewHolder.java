package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.viewmodel.RedPaperViewModel;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.TioDBHelper;
import com.watayouxiang.db.dao.RedPacketCrud;
import com.watayouxiang.db.table.RedPacketStatusTable;
import com.watayouxiang.httpclient.model.response.PayGrabRedPacketResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.wallet.feature.paperdetail.PaperDetailActivity;
import com.watayouxiang.wallet.widget.OpenRedPaperDialog;
import com.watayouxiang.wallet.widget.RedPaperDialog;
import com.watayouxiang.wallet.widget.RedPaperVo;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/10
 *     desc   :
 * </pre>
 */
public class MsgRedPaperViewHolder extends MsgBaseViewHolder {

    private ConstraintLayout cl_container;
    private TextView tv_title;
    private TextView tv_subtitle;

    private TextView tvRemark;

    @Nullable
    private InnerMsgRed msgRed;
    private final RedPaperViewModel viewModel = new RedPaperViewModel();
    private RedPaperDialog redPaperDialog;

    public MsgRedPaperViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.wallet_redpaper_msg;
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
        msgRed = (InnerMsgRed) getMessage().getContentObj();
        if (msgRed == null) return;

        initUI();
    }

    private void initUI() {
        tvRemark.setText(getContext().getString(R.string.app_name)+getContext().getString(R.string.red_package));
        RedPacketStatusTable redPacketStatusTable = RedPacketCrud.queryByRedId(Long.parseLong(msgRed.serialnumber));

        tv_title.setText(StringUtils.null2Length0(msgRed.text));
//        String status = msgRed.status;
        String status = null;
        if (redPacketStatusTable == null){
            status = msgRed.status;
        }else {
            status = redPacketStatusTable.getRedStatus();
        }

        if ("SUCCESS".equals(status)) {
            // 已抢完
            setRedStatus(2);
        } else if ("TIMEOUT".equals(status)) {
            // 24小时超时
            setRedStatus(3);
        } else if ("SEND".equals(status)) {
            // 抢红包中
            setRedStatus(4);
        } else {
            // 其他状态则认为是 "可抢红包"
            setRedStatus(4);
        }
    }

    private void setRedStatus(int status) {
        if (status == 1) {
            // 已领取
            cl_container.setSelected(true);
            tv_subtitle.setVisibility(View.VISIBLE);
            tv_subtitle.setText(getContext().getString(R.string.geted));
        } else if (status == 2) {
            // 已抢完
            cl_container.setSelected(true);
            tv_subtitle.setVisibility(View.VISIBLE);
            tv_subtitle.setText(getContext().getString(R.string.geted_finnish));
        } else if (status == 3) {
            // 24小时超时
            cl_container.setSelected(true);
            tv_subtitle.setVisibility(View.VISIBLE);
            tv_subtitle.setText(getContext().getString(R.string.overdated));
        } else if (status == 4) {
            // 抢红包中
            cl_container.setSelected(false);
            tv_subtitle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onContentClick(View view) {
        if (msgRed == null) return;
        if (!ClickUtils.isViewSingleClick(view)) return;
        TioMsg message = getMessage();
        boolean groupMsg = message.isGroupMsg();
        boolean p2PMsg = message.isP2PMsg();
        boolean sendMsg = message.isSendMsg();

        if (p2PMsg) {
            // 私聊
            if (sendMsg) {
                // 自己发的红包
                // 跳转红包详情页
                PaperDetailActivity.start(getContext(), msgRed.serialnumber);
            } else {
                // 别人发的红包
                // 查询红包状
                viewModel.getRedStatus(msgRed.serialnumber, this, true);
            }
        } else if (groupMsg) {
            // 群聊
            // 查询红包状
            viewModel.getRedStatus(msgRed.serialnumber, this, true);
        } else {
            TioToast.showShort(getContext().getString(R.string.unkown_session_type));
        }
    }

    /**
     * 获取红包状态响应
     *
     * @param status     1 已领取，2 已抢完，3 24小时超时，4 抢红包中
     * @param showDialog 是否显示弹窗
     */
    public void onRedStatusResp(int status, boolean showDialog) {
        if (status == 1) {
            // 已领取
            setRedStatus(1);
        } else if (status == 2) {
            // 已抢完
            setRedStatus(2);
        } else if (status == 3) {
            // 24小时超时
            setRedStatus(3);
        } else if (status == 4) {
            // 抢红包中
            setRedStatus(4);
        }

        if (showDialog) {
            if (msgRed == null) return;
            
            String avatar = getMessage().getAvatar();
            avatar = HttpCache.getResUrl(avatar);
            String name = getMessage().getName();
            String gift = msgRed.text;
            boolean isSendMsg = getMessage().isSendMsg();
            RedPaperVo redPaperVo = new RedPaperVo(avatar, name, gift, isSendMsg, msgRed.serialnumber);

            if (status == 1) {
                // 已领取
                // 查看红包详情
                PaperDetailActivity.start(getContext(), msgRed.serialnumber);
            } else if (status == 2) {
                // 已抢完
                // 显示已经抢完的弹窗
                new OpenRedPaperDialog(getContext(), OpenRedPaperDialog.NONE, redPaperVo).show();
            } else if (status == 3) {
                // 24小时超时
                // 显示超时的弹窗
                new OpenRedPaperDialog(getContext(), OpenRedPaperDialog.OVERDUE, redPaperVo).show();
            } else if (status == 4) {
                // 抢红包中
                // 显示待抢弹窗
                redPaperDialog = new RedPaperDialog(getContext(), redPaperVo);
                redPaperDialog.setOnRedPaperListener(() -> viewModel.getGrabRedPacket(MsgRedPaperViewHolder.this, msgRed.serialnumber));
                redPaperDialog.show();
            }
        }
    }

    /**
     * 抢红包响应
     */
    public void onGrabRedPacketResp(PayGrabRedPacketResp resp) {
        // 关闭弹窗
        if (redPaperDialog != null) {
            redPaperDialog.dismiss();
        }
        if (msgRed == null) return;
        // 跳转红包详情页
        PaperDetailActivity.start(getContext(), msgRed.serialnumber);
        // 查询红包状态
        viewModel.getRedStatus(msgRed.serialnumber, this, false);
    }

    public void dismiss(){
        if (redPaperDialog != null) {
            redPaperDialog.dismiss();
        }
    }

}
