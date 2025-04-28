package com.tiocloud.chat.feature.share.friend.mvp;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.feature.share.friend.model.ShareCardEntity;
import com.tiocloud.chat.feature.share.friend.model.ShareCardFrom;
import com.tiocloud.chat.feature.share.friend.model.ShareCardTo;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ShareCardReq;
import com.watayouxiang.androidutils.widget.TioToast;

public class ShareFriendPresenter extends ShareFriendContract.Presenter {
    public ShareFriendPresenter(ShareFriendContract.View view) {
        super(new ShareFriendModel(), view, false);
    }

    @Override
    public void initUI() {
        getView().bindContentView();
        getView().initEditText();
        getView().initFragmentContainers();
        getView().showRecentPage();
    }

    @Override
    public void onEtTextChanged(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            getView().showRecentPage();
        } else {
            getView().showSearchResultPage(s.toString());
        }
    }

    @Override
    public void shareCard(ShareCardTo to) {
        ShareCardFrom from = new ShareCardFrom(getView().getFromFriendUid());
        ShareCardEntity entity = new ShareCardEntity(to, from);
        getView().showCardDialog(entity);
    }

    @Override
    public void reqShareCard(String chatmode, String uids, String groupids, String cardid) {
        ShareCardReq shareCardReq = getModel().getShareCardReq(chatmode, uids, groupids, cardid);
        shareCardReq.post(new TioCallback<String>() {
            @Override
            public void onTioSuccess(String s) {
                getView().getActivity().finish();
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }
}
