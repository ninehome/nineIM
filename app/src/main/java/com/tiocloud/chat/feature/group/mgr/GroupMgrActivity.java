package com.tiocloud.chat.feature.group.mgr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.databinding.TioGroupMgrActivityBinding;
import com.tiocloud.chat.feature.forbidden.ForbiddenMvpPresenter;
import com.tiocloud.chat.feature.group.member.GroupMemberActivity;
import com.tiocloud.chat.feature.group.mgr.mvp.Contract;
import com.tiocloud.chat.feature.group.mgr.mvp.Presenter;
import com.tiocloud.chat.feature.group.silent.SilentMgrActivity;
import com.watayouxiang.androidutils.listener.OnTioClickListener;
import com.watayouxiang.androidutils.listener.SimpleOnCheckedChangeListener;
import com.watayouxiang.androidutils.page.easy.EasyActivity;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2021/01/05
 *     desc   : 群管理
 * </pre>
 */
public class GroupMgrActivity extends EasyActivity<TioGroupMgrActivityBinding> implements Contract.View {

    private Presenter presenter;
    private final ForbiddenMvpPresenter forbiddenPresenter = new ForbiddenMvpPresenter(null);

    public static void start(Context context, String groupId) {
        Intent starter = new Intent(context, GroupMgrActivity.class);
        starter.putExtra(TioExtras.EXTRA_GROUP_ID, groupId);
        context.startActivity(starter);
    }

    public String getGroupId() {
        return getIntent().getStringExtra(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.tio_group_mgr_activity;
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    protected Integer getStatusBarColor() {
        return getResources().getColor(R.color.grayf4f5f6);
    }

    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this);
        presenter.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        forbiddenPresenter.detachView();
    }

    @Override
    public void resetUI() {
        // 开启成员邀请
        binding.switchToggleInviteMember.setChecked(false);
        binding.switchToggleInviteMember.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                presenter.toggleInviteMemberSwitch(isChecked, compoundButton);
            }
        });

        binding.switchInviteMemberVerify.setChecked(false);
        binding.switchInviteMemberVerify.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                presenter.inviteMemberVerifySwitch(isChecked, compoundButton);
            }
        });
        binding.switchBanP2pChat.setChecked(false);
        binding.switchBanP2pChat.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                presenter.banP2pChatSwitch(isChecked, compoundButton);
            }
        });

        binding.switchBanMemeberExit.setChecked(false);
        binding.switchBanMemeberExit.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                presenter.banMemberExitSwitch(isChecked, compoundButton);
            }
        });

        // 禁言列表
        binding.tvBanListSubtitle.setText("");
        // 群体禁言
        binding.switchBanAll.setChecked(false);
        binding.switchBanAll.setOnCheckedChangeListener(new SimpleOnCheckedChangeListener() {
            @Override
            public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                super.onUserCheckedChanged(compoundButton, isChecked);
                forbiddenPresenter.toggleSwitch_ForbiddenGroupMember(getGroupId(), compoundButton, isChecked);
            }
        });
        // 禁言名单
        binding.rlBanList.setOnClickListener(new OnTioClickListener() {
            @Override
            public void onSingleClick(View view) {
                SilentMgrActivity.start(getActivity(), getGroupId());
            }
        });
    }

    @Override
    public void onGroupInfoResp(GroupInfoResp groupInfo) {
        final GroupInfoResp.Group group = groupInfo.group;
        GroupInfoResp.GroupUser groupUser = groupInfo.groupuser;
        if (groupUser == null || group == null) return;

        // 成员邀请开关
        binding.switchToggleInviteMember.setChecked(group.isAllowInviteMember());
        // 全体禁言开关
        binding.switchBanAll.setChecked(group.isForbiddenMemberTalk());
        if (groupUser.grouprole == 1){
            binding.rlManagerlist.setVisibility(View.GONE);
//            binding.rlManagerlist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    GroupMemberActivity.start(GroupMgrActivity.this, group.id, true);
//                }
//            });
        }else {
            binding.rlManagerlist.setVisibility(View.GONE);
        }
        binding.switchInviteMemberVerify.setChecked(group.joinmode == 1);
        binding.switchBanP2pChat.setChecked(group.friendflag == 2);
        binding.switchBanMemeberExit.setChecked(group.exitflag == 2);
    }

    @Override
    public void onForbiddenUserListResp(ForbiddenUserListResp resp) {
        int totalRow = resp.getTotalRow();
        // 禁言人数
        binding.tvBanListSubtitle.setText(StringUtils.format(getString(R.string.speople), totalRow));
    }
}
