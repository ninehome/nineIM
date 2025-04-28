package com.tiocloud.chat.feature.session.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.adapter.MsgAdapter;
import com.tiocloud.chat.feature.session.common.adapter.viewholder.base.MsgBaseViewHolder;
import com.tiocloud.chat.feature.session.group.GroupSessionActivity;
import com.tiocloud.chat.feature.session.group.fragment.GroupSessionFragment;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;

/**
 * author : TaoWang
 * date : 2020/4/2
 * desc : 名片
 */
public class MsgCardViewHolder extends MsgBaseViewHolder {

    private TioImageView hiv_avatar;
    private TextView tv_cardType;
    private ImageView iv_cardType;
    private TextView tv_usrName;

    private InnerMsgCard cardData;

    public MsgCardViewHolder(MsgAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int contentResId() {
        return R.layout.tio_msg_item_card;
    }

    @Override
    protected void inflateContent() {
        hiv_avatar = findViewById(R.id.hiv_avatar);
        tv_cardType = findViewById(R.id.tv_cardType);
        iv_cardType = findViewById(R.id.iv_cardType);
        tv_usrName = findViewById(R.id.tv_usrName);
    }

    @Override
    protected void bindContent(BaseViewHolder holder) {
        cardData = (InnerMsgCard) getMessage().getContentObj();
        if (cardData == null) return;

        // 名片头像
        hiv_avatar.load_msg_card(cardData.bizavatar);
        // 名片类型
        if (cardData.cardtype == 1) {
            tv_cardType.setText(getContext().getString(R.string.person_card));
            iv_cardType.setImageResource(R.drawable.ic_user_card_type_session);
        } else if (cardData.cardtype == 2) {
            tv_cardType.setText(getContext().getString(R.string.group_card));
            iv_cardType.setImageResource(R.drawable.ic_group_card_type_session);
        }
        // 名片名称
        tv_usrName.setText(StringUtil.nonNull(cardData.bizname));
    }

    @Override
    protected void onContentClick(View view) {
        if (cardData == null) return;

        if (getActivity() != null && getActivity() instanceof GroupSessionActivity){
            GroupSessionFragment fragmentByClass = (GroupSessionFragment) ((GroupSessionActivity) getActivity()).findFragmentByClass(GroupSessionFragment.class);
            if (!fragmentByClass.canFriend){
                if (fragmentByClass.myrole != 1 && fragmentByClass.myrole != 3){
                    return;
                }
            }
        }

        String bizid = cardData.bizid;
        if (cardData.cardtype == 1) {// 个人名片
            getAdapter().getCardPresenter().openP2PCard(view.getContext(), bizid);
        } else if (cardData.cardtype == 2) {// 群名片
            getAdapter().getCardPresenter().openGroupCard(view.getContext(), bizid, String.valueOf(cardData.shareFromUid));
        }
    }
}
