package com.tiocloud.chat.feature.group.invitemember.fragment.mvp;

import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.invitemember.fragment.adapter.InviteMemberAdapter;
import com.watayouxiang.httpclient.model.response.ApplyGroupFdListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.widget.TioToast;

import java.util.LinkedList;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class InviteMemberPresenter extends InviteMemberContract.Presenter {

    public InviteMemberPresenter(InviteMemberContract.View view) {
        super(new InviteMemberModel(), view);
    }

    @Override
    public void init() {
        getView().initRecyclerView();
        initMenuBtn();
        search(null);
    }

    @Override
    public void search(final String keyWord) {
        final InviteMemberAdapter adapter = getView().getListAdapter();
        if (adapter == null) return;
        getModel().getApplyGroupFdList(getView().getGroupId(), keyWord, new BaseModel.DataProxy<ApplyGroupFdListResp>() {
            @Override
            public void onSuccess(ApplyGroupFdListResp friends) {
                adapter.setNewData(friends, keyWord);
            }
        });
    }

    // ====================================================================================
    // 安装菜单
    // ====================================================================================

    private TextView tv_menuBtn;

    @Override
    public void installMenuBtn(TextView tv_menuBtn) {
        this.tv_menuBtn = tv_menuBtn;
    }

    private void initMenuBtn() {
        final InviteMemberAdapter adapter = getView().getListAdapter();
        if (adapter == null) return;

        tv_menuBtn.setEnabled(false);
        tv_menuBtn.setText(getView().getActivity().getString(R.string.invitation));

        adapter.setOnCheckedChangeListener(linkedList -> {
            if (linkedList.size() > 0) {
                tv_menuBtn.setText(String.format(Locale.getDefault(), getView().getActivity().getString(R.string.invitate), linkedList.size()));
                tv_menuBtn.setEnabled(true);
            } else {
                tv_menuBtn.setText(getView().getActivity().getString(R.string.invitation));
                tv_menuBtn.setEnabled(false);
            }
        });
        tv_menuBtn.setOnClickListener(view -> inviteMember());
    }

    // ====================================================================================
    // 邀请入群
    // ====================================================================================

    private void inviteMember() {
        final InviteMemberAdapter adapter = getView().getListAdapter();
        if (adapter == null) return;
        LinkedList<String> checkedIds = adapter.getCheckedIds();
        String uidList = StringUtil.list2String(checkedIds);
        if (uidList == null) return;
        postInviteMember(uidList);
    }

    private void postInviteMember(String uidList) {
        getModel().postJoinGroup(getView().getGroupId(), uidList, new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String createGroupResp) {
                getView().getActivity().finish();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                TioToast.show(getView().getActivity(), msg);
            }
        });
    }
}
