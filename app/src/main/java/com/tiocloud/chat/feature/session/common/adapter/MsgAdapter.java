package com.tiocloud.chat.feature.session.common.adapter;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.share.msg.ShareMsgActivity;
import com.tiocloud.chat.mvp.addfriend.AddFriendPresenter;
import com.tiocloud.chat.mvp.card.CardPresenter;
import com.tiocloud.chat.mvp.download.DownloadPresenter;
import com.watayouxiang.androidutils.recyclerview.BaseMultiItemFetchLoadAdapter;
import com.watayouxiang.audiorecord.TioAudioPlayer;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author : TaoWang
 * date : 2020-02-07
 * desc :
 */
public class MsgAdapter extends BaseMultiItemFetchLoadAdapter<TioMsg, BaseViewHolder> {

    @NonNull
    private final String chatLinkId;
    private  String chatMode;
    private  String msgId;


    public MsgAdapter(RecyclerView recyclerView,String chatMode, List<TioMsg> data, @NonNull String chatLinkId) {
        super(recyclerView, data);

        this.chatLinkId = chatLinkId;
        this.chatMode = chatMode;

        Set<Map.Entry<TioMsgType, Class<? extends MsgBaseViewHolder>>> entries = MsgViewHolderFactory.getViewHolders().entrySet();
        for (Map.Entry<TioMsgType, Class<? extends MsgBaseViewHolder>> entry : entries) {
            addItemType(entry.getKey().getValue(), MsgViewHolderFactory.getLayoutResId(), entry.getValue());
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    protected int getViewType(TioMsg message) {
        return MsgViewHolderFactory.getViewType(message);
    }

    @Override
    protected String getItemKey(TioMsg item) {
        return item.getId();
    }

    /**
     * 资源释放
     */
    public void release() {
        if (cardPresenter != null) {
            cardPresenter.detachView();
            cardPresenter = null;
        }
        if (addFriendPresenter != null) {
            addFriendPresenter.detachView();
            addFriendPresenter = null;
        }
        if (downloadPresenter != null) {
            downloadPresenter.detachView();
            downloadPresenter = null;
        }
        TioAudioPlayer.getInstance().release();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    /**
     * 根据 mid 删除消息
     *
     * @param mid 消息唯一id
     */
    public void deleteMsg(long mid) {
        // 获取 item 的位置
        int position = getMsgPosition(mid);
        // 存在则删除
        if (position != -1) {
            remove(position);
        }
    }

    //需要自己找位置插进去
    public void addOrderMsg(TioMsg insertTioMsg){
        int mid = Integer.parseInt(insertTioMsg.getId());
        int pos = -1;
        for (int i = 0; i < getData().size(); i++){
            TioMsg tioMsg = getData().get(i);
            int m = Integer.parseInt(tioMsg.getId());
            if (mid == i){
                return;
            }
            if (mid > m){
                continue;
            }
            if (mid < m){
                pos = i - 1;
                break;
            }
        }
        if (pos >= 0){
            add(pos, insertTioMsg);
        }
    }

    /**
     * 根据 mid 查找 消息位置
     *
     * @param mid 消息唯一id
     * @return 无则返回 -1
     */
    public int getMsgPosition(long mid) {
        int position = -1;
        List<TioMsg> data = getData();
        for (int i = 0, size = data.size(); i < size; i++) {
            String _mid = data.get(i).getId();
            if (_mid != null && _mid.equals(String.valueOf(mid))) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 已读 全部消息
     */
    public void readAllMsg() {
        List<TioMsg> data = getData();
        for (int i = 0, size = data.size(); i < size; i++) {
            data.get(i).setReadMsg(true);
        }
        notifyDataSetChanged();
    }

    /**
     * 长按头像
     */
    public boolean onAvatarLongClick(View v, TioMsg msg) {
        return false;
    }

    /**
     * 会话id
     */
    @NonNull
    public String getChatLinkId() {
        return chatLinkId;
    }

    // ====================================================================================
    // presenter
    // ====================================================================================

    private CardPresenter cardPresenter;
    private AddFriendPresenter addFriendPresenter;
    private DownloadPresenter downloadPresenter;

    @NonNull
    public CardPresenter getCardPresenter() {
        if (cardPresenter == null) {
            cardPresenter = new CardPresenter();
        }
        return cardPresenter;
    }

    @NonNull
    public AddFriendPresenter getAddFriendPresenter() {
        if (addFriendPresenter == null) {
            addFriendPresenter = new AddFriendPresenter();
        }
        return addFriendPresenter;
    }

    @NonNull
    public DownloadPresenter getDownloadPresenter() {
        if (downloadPresenter == null) {
            downloadPresenter = new DownloadPresenter();
        }
        return downloadPresenter;
    }

    public void showBotomDialog(){
        Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.tio_multi_choose_bottom_dialog, null);
        ImageView iv_transmit = contentView.findViewById(R.id.iv_transmit);
        iv_transmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                showDialog();
            }
        });
        ImageView iv_delete = contentView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<getData().size();i++){
                    if (selectedChecbox.get(i)){
                        deleteMsg(Long.parseLong(getData().get(i).getId()));
                    }
                }
            }
        });
        TextView tv_exit = contentView.findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                closeMultiChoose();
            }
        });
        bottomDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(mContext);
        layoutParams.height = ScreenUtil.dip2px(mContext,55);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialogAnimation);
        bottomDialog.getWindow().setDimAmount(0);
        bottomDialog.show();
    }

    private void showDialog(){
        Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.tio_transmit_dialog, null);
        TextView tv_single_transmit = contentView.findViewById(R.id.tv_single_transmit);
        TextView tv_combine_transmit = contentView.findViewById(R.id.tv_combine_transmit);
        TextView tv_close = contentView.findViewById(R.id.tv_close);

        tv_single_transmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
                bottomDialog.dismiss();
                closeMultiChoose();
            }
        });

        tv_combine_transmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
                bottomDialog.dismiss();
                closeMultiChoose();
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                closeMultiChoose();
            }
        });

        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(mContext);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialogAnimation);
        bottomDialog.getWindow().setDimAmount(0);
        bottomDialog.show();
    }

    private void closeMultiChoose(){
        selectedChecbox.clear();
        isMultiChoose = false;
        setMsgId("");
        notifyDataSetChanged();
    }

    private void showShare(){
        StringBuffer stringBuffer = new StringBuffer();
        List<TioMsg> tioMsgs = getData();
        for (int i=0;i<tioMsgs.size();i++){
            if(selectedChecbox.get(i)){
                stringBuffer.append(tioMsgs.get(i).getId());
                stringBuffer.append(",");
            }
        }
        String str = stringBuffer.toString();
        str = str.substring(0,stringBuffer.toString().length()-1);
        ShareMsgActivity.multiStart(mContext,getChatLinkId(),str,chatMode);
    }
}
