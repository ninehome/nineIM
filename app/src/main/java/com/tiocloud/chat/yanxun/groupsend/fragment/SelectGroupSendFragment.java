package com.tiocloud.chat.yanxun.groupsend.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.tiocloud.chat.R;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.yanxun.groupsend.SelectGroupSendActivity;
import com.tiocloud.chat.yanxun.groupsend.fragment.mvp.FragmentGroupSendContract;
import com.tiocloud.chat.yanxun.groupsend.fragment.mvp.FragmentGroupSendPresenter;
import com.tiocloud.chat.yanxun.groupsend.select.SelectFriendItem;
import com.watayouxiang.androidutils.page.TioFragment;

import java.util.LinkedList;
import java.util.List;

/**R * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectGroupSendFragment extends TioFragment implements FragmentGroupSendContract.View {
    private FragmentGroupSendPresenter presenter;
    private List<String> mExistIds;

    private List<SelectFriendItem> selectedLableFriends;

    public static SelectGroupSendFragment create() {
        return new SelectGroupSendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_create_group_fragment, container, false);
    }

    public LinkedList<String> getCheckIds(){
        return presenter.getCheckIds();
    }

    public LinkedList<String> getCheckNames(){
        return presenter.getCheckNames();
    }

    public List<SelectFriendItem> getSelectedLableFriendIds() {
        return selectedLableFriends;
    }

    public List<SelectFriendItem> getSelectGroup(){
        return presenter.getmItemList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            String mSelectedRoomIds = data.getStringExtra("SELECTED_ITEMS");
            if (TextUtils.isEmpty(mSelectedRoomIds)) {
                return;
            }
            List<SelectFriendItem> mItemList = JSON.parseArray(mSelectedRoomIds, SelectFriendItem.class);
            presenter.setGroupItemList(mItemList);
        }else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            String mSelectedLabelIds = data.getStringExtra("SELECTED_LABEL_IDS");
            if (TextUtils.isEmpty(mSelectedLabelIds)) {
                return ;
            }
            String mSelectedLabelNames = data.getStringExtra("SELECTED_LABEL_NAMES");
            String mSelectedLableIds = data.getStringExtra("SELECTED_LABEL_FRIEND_IDS");
            if (TextUtils.isEmpty(mSelectedLabelNames)) {
                return ;
            }
            List<String> mLabelIds = JSON.parseArray(mSelectedLabelIds, String.class);
            selectedLableFriends = JSON.parseArray(mSelectedLableIds, SelectFriendItem.class);
            presenter.setLableItemList(JSON.parseArray(mSelectedLabelNames, String.class));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getIntent() != null) {
            String ids = getActivity().getIntent().getStringExtra("exist_ids");
            mExistIds = JSON.parseArray(ids, String.class);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ContactsCatalogView contactsCatalogView = findViewById(R.id.ccv_catalogList);
        presenter = new FragmentGroupSendPresenter(this);
        presenter.setSelectGroupSendFragment(this);
        presenter.initRecyclerView(recyclerView, contactsCatalogView);
        presenter.initMenuBtn(getTvMenuBtn());
        presenter.search(null);
    }

    private TextView getTvMenuBtn() {
        FragmentActivity activity = getActivity();
        if (activity instanceof SelectGroupSendActivity) {
            SelectGroupSendActivity createGroupActivity = (SelectGroupSendActivity) activity;
            return createGroupActivity.getTvMenuBtn();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    public void search(String keyWord) {
        if (presenter != null) {
            presenter.search(keyWord);
        }
    }

    public void createGroup() {
        if (presenter != null) {
            presenter.createLable();
        }
    }

    @Override
    public void finishPage() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
