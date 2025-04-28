package com.tiocloud.chat.feature.share.group.feature.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.databinding.FragmentShareGroupResultBinding;
import com.tiocloud.chat.feature.share.group.ShareGroupActivity;
import com.tiocloud.chat.feature.share.group.model.ShareCardTo;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.MailListReq;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.widget.TioToast;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/20
 *     desc   :
 * </pre>
 */
public class ResultFragment extends TioFragment {

    private FragmentShareGroupResultBinding binding;
    private ResultAdapter resultAdapter;
    private String searchKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShareGroupResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void load() {
        if (resultAdapter == null) return;
        if (searchKey == null) return;

        MailListReq mailListReq = new MailListReq(null, searchKey);
        mailListReq.setCancelTag(this);
        mailListReq.get(new TioCallback<MailListResp>() {
            @Override
            public void onTioSuccess(MailListResp mailListResp) {
                resultAdapter.setNewData(mailListResp, searchKey);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private void initRecyclerView() {
        resultAdapter = new ResultAdapter(binding.recyclerView) {
            @Override
            public void onClickFriendItem(MailListResp.Friend friend) {
                super.onClickFriendItem(friend);
                ShareGroupActivity shareSessionActivity = getShareSessionActivity();
                if (shareSessionActivity != null) {
                    ShareCardTo shareCardTo = getShareCardTo(friend);
                    shareSessionActivity.shareCard(shareCardTo);
                }
            }

            private ShareCardTo getShareCardTo(MailListResp.Friend friend) {
                return new ShareCardTo(friend.avatar, friend.nick, String.valueOf(friend.uid), null);
            }

            @Override
            public void onClickGroupItem(MailListResp.Group group) {
                super.onClickGroupItem(group);
                ShareGroupActivity shareSessionActivity = getShareSessionActivity();
                if (shareSessionActivity != null) {
                    ShareCardTo shareCardTo = getShareCardTo(group);
                    shareSessionActivity.shareCard(shareCardTo);
                }
            }

            private ShareCardTo getShareCardTo(MailListResp.Group group) {
                return new ShareCardTo(group.avatar, group.name, null, group.groupid);
            }
        };
    }

    private @Nullable
    ShareGroupActivity getShareSessionActivity() {
        if (getActivity() instanceof ShareGroupActivity) {
            return (ShareGroupActivity) getActivity();
        }
        return null;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        load();
    }
}
