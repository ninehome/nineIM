package com.tiocloud.chat.feature.session.common.adapter.viewholder.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.model.TioMsgType;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.group.fragment.GroupSessionFragment;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.widget.IntercpetLayout;
import com.tiocloud.chat.widget.dialog.tio.SessionMsgDialog;
import com.watayouxiang.androidutils.recyclerview.RecyclerViewHolder;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UserInfoReq;
import com.watayouxiang.httpclient.model.response.UserInfoResp;

import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-30
 * desc : 基本Holder
 */
public abstract class MsgBaseViewHolder extends RecyclerViewHolder<MsgAdapter, BaseViewHolder, TioMsg> {

    private static final int leftContentBgId = R.drawable.shape_bubble_left;
    private static final int rightContentBgId = R.drawable.shape_bubble_right;

    private static final int leftContentBgIdAdmin = R.drawable.shape_bubble_left_admin;
    private static final int rightContentBgIdAdmin = R.drawable.shape_bubble_right_admin;

    private View view;
    private Context context;
    private TioMsg message;

    private FrameLayout contentContainer;
    private IntercpetLayout root_rl;
    private TioImageView avatarLeft;
    private TioImageView avatarRight;
    private TextView nickLeft;
    private TextView nickRight;
    private TextView timeView;
    private TextView tv_receipt_left;
    private TextView tv_receipt_right;

    private TextView ivLableLeft, ivLableRight;

    public CheckBox checkBox;

    boolean isOwner, isAdmin;

    public int position;

