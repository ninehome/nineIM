package com.tiocloud.chat.feature.group.info.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tiocloud.account.TioAccount;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.TioConfig;
import com.tiocloud.chat.constant.TioExtras;
import com.tiocloud.chat.feature.curr.detail.CurrDetailActivity;
import com.tiocloud.chat.feature.curr.modify.ModifyActivity;
import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.group.info.GroupInfoActivity;
import com.tiocloud.chat.feature.group.info.fragment.adapter.MemberItem;
import com.tiocloud.chat.feature.group.info.fragment.adapter.MemberListAdapter;
import com.tiocloud.chat.feature.group.info.fragment.mvp.FragmentGroupInfoContract;
import com.tiocloud.chat.feature.group.info.fragment.mvp.FragmentGroupInfoPresenter;
import com.tiocloud.chat.feature.group.member.GroupMemberActivity;
import com.tiocloud.chat.feature.group.mgr.GroupMgrActivity;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.dialog.tio.PicSelectDialog;
import com.watayouxiang.androidutils.engine.EasyPhotosEngine;
import com.watayouxiang.androidutils.listener.OnTioClickListener;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.util.UrlUtil;
import com.watayouxiang.androidutils.widget.CommonTextInputDialog;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.CacheTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.ModifyGroupNickReq;
import com.watayouxiang.httpclient.model.request.ModifyNameReq;
import com.watayouxiang.httpclient.model.request.UpdateAvatarReq;
import com.watayouxiang.httpclient.model.request.UpdateGroupAvatarReq;
import com.watayouxiang.httpclient.model.response.GroupInfoResp;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;
import com.watayouxiang.qrcode.TioQRCode;
import com.watayouxiang.qrcode.feature.qrcode_group.GroupQRCodeActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class GroupInfoFragment extends TioFragment implements FragmentGroupInfoContract.View {

    private FragmentGroupInfoPresenter presenter;
    private ViewHolder holder;
    private MemberListAdapter memberListAdapter;
    private final static int REQ_CODE_IMAGE_GIF = 1333;
    private Uri mDestination;

    public static GroupInfoFragment create(String groupId) {
        GroupInfoFragment fragment = new GroupInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TioExtras.EXTRA_GROUP_ID, groupId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getGroupId() {
        return getArguments().getString(TioExtras.EXTRA_GROUP_ID);
    }

    @Override
    public String getChatLinkId() {
        String groupId = getGroupId();
        int _groupId = Integer.parseInt(groupId);
        return String.valueOf(-_groupId);
    }

    public static class ViewHolder {
        public TextView tv_groupName;
        public TextView tv_viewAllMember;
        public LinearLayout ll_viewAllMember;
        public TextView tv_groupIntro;
        public TextView tv_groupNotice;
        public RecyclerView rv_memberList;
        public RelativeLayout rl_deleteChatRecord;
        public RelativeLayout rl_complaint;
        public LinearLayout ll_groupName;
        public LinearLayout ll_avatar;
        public View view_avatar_line;
        public LinearLayout ll_groupNotice;
        public LinearLayout ll_groupIntro;
        public ImageView iv_arrow_groupName;
        public LinearLayout ll_groupOwner;
        public TextView tv_groupOwner;
        public LinearLayout ll_groupNick;
        public TextView tv_groupNick;
        public RelativeLayout rl_QRCode;
        public RelativeLayout rl_groupMgr;
        public View v_groupMgrBottom;
        public CheckBox switch_no_distrub;

        private ViewHolder(View v) {
            tv_groupName = v.findViewById(R.id.tv_groupName);
            tv_viewAllMember = v.findViewById(R.id.tv_viewAllMember);
            ll_viewAllMember = v.findViewById(R.id.ll_viewAllMember);
            tv_groupIntro = v.findViewById(R.id.tv_groupIntro);
            tv_groupNotice = v.findViewById(R.id.tv_groupNotice);
            rv_memberList = v.findViewById(R.id.rv_memberList);
            rl_deleteChatRecord = v.findViewById(R.id.rl_deleteChatRecord);
            rl_complaint = v.findViewById(R.id.rl_complaint);
            ll_groupName = v.findViewById(R.id.ll_groupName);
            ll_groupNotice = v.findViewById(R.id.ll_groupNotice);
            ll_groupIntro = v.findViewById(R.id.ll_groupIntro);
            iv_arrow_groupName = v.findViewById(R.id.iv_arrow_groupName);
            ll_groupOwner = v.findViewById(R.id.ll_groupOwner);
            tv_groupOwner = v.findViewById(R.id.tv_groupOwner);
            ll_groupNick = v.findViewById(R.id.ll_groupNick);
            tv_groupNick = v.findViewById(R.id.tv_groupNick);
            rl_QRCode = v.findViewById(R.id.rl_QRCode);
            rl_groupMgr = v.findViewById(R.id.rl_groupMgr);
            v_groupMgrBottom = v.findViewById(R.id.v_groupMgrBottom);
            switch_no_distrub = v.findViewById(R.id.switch_no_distrub);
            ll_avatar = v.findViewById(R.id.ll_avatar);
            view_avatar_line = v.findViewById(R.id.view_avatar_line);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new FragmentGroupInfoPresenter(this);
        View view = inflater.inflate(R.layout.tio_group_info_fragment, container, false);
        holder = new ViewHolder(view);
        mDestination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropImage.jpeg"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadUIData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public TioActivity getTioActivity() {
        return (TioActivity) getActivity();
    }

    @Override
    public void initPageUI() {
        if (holder == null) return;
        if (presenter == null) return;

        // 成员列表
        memberListAdapter = new MemberListAdapter(null, holder.rv_memberList);
        memberListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MemberItem item = memberListAdapter.getData().get(position);
                if (item.getItemType() == MemberItem.BUTTON) {// 按钮
                    item.button.onItemClick(getTioActivity(), getGroupId());
                } else if (item.getItemType() == MemberItem.USER) {// 用户
                    UserDetailActivity.start(getTioActivity(), String.valueOf(item.user.uid), myRole == 1 || myRole == 3 ? false : !canFriend, myRole == 1 || myRole == 3);
                }
            }
        });

        int myrole = CacheTableCrud.getGroupRoleMy(getGroupId());
        if (myrole == 1 || myrole == 3){
            holder.ll_avatar.setVisibility(View.VISIBLE);
            holder.view_avatar_line.setVisibility(View.VISIBLE);
        }else {
            holder.ll_avatar.setVisibility(View.GONE);
            holder.view_avatar_line.setVisibility(View.GONE);
        }

        // 清除聊天记录
        holder.rl_deleteChatRecord.setVisibility(View.VISIBLE);
        holder.rl_deleteChatRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showClearChatRecordDialog();
            }
        });
        // 投诉
        holder.rl_complaint.setOnClickListener(v -> presenter.reqComplaint(getChatLinkId()));
        // 群二维码
        if (TioQRCode.IS_OPEN/* && joinFlag*/) {
            holder.rl_QRCode.setVisibility(View.VISIBLE);
            holder.rl_QRCode.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
                @Override
                public void onDebouncingClick(View v) {
                    GroupQRCodeActivity.start(getTioActivity(), getGroupId());
                }
            });
        } else {
            holder.rl_QRCode.setVisibility(View.GONE);
        }
        // 群管理
        if(holder.rl_groupMgr != null){
            holder.rl_groupMgr.setVisibility(View.GONE);
        }
        if(holder.v_groupMgrBottom != null){
            holder.v_groupMgrBottom.setVisibility(View.GONE);
        }
        holder.rl_groupMgr.setOnClickListener(new OnTioClickListener() {
            @Override
            public void onSingleClick(View view) {
                GroupMgrActivity.start(getTioActivity(), getGroupId());
            }
        });

        holder.ll_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PicSelectDialog(getActivity())
                        .setOnBtnListener(new PicSelectDialog.OnBtnListener() {
                            @Override
                            public void onClickPositive(View view, int sex, PicSelectDialog dialog) {
                                if (sex == 0){
                                    EasyPhotos.createCamera(GroupInfoFragment.this, true)
                                            .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                                            .start(REQ_CODE_IMAGE_GIF);
                                }else {
                                    EasyPhotos.createAlbum(GroupInfoFragment.this, false, true, EasyPhotosEngine.getInstance())
                                            .setFileProviderAuthority("com.tiocloud.chat.fileprovider")
                                            .setPuzzleMenu(false)
                                            .setCleanMenu(false)
                                            .setCount(1)
                                            .setVideo(false)
                                            .setGif(true)
                                            .start(REQ_CODE_IMAGE_GIF);
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onClickNegative(View view, PicSelectDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        presenter.getAvatarDialog().onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_IMAGE_GIF) {
            // 容错处理
            if (data == null) return;
            //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
            boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
            TioLogger.i(String.valueOf(resultPhotos));

            // 容错处理
            if (resultPhotos == null || resultPhotos.size() == 0) {
                return;
            }

            // 判断类型
            Photo photo = resultPhotos.get(0);
            if (UrlUtil.isImageSuffix(photo.path) || UrlUtil.isGifSuffix(photo.path)) {
                UCrop.Options options = new UCrop.Options();
                // 修改标题栏颜色
                options.setToolbarColor(this.getResources().getColor(R.color.white));
                options.setStatusBarColor(this.getResources().getColor(R.color.white));
                options.setToolbarWidgetColor(this.getResources().getColor(R.color.black));
                // 隐藏底部工具
                options.setHideBottomControls(true);
                // 图片格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                // 设置图片压缩质量
                options.setCompressionQuality(100);
                // 上传图片
                UCrop.of(photo.uri, mDestination)
                        // 长宽比
                        .withAspectRatio(1, 1)
                        // 图片大小
                        .withMaxResultSize(512, 512)
                        // 配置参数
                        .withOptions(options)
                        .start(requireActivity(), GroupInfoFragment.this, UCrop.REQUEST_CROP);
            }
        }else if (requestCode == UCrop.REQUEST_CROP){
            handleCropResult(data);
        }
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        try {
            final Uri resultUri = UCrop.getOutput(result);
            Luban.with(requireActivity()).load(resultUri).ignoreBy(100).setTargetDir(requireActivity().getCacheDir().getAbsolutePath()).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    LogUtils.i("压缩开始");
                }

                @Override
                public void onSuccess(File file) {
                    LogUtils.i("压缩成功");
                    uploadAvatar(file.getPath());
                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.e("压缩失败" + e.getMessage());
                    uploadAvatar(resultUri.getPath());
                }
            }).launch();
        }catch (Exception e){
            Toast.makeText(getActivity(), getString(R.string.cannot_crop_pic), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void uploadAvatar(String path) {
        UpdateGroupAvatarReq req = new UpdateGroupAvatarReq(getGroupId(), path);
        req.setCancelTag(this);
        req.upload(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                ToastUtils.showShort(getString(R.string.group_head_update));
            }

            @Override
            public void onTioError(String msg) {

            }
        });
    }

    @Override
    public void setMenuBtn(final boolean groupOwner, final boolean showExit) {
        if (getActivity() instanceof GroupInfoActivity) {
            GroupInfoActivity activity = (GroupInfoActivity) getActivity();
            activity.getTitleBar().getIvRight().setOnClickListener(view -> {
                if (presenter == null) return;
                presenter.showOperDialog(groupOwner, showExit);
            });
        }
    }
    boolean isManager = false;
    boolean canFriend = false;
    boolean joinFlag = false;
    int myRole = 2;
    @Override
    public void setUIData(GroupInfoResp groupInfo) {
        if (groupInfo == null) return;
        final GroupInfoResp.Group group = groupInfo.group;
        GroupInfoResp.GroupUser groupUser = groupInfo.groupuser;
        if (groupUser == null || group == null) return;
        if (holder == null) return;
        if (presenter == null) return;
        myRole = groupUser.grouprole;
        final boolean groupOwner = groupUser.grouprole == 1;
        isManager = groupUser.grouprole == 1 || groupUser.grouprole == 3;
        canFriend = group.friendflag == 1;
        joinFlag = group.joinmode == 2;
        // 查看全部成员
//        holder.ll_viewAllMember.setVisibility(View.VISIBLE);
        if (isManager || canFriend){
            holder.ll_viewAllMember.setVisibility(View.VISIBLE);
            // 查看所有成员
            holder.ll_viewAllMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupMemberActivity.start(getTioActivity(), getGroupId(), myRole, canFriend, isManager);
                }
            });
        }else {
            holder.ll_viewAllMember.setVisibility(View.VISIBLE);
            holder.ll_viewAllMember.setEnabled(false);
        }
        holder.tv_viewAllMember.setText(String.format(Locale.getDefault(),
                getString(R.string.query_all_mamber), group.joinnum));
        // 名称
        holder.tv_groupName.setText(StringUtil.nonNull(group.name));
        holder.iv_arrow_groupName.setVisibility(groupOwner ? View.VISIBLE : View.INVISIBLE);
        holder.ll_groupName.setOnClickListener(groupOwner ? new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ModifyActivity.start_group(view.getContext(), ModifyType.GROUP_NAME, group.id, group.name);
                new CommonTextInputDialog(getActivity())
                        .setTopTitle(getString(R.string.repair_group_nick))
                        .setSubTitle(getString(R.string.good_group_nick))
                        .setPositiveText(getString(R.string.confirm))
                        .setEdittext(group.name)
                        .setMaxLimit(30)
                        .setEditHeight(60)
                        .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                        if (TextUtils.isEmpty(submitTxt)){
                            ToastUtils.showShort(getString(R.string.group_nick_not_empty));
                            return;
                        }
                        ModifyNameReq req = new ModifyNameReq(group.id, submitTxt.trim());
                        req.setCancelTag(this);
                        req.post(new TioCallback<String>() {
                            @Override
                            public void onTioSuccess(String s) {
                                presenter.loadUIData();
                            }

                            @Override
                            public void onTioError(String msg) {
                            }
                        });
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickNegative(View view, CommonTextInputDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } : null);
        holder.switch_no_distrub.setChecked(TioConfig.isNoDisturbBizId(2, getGroupId()));
        holder.switch_no_distrub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TioConfig.saveNoDisturbBizId(2, getGroupId(), b);
                TioConfig.saveNoDisturbChatLinkId(2, getChatLinkId(), b);
            }
        });
        holder.ll_groupName.setClickable(groupOwner);
        // 介绍
        holder.tv_groupIntro.setText(StringUtil.nonNull(group.intro));
        holder.ll_groupIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickGroupIntroItem(isManager, group);
            }
        });
        // 公告
        holder.tv_groupNotice.setText(StringUtil.nonNull(group.notice));
        holder.ll_groupNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickGroupNoticeItem(isManager, group);
            }
        });
        // 群昵称
        if (!TioConfig.OpenCloseConfig.isShowGroupNick()){
            holder.ll_groupNick.setVisibility(View.GONE);
        }else {
            holder.tv_groupNick.setText(StringUtil.nonNull(groupUser.groupnick));
            holder.ll_groupNick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ModifyActivity.start_group(v.getContext(), ModifyType.GROUP_NICK, group.id, groupUser.groupnick);
                    new CommonTextInputDialog(getActivity())
                            .setEdittext(groupUser.groupnick)
                            .setTopTitle(getString(R.string.my_group_nick))
                            .setSubTitle(getString(R.string.good_nick))
                            .setMaxLimit(20)
                            .setEditHeight(60)
                            .setPositiveText(getString(R.string.submit))
                            .setOnBtnListener(new CommonTextInputDialog.OnBtnListener() {
                                @Override
                                public void onClickPositive(View view, String submitTxt, CommonTextInputDialog dialog) {
                                    if (TextUtils.isEmpty(submitTxt)){
                                        ToastUtils.showShort(getString(R.string.nick_not_empty));
                                        return;
                                    }
                                    try {
                                        int limit = TioAccount.isOA ? 4 : 1;
                                        if (submitTxt.trim().getBytes("utf-8").length < limit) {
                                            TioToast.showShort(String.format(Locale.getDefault(),
                                                    getString(R.string.group_nick_not_low), limit));
                                            return;
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    dialog.dismiss();
                                    ModifyGroupNickReq req = new ModifyGroupNickReq(group.id, submitTxt.trim());
                                    req.setCancelTag(this);
                                    req.post(new TioCallback<String>() {
                                        @Override
                                        public void onTioSuccess(String s) {
                                            presenter.loadUIData();
                                        }

                                        @Override
                                        public void onTioError(String msg) {
                                        }
                                    });
                                }

                                @Override
                                public void onClickNegative(View view, CommonTextInputDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
        }
        // 群管理
        holder.rl_groupMgr.setVisibility(isManager ? View.VISIBLE : View.GONE);
        if(holder.v_groupMgrBottom != null){
            holder.v_groupMgrBottom.setVisibility(isManager ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public MemberListAdapter getMemberListAdapter() {
        return memberListAdapter;
    }

    @Override
    public void setGroupOwnerInfo(GroupUserListResp.GroupMember user) {
        if (user == null) return;
        if (holder == null) return;

        String nick = user.nick;
        final int uid = user.uid;

        // 群主
        holder.tv_groupOwner.setText(StringUtil.nonNull(nick));
        holder.ll_groupOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetailActivity.start(getTioActivity(), String.valueOf(uid));
            }
        });
    }
}
