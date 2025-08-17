package com.tiocloud.chat.feature.session.group.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.feature.forbidden.ForbiddenMvpPresenter;
import com.tiocloud.chat.feature.session.common.SessionFragment;
import com.tiocloud.chat.feature.session.common.adapter.msg.TioMsg;
import com.tiocloud.chat.feature.session.common.model.SessionExtras;
import com.tiocloud.chat.feature.session.common.panel.MsgInputPanel;
import com.tiocloud.chat.feature.session.group.fragment.ait.AitManager;
import com.tiocloud.chat.feature.session.group.fragment.msg.GroupMsg;
import com.tiocloud.chat.feature.session.group.fragment.mvp.GroupFragmentContract;
import com.tiocloud.chat.feature.session.group.fragment.mvp.GroupFragmentPresenter;
import com.tiocloud.chat.util.StringUtil;
import com.watayouxiang.androidutils.util.HtmlUtils;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.GroupInfoReq;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.body.wx.WxGroupChatReq;
import com.watayouxiang.imclient.packet.TioPacket;
import com.watayouxiang.imclient.packet.TioPacketBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2019-12-27
 * desc : 群聊
 */
public class GroupSessionFragment extends SessionFragment implements GroupFragmentContract.View {

    public List<Integer> managerIds;
    public Integer myrole = 2;
    public Integer owerId;
    public boolean canFriend = false;
    public String groupName;

    public static GroupSessionFragment create(@NonNull String chatLinkId, @NonNull String groupId) {
        GroupSessionFragment fragment = new GroupSessionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SessionExtras.EXTRA_CHAT_LINK_ID, chatLinkId);
        bundle.putString(SessionExtras.EXTRA_GROUP_ID, groupId);
        bundle.putString(SessionExtras.CHATMODE, "2");
        fragment.setArguments(bundle);
        return fragment;
    }

    public boolean isCusMember(Integer uid){
        if (managerIds == null){
            return true;
        }
        for (Integer integer : managerIds){
            if (integer == null){
                continue;
            }
            if (integer.intValue() == uid.intValue()){
                return false;
            }
        }
        if (owerId != null && owerId.intValue() == uid.intValue()){
            return false;
        }
        return true;
    }

    @NonNull
    @Override
    public String getGroupId() {
        return getArguments().getString(SessionExtras.EXTRA_GROUP_ID);
    }

    @NonNull
    @Override
    public List<String> getChatLinkIds() {
        String string = getArguments().getString(SessionExtras.EXTRA_CHAT_LINK_ID);
        List<String> strings = new ArrayList<>();
        strings.add(string);
        return strings;
    }

    // ====================================================================================
    // init
    // ====================================================================================

    private GroupFragmentPresenter presenter;
    private AitManager aitManager;
    private ForbiddenMvpPresenter forbiddenPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new GroupFragmentPresenter(this);
        forbiddenPresenter = new ForbiddenMvpPresenter(null);
        getGroupInfo();
//        showNotice("通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知");
    }

    public int getMyRole(){
        return myrole;
    }

    public void getGroupInfo(){
        GroupInfoReq groupInfoReq = new GroupInfoReq("1", getGroupId());
//        groupInfoReq.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
//        if (!TioConfig.OpenCloseConfig.enterGroupChatCheck()){
//            //不需要验证的时候，可使用缓存
//        }
        if (getActivity() == null){
            LogUtils.e("getActivity()==null");
            return;
        }
        groupInfoReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        TioHttpClient.get(this, groupInfoReq, new TaoCallback<BaseResp<GroupInfoResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<GroupInfoResp>> response) {
                GroupInfoResp data = response.body().getData();
                if (data != null && data.group != null) {
                    if (getActivity() == null){
                        return;
                    }
                    CacheTableCrud.insertOrUpdate("group_info_"+getGroupId(), data);
                    setGroupInfoData(data);

                    //保存群主
                    CacheTableCrud.setGroupRole(getGroupId(), String.valueOf(data.group.uid), 1);
                    //保存我的角色
                    if (data.groupuser != null){
                        CacheTableCrud.setGroupRole(getGroupId(), String.valueOf(TioDBPreferences.getCurrUid()), data.groupuser.grouprole);
                    }
                    //保存管理员
                    if (data.manges != null){
                        for (Integer integer : data.manges){
                            CacheTableCrud.setGroupRole(getGroupId(), String.valueOf(integer), 3);
                        }
                    }

                }
            }

            @Override
            public void onCacheSuccess(Response<BaseResp<GroupInfoResp>> response) {
                super.onCacheSuccess(response);
                GroupInfoResp data = response.body().getData();
                if (data != null && data.group != null) {
                    setGroupInfoData(data);
                }
            }

            @Override
            public void onError(Response<BaseResp<GroupInfoResp>> response) {
                super.onError(response);
                GroupInfoResp data = CacheTableCrud.getValue("group_info_" + getGroupId(), GroupInfoResp.class);
                if (data != null){
                    setGroupInfoData(data);
                }
            }
        });
    }

    private void setGroupInfoData(GroupInfoResp data){
        managerIds = data.manges;
        owerId = data.group.uid;
        canFriend = data.group.friendflag == 1;
        if (data.groupuser != null){
            myrole = data.groupuser.grouprole;
        }else {
            getActivity().finish();
            ToastUtils.showShort(getString(R.string.you_kickout_group_chat));
            return;
        }
        groupName = data.group.name;
        refreshNotice(data.group.notice);
        presenter.init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        aitManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (aitManager != null){
            aitManager.reset();
        }
        forbiddenPresenter.detachView();
    }

    @Override
    public boolean onAvatarLongClick(View v, TioMsg msg) {
        if (msg == null) return false;
        if (msg.isSendMsg()) return false;

        String uid = msg.getUid();
        String groupId = getGroupId();

        boolean hasKick = (myrole == 1 || myrole == 3) && isCusMember(Integer.parseInt(uid));

        forbiddenPresenter.longClickAvatar(hasKick, uid, groupId, v, () -> aitManager.insertAitMemberInner(msg));
        return true;
    }

    @Override
    public boolean sendMessage(String msg) {
        String aitStr = aitManager.getAitTeamMemberStr();
        String escapeMsg = HtmlUtils.escapeHtml(msg);
        int chatLinkId = Integer.parseInt(getChatLinkIds().get(0));

        WxGroupChatReq body = new WxGroupChatReq(escapeMsg, chatLinkId, aitStr);

//        presenter.getView().getMsgListProxy().sendMsg(new GroupMsg(wxGroupChatNtf, currUid, currNick));
//        presenter.getView().getMsgListProxy().sendMsg(body);

        TioPacket packet = TioPacketBuilder.getWxGroupChatReq(body);
        boolean isSend = TioIMClient.getInstance().sendPacket(packet);

        if (isSend) {
            aitManager.reset();
        }

        return isSend;
    }

    @Override
    public void resetUI() {
        // 列表初始化
        getMsgListProxy().setOnFetchMoreListener(() -> presenter.loadMore());
        // 艾特功能实现
        initAitManager();
    }

    @Override
    public void toRefreshNotice(boolean showDialog, String notice) {
        refreshNotice(notice);

    }

    // ====================================================================================
    // ait相关
    // ====================================================================================

    private void initAitManager() {
        aitManager = new AitManager(this, getGroupId());
        MsgInputPanel inputPanel = getInputPanel();
        inputPanel.setAitTextWatcher(aitManager);
        aitManager.setTextChangeListener(inputPanel);
    }

}