    @Nullable
    public Activity getActivity() {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public Context getContext() {
        return context;
    }
    public int getPisition() {
        return position;
    }

    public TioMsg getMessage() {
        return message;
    }

    // ==============================================================================
    // create
    // ==============================================================================

    public MsgBaseViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(BaseViewHolder holder, TioMsg data, int position, boolean isScrolling) {
        view = holder.getConvertView();
        context = holder.itemView.getContext();
        message = data;
        this.position = position;

        inflate();
        refresh(holder);
    }

    private void inflate() {
        // 内容
        contentContainer = findViewById(R.id.message_item_content);
        root_rl = findViewById(R.id.root_rl);
        // 头像
        avatarLeft = findViewById(R.id.message_item_portrait_left);
        avatarRight = findViewById(R.id.message_item_portrait_right);
        // 昵称
        nickLeft = findViewById(R.id.message_item_nickname_left);
        nickRight = findViewById(R.id.message_item_nickname_right);
        // 时间
        timeView = findViewById(R.id.message_item_time);
        // 消息接收状况
        tv_receipt_left = findViewById(R.id.tv_receipt_left);
        tv_receipt_right = findViewById(R.id.tv_receipt_right);

        ivLableLeft = findViewById(R.id.iv_lable_left);
        ivLableRight = findViewById(R.id.iv_lable_right);
        checkBox = findViewById(R.id.checkBox);

        // 避免多次填充布局
        int contentResId;
        if (contentContainer.getChildCount() == 0 && (contentResId = contentResId()) != 0) {
            View.inflate(view.getContext(), contentResId, contentContainer);
        }
        inflateContent();
//        if(getAdapter().isMultiChoose){
//            contentContainer.setIsIntercept(true);
//        }else {
//            contentContainer.setIsIntercept(false);
//        }

    }

    private void refresh(BaseViewHolder holder) {
        if (message.isGroupMsg()){
            isOwner = false;
            isAdmin = false;
            int groupRole = CacheTableCrud.getGroupRole(String.valueOf(-Integer.parseInt(message.getChatLinkId())), Long.parseLong(message.getUid()));
            if (groupRole == 1){
                isOwner = true;
            }else if (groupRole == 3){
                isAdmin = true;
            }

//            GroupSessionFragment fragmentByClass = (GroupSessionFragment) ((GroupSessionActivity) getActivity()).findFragmentByClass(GroupSessionFragment.class);
//            if (fragmentByClass != null){
//                List<Integer> managerIds = fragmentByClass.managerIds;
//                Integer ownId = fragmentByClass.owerId;
//                Integer myRole = fragmentByClass.myrole;
//                if (message.getUid().equals(String.valueOf(ownId))){
//                    isOwner = true;
//                }else if (isManager(managerIds, message.getUid())){
//                    isAdmin = true;
//                }else {
//                    isOwner = false;
//                    isAdmin = false;
//                }
//            }else {
//                isOwner = false;
//                isAdmin = false;
//            }
        }

        if(getAdapter().isMultiChoose){
            root_rl.setIsIntercept(true);
        }else {
            root_rl.setIsIntercept(false);
        }
        showCheckBox();
        setHeadImageView();
        setContent();
        setNickView();
        setTimeView();
        setDisplayReceipt();
        bindContent(holder);
        root_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().selectedChecbox.put(getMsgPosition(Long.parseLong(message.getId())),!checkBox.isChecked());
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    public void showCheckBox(){
        if(getAdapter().getMsgId() != null){
            if(getAdapter().getMsgId().equals(message.getId())){
                checkBox.setChecked(true);
            }
        }

        checkBox.setVisibility(getAdapter().isMultiChoose ? View.VISIBLE:View.GONE);
    }


    // ====================================================================================
    // init
    // ====================================================================================

    private void setDisplayReceipt() {
        TextView show = message.isSendMsg() ? tv_receipt_left : tv_receipt_right;
        TextView hide = message.isSendMsg() ? tv_receipt_right : tv_receipt_left;

        if (!TioConfig.OpenCloseConfig.showReadStatus()){
            show.setVisibility(View.GONE);
            hide.setVisibility(View.GONE);
            return;
        }

        hide.setVisibility(View.GONE);

        Boolean displayReceipt = message.getReadMsg();
        if (!message.isSendMsg() || displayReceipt == null || message.getMsgType() == TioMsgType.tip) {
            // 不是发送的消息 || 无数据||系统消息
            show.setVisibility(View.GONE);
        } else {
            show.setVisibility(View.VISIBLE);
            if (displayReceipt) {
                // 已读
                show.setText(context.getString(R.string.have_read));
                show.setSelected(false);
            } else {
                // 未读
                show.setText(context.getString(R.string.no_read));
                show.setSelected(true);
            }
        }
    }

    private void setTimeView() {
//        Long time = message.getTime();
//        if (time != null) {
//            timeView.setVisibility(View.VISIBLE);
//            String timeText = TimeUtil.getShowTime(time, false);
//            timeView.setText(String.valueOf(timeText));
//        } else {
//            timeView.setVisibility(View.GONE);
//        }
        timeView.setVisibility(View.GONE);
    }

    private void setNickView() {
        TextView show = message.isSendMsg() ? nickRight : nickLeft;
        TextView hide = message.isSendMsg() ? nickLeft : nickRight;

        hide.setVisibility(View.GONE);

        boolean showName = message.isShowName();
        if (showName) {
            show.setVisibility(View.VISIBLE);
        } else {
            show.setVisibility(View.GONE);
        }
        setName(show);
    }

    private void setName(TextView show){
        String name = message.getName();
        if (message.isGroupMsg()){
            String uid = message.getUid();
            if (TioConfig.friendRemarkCacheMap.containsKey(uid)){
                if (TioConfig.friendRemarkCacheMap.get(uid) != null){
                    show.setText(TioConfig.friendRemarkCacheMap.get(uid));
                }else {
                    show.setText(StringUtils.null2Length0(name));
                }
            }else {
                //没有缓存，则从服务器上拿
                synchronized (this){
                    if (TioConfig.friendRemarkCacheMap.containsKey(uid)){
                        setName(show);
                        return;
                    }
                    UserInfoReq req = new UserInfoReq(uid);
                    TioHttpClient.get(this, req, new TaoCallback<BaseResp<UserInfoResp>>() {
                        @Override
                        public void onSuccess(Response<BaseResp<UserInfoResp>> response) {
                            UserInfoResp data = response.body().getData();
                            if (data != null) {
                                String remarkname = data.remarkname;
                                TioConfig.friendRemarkCacheMap.put(uid, remarkname);
                                setName(show);
                            }
                        }
                    });
                }
            }
        }else {
            show.setText(StringUtils.null2Length0(name));
        }
    }

    private void setContent() {
        if (isShowContentBg()) {
            if (message.isSendMsg()) {
                if (isOwner || isAdmin){
                    contentContainer.setBackgroundResource(rightContentBgIdAdmin);
                }else {
                    contentContainer.setBackgroundResource(rightContentBgId);
                }
                contentContainer.setPadding(SizeUtils.dp2px(10), SizeUtils.dp2px(8), SizeUtils.dp2px(10), SizeUtils.dp2px(8));
            } else {
                if (isOwner || isAdmin){
                    contentContainer.setBackgroundResource(leftContentBgIdAdmin);
                }else {
                    contentContainer.setBackgroundResource(leftContentBgId);
                }
                contentContainer.setPadding(SizeUtils.dp2px(10), SizeUtils.dp2px(8), SizeUtils.dp2px(10), SizeUtils.dp2px(8));
            }
        }
        contentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContentClick(v);
            }
        });
        contentContainer.setOnLongClickListener(onContentLongClick());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getAdapter().selectedChecbox.put(getMsgPosition(Long.parseLong(message.getId())),isChecked);
            }
        });
    }

    /**
     * 根据 mid 查找 消息位置
     *
     * @param mid 消息唯一id
     * @return 无则返回 -1
     */
    public int getMsgPosition(long mid) {
        int position = -1;
        List<TioMsg> data = getAdapter().getData();
        for (int i = 0, size = data.size(); i < size; i++) {
            String _mid = data.get(i).getId();
            if (_mid != null && _mid.equals(String.valueOf(mid))) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void setHeadImageView() {
        TioImageView show = message.isSendMsg() ? avatarRight : avatarLeft;
        TioImageView hide = message.isSendMsg() ? avatarLeft : avatarRight;

        hide.setVisibility(View.GONE);

        String avatar = message.getAvatar();
        if (avatar != null) {
            show.tio_roundAvatar(avatar);
            show.setVisibility(View.VISIBLE);

            if (getActivity() instanceof GroupSessionActivity){
                GroupSessionFragment fragmentByClass = (GroupSessionFragment) ((GroupSessionActivity) getActivity()).findFragmentByClass(GroupSessionFragment.class);
                if (fragmentByClass != null){
                    Integer myRole = fragmentByClass.myrole;
                    if (myRole != null){
                        show.setOnClickListener(onAvatarClicked(fragmentByClass));
                    }
                }
            }else {
                show.setOnClickListener(onAvatarClicked(null));
            }



            show.setOnLongClickListener(onAvatarLongClick());

            ClickUtils.applyPressedBgDark(show);
            ClickUtils.applyPressedViewScale(show);
        } else {
            show.setVisibility(View.GONE);
        }

        ivLableLeft.setVisibility(View.GONE);
        ivLableRight.setVisibility(View.GONE);
        //设置标签
        if (message.isGroupMsg() && message.getAvatar() != null){
            TextView ivShowLable = message.isSendMsg() ? ivLableRight:ivLableLeft;
            if (isOwner){
                ivShowLable.setVisibility(View.VISIBLE);
                ivShowLable.setText(R.string.group_owner);
//                ivShowLable.setImageResource(R.drawable.label_qunzhu);
            }else if (isAdmin){
                ivShowLable.setVisibility(View.VISIBLE);
//                ivShowLable.setImageResource(R.drawable.label_adminster);
                ivShowLable.setText(R.string.group_manager);
            }
        }
    }

    private boolean isManager(List<Integer> managerIds, String uid){
        if (managerIds == null){
            return false;
        }
        for (Integer id : managerIds){
            if (uid.equals(id.toString())){
                return true;
            }
        }
        return false;
    }


    // ==============================================================================
    // avatar
    // ==============================================================================

    protected View.OnClickListener onAvatarClicked(GroupSessionFragment groupSessionFragment) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (groupSessionFragment == null){
                    UserDetailActivity.start(context, message.getUid(),false, false);
                }else {
                    Integer myRole = CacheTableCrud.getGroupRole(String.valueOf(-Integer.parseInt(message.getChatLinkId())), Long.parseLong(message.getUid()));

                    UserDetailActivity.start(context, message.getUid(),myRole == 1 || myRole == 3 ? false : !groupSessionFragment.canFriend, myRole == 1 || myRole == 3);
                }
                // 进入用户信息页
            }
        };
    }

    protected View.OnLongClickListener onAvatarLongClick() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return getAdapter().onAvatarLongClick(v, getMessage());
            }
        };
    }

    // ==============================================================================
    // content
    // ==============================================================================

    protected abstract int contentResId();

    protected abstract void inflateContent();

    protected abstract void bindContent(BaseViewHolder holder);

    protected boolean isShowContentBg() {
        return false;
    }

    protected void onContentClick(View view) {

    }

    protected View.OnLongClickListener onContentLongClick() {
        return view -> {
            showAttachView(view, null);
            return true;
        };
    }

    public FrameLayout getContentContainer() {
        return contentContainer;
    }

    // ==============================================================================
    // 拓展
    // ==============================================================================

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 显示消息粘附视图
     *
     * @param attachView 粘附于哪个 view
     * @param copyText   复制的 text
     */
    protected void showAttachView(@NonNull View attachView, @Nullable String copyText) {
        // mid
        String mid = getMessage().getId();
        // chatlinkid
        String chatLinkId = getMessage().getChatLinkId();
        boolean isUpManager = false;
        if (getActivity() instanceof GroupSessionActivity){
            GroupSessionFragment fragmentByClass = (GroupSessionFragment) ((GroupSessionActivity) getActivity()).findFragmentByClass(GroupSessionFragment.class);
            if (fragmentByClass != null){
                List<Integer> managerIds = fragmentByClass.managerIds;
                Integer ownId = fragmentByClass.owerId;
                Integer myRole = fragmentByClass.myrole;
                if (managerIds != null && ownId != null && myRole != null){
                    if (myRole == 1){
                        isUpManager = true;
                    }else if (myRole == 2){
                        isUpManager = false;
                    }else {
                        //管理员
                        String msgUid = getMessage().getUid();
                        if (msgUid.equals(ownId.toString())){
                            isUpManager = false;
                        }else{
                            isUpManager = true;
                            for (Integer integer : managerIds){
                                if (integer.toString().equals(msgUid)){
                                    isUpManager = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 弹窗
        Context context = attachView.getContext();
        if (context instanceof Activity) {
            SessionMsgDialog sessionMsgDialog = new SessionMsgDialog((Activity) context);
            // 复制
            sessionMsgDialog.setCopyData(copyText)
                    // 消息撤回
                    .setWithdrawData(isUpManager, getAdapter().getChatLinkId(), mid, getMessage(), getMessage().getMsgType())
                    // 消息删除
                    .setDeleteData(isUpManager, getAdapter().getChatLinkId(), mid)
                    // 消息转发
                    .setForwardData(chatLinkId, mid, getMessage())
                    //收藏
                    .setCollectData(getPicWH(), getPicUrl(), isCollect(), getMessage())
                    // 举报
                    .setComplaintData(chatLinkId, mid, getMessage().getMsgType())
                    //保存媒体
                    .setSaveMediaData(getMessage().getContentObj(), getMessage().getMsgType())
                    // 显示
                    .show_canceledOnTouchOutside(attachView.getContext());

            sessionMsgDialog.getMutiChooseView().setOnClickListener(v -> {
                int  pos = getMsgPosition(Long.parseLong(getMessage().getId()));
                getAdapter().setMsgId(getMessage().getId());
                getAdapter().selectedChecbox.put(pos,true);
                sessionMsgDialog.dismiss();
                getAdapter().isMultiChoose = true;
                getAdapter().notifyDataSetChanged();
                getAdapter().showBotomDialog();
            });

        }
    }


    public boolean isCollect(){
        return false;
    }

    public int[] getPicWH(){
        return null;
    }

    public String getPicUrl(){
        return null;
    }


}
