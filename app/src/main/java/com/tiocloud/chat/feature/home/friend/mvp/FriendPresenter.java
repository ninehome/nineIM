package com.tiocloud.chat.feature.home.friend.mvp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.sina.weibo.sdk.utils.LogUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioFriendFragmentBinding;
import com.tiocloud.chat.event.UpdateFriendApplyNumEvent;
import com.tiocloud.chat.feature.home.chat.ChatFragment;
import com.tiocloud.chat.feature.home.friend.adapter.ContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.IContact;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.adapter.model.IItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.ButtonItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.ContactItem;
import com.tiocloud.chat.feature.home.friend.adapter.model.item.FuncItem;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTaskProxy;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.main.model.MainTab;
import com.tiocloud.chat.feature.user.applylist.ApplyListActivity;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.tiocloud.chat.widget.popupwindow.FriendOpWindow;
import com.tiocloud.chat.yanxun.group.MainGroupActivity;
import com.tiocloud.chat.yanxun.groupsend.SelectGroupSendActivity;
import com.tiocloud.chat.yanxun.lable.LabelActivityNewUI;
import com.tiocloud.chat.yanxun.lable.LableActivity;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.imclient.model.body.wx.WxUserSysNtf;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc :
 */
public class FriendPresenter extends FriendContract.Presenter {

    private final TioFriendFragmentBinding binding;
    private ContactAdapter adapter;

    // ====================================================================================
    // eventBus
    // ====================================================================================

    public FriendPresenter(FriendContract.View view, TioFriendFragmentBinding binding) {
        super(view);
        this.binding = binding;
    }

    // 用户系统通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxUserSysNtf(WxUserSysNtf ntf) {
        switch (ntf.code) {
            case 30:// 申请好友请求
            case 31:// 好友发生变更-新增
            case 32:// 好友发生变更-减员
            case 33:// 好友发生变更-信息修改
                load();
                break;
        }
    }

    // 更新好友申请数量的事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxUserSysNtf(UpdateFriendApplyNumEvent event) {
        requestApplyNum();
    }

    // ====================================================================================
    // init
    // ====================================================================================

    @Override
    public void initRefreshView() {
        binding.refreshView.setEnabled(true);
        binding.refreshView.setOnRefreshListener(this::load);
    }

    @Override
    public void initListView() {
        adapter = new ContactAdapter(getView().getActivity());
        adapter.installCatalogView(binding.contactsCatalogView, binding.contactListView);
        binding.contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IItem item = adapter.getItem(position);
                if (item == null) return;

                switch (item.getType()) {
                    case IItem.Type.FUNC:
                        ApplyListActivity.start(getView().getActivity());
                        break;
                    case IItem.Type.CONTACT:
                        ContactItem contactItem = (ContactItem) item;
                        IContact contact = contactItem.getContact();
                        UserDetailActivity.start(getView().getActivity(), contact.getId());
                        break;
                    case IItem.Type.BUTTON:
                        ButtonItem buttonItem = (ButtonItem)item;
                        if (buttonItem.btnType == ButtonItem.BtnType.GROUP){
                            MainGroupActivity.start(getView().getActivity());
                        }else if (buttonItem.btnType == ButtonItem.BtnType.LABLE){
                            LableActivity.start(getView().getActivity());
//                            LabelActivityNewUI.start(getView().getActivity());
                        }else if (buttonItem.btnType == ButtonItem.BtnType.GROUPSEND){
                            SelectGroupSendActivity.start(getView().getActivity());
//                            LabelActivityNewUI.start(getView().getActivity());
                        }
                        break;
                }
            }
        });
        binding.contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                IItem item = adapter.getItem(position);
                if (item == null) return false;

                if (item.getType() == IItem.Type.CONTACT) {
                    ContactItem contactItem = (ContactItem) item;
                    IContact contact = contactItem.getContact();
                    // 显示长按操作弹窗
                    showFriendOpWindow(view, contact);
                }
                return true;
            }

            private void showFriendOpWindow(View view, IContact contact) {
                new FriendOpWindow(view) {
                    @Override
                    public void onDelFriendOk(IContact contact) {
                        super.onDelFriendOk(contact);
                        load();
                    }
                }.show(contact);
            }
        });
        binding.contactListView.setAdapter(adapter);
    }

    // ====================================================================================
    // data
    // ====================================================================================

    @Override
    public void load() {
        requestContacts(data -> {
            // 停止刷新
            if (binding != null) {
                binding.refreshView.setRefreshing(false);
            }
            // 获取好友申请数
            requestApplyNum();
        });
    }

    /**
     * 获取联系人数据
     */
    private void requestContacts(MailListTaskProxy proxy) {
        // 功能项
        IData iData = new IData();
        iData.add(new FuncItem(0));
        iData.add(new ButtonItem(getView().getActivity().getString(R.string.group_chat), ButtonItem.BtnType.GROUP,R.mipmap.icon_groupchat));
        iData.add(new ButtonItem(getView().getActivity().getString(R.string.label), ButtonItem.BtnType.LABLE, R.mipmap.icon_label));
        iData.add(new ButtonItem(getView().getActivity().getString(R.string.group_send), ButtonItem.BtnType.GROUPSEND, R.mipmap.icon_groupfa));
        // 获取联系人
        getModel().requestMailList(iData, taskData -> {
            if (taskData.ok) {
                StringBuilder sb = null;
                for (IItem data : taskData.data){
                    if (data instanceof ContactItem){
                        String id = ((ContactItem) data).getContact().getId();
                        if (sb == null){
                            sb = new StringBuilder();
                        }else {
                            sb.append(",");
                        }
                        sb.append(id);
                    }
                }
                if (sb != null){
                    ChatFragment.queryUids = sb.toString();
                }
                if (adapter != null) {
                    adapter.setData(taskData.data);
                }
            } else {
                TioToast.showShort(taskData.msg);
            }
            if (proxy != null) {
                proxy.onTaskDone(taskData);
            }
        });
    }

    /**
     * 获取好友申请数
     */
    private void requestApplyNum() {
        getModel().requestApplyData(new TioCallback<Integer>() {
            @Override
            public void onTioSuccess(Integer integer) {
                // 更新 首页-好友tab 红点
                updateFriendRedDot(integer);
                // 更新 - 列表 - 好友请求数
                if (adapter != null) {
                    adapter.updateFriendApplyNum(integer);
                }
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    /**
     * 更新 首页-好友tab 红点
     */
    private void updateFriendRedDot(int applyCount) {
        Activity activity = getView().getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.updateRedDot(MainTab.FRIEND.tabIndex, applyCount);
        }
    }
}
