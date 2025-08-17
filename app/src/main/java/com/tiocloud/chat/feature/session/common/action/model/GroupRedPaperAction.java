package com.tiocloud.chat.feature.session.common.action.model;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseRedPaperAction;
import com.watayouxiang.wallet.feature.redpaper.RedPaperActivity;

/**
 * author : TaoWang
 * date : 2020/3/5
 * desc :
 */
public class GroupRedPaperAction extends BaseRedPaperAction {
    public GroupRedPaperAction() {
        super(R.drawable.icon_im_hongbao, R.string.red_paper);
    }

    @Override
    public void onClick() {
        RedPaperActivity.startGroup(activity, chatLinkIds.get(0));
    }
}
