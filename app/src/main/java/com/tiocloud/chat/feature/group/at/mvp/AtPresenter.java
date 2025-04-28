package com.tiocloud.chat.feature.group.at.mvp;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.AtGroupUserListReq;
import com.watayouxiang.httpclient.model.response.AtGroupUserListResp;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.ListIterator;

public class AtPresenter extends AtContract.Presenter {
    private final String currUid = String.valueOf(TioDBPreferences.getCurrUid());

    public AtPresenter(AtContract.View view) {
        super(new AtModel(), view, false);
    }

    @Override
    public void init() {
        getView().bindContentView();
        getView().initEditText();
        getView().initRecyclerView();
        load(null);
    }

    private void load(@Nullable String searchkey) {
        AtGroupUserListReq atGroupUserListReq = new AtGroupUserListReq(getView().getGroupId(), searchkey);
        atGroupUserListReq.setCancelTag(this);
        atGroupUserListReq.get(new TioCallback<AtGroupUserListResp>() {
            @Override
            public void onTioSuccess(AtGroupUserListResp lists) {
                removeMyself(lists);
                getView().onAtGroupUserListResp(lists, searchkey);
            }

            private void removeMyself(@Nullable AtGroupUserListResp lists) {
                if (lists != null && !lists.isEmpty()) {
                    ListIterator<AtGroupUserListResp.List> it = lists.listIterator();
                    while (it.hasNext()) {
                        String uid = String.valueOf(it.next().uid);
                        if (StringUtil.equals(uid, currUid)) {
                            it.remove();
                        }
                    }
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    @Override
    public void onEtTextChanged(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            load(null);
        } else {
            load(s.toString());
        }
    }
}
