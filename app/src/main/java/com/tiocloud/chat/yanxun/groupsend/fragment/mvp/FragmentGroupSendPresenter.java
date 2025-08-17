package com.tiocloud.chat.yanxun.groupsend.fragment.mvp;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.SizeUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.yanxun.groupsend.SelectGroupSendActivity;
import com.tiocloud.chat.yanxun.groupsend.SelectLabelActivity;
import com.tiocloud.chat.yanxun.groupsend.fragment.SelectGroupSendFragment;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.ExSelectGroupSendAdapter;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.model.MultiModel;
import com.tiocloud.chat.yanxun.groupsend.fragment.adapter.model.MultiType;
import com.tiocloud.chat.yanxun.groupsend.select.SelectFriendItem;
import com.tiocloud.chat.yanxun.groupsend.select.SelectRoomActivity;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class FragmentGroupSendPresenter extends FragmentGroupSendContract.Presenter {

    private ExSelectGroupSendAdapter adapter;
    private TextView tvMenuBtn;
    SelectGroupSendFragment selectGroupSendFragment;
    View groupView,lableView;

    private List<SelectFriendItem> mGroupItemList;

    private List<String> mLableNameList;

    public void setSelectGroupSendFragment(SelectGroupSendFragment selectGroupSendFragment) {
        this.selectGroupSendFragment = selectGroupSendFragment;
    }

    public LinkedList<String> getCheckIds(){
        return adapter.getCheckedIds();
    }

    public LinkedList<String> getCheckNames(){
        return adapter.getCheckedNames();
    }

    public List<SelectFriendItem> getmItemList() {
        return mGroupItemList;
    }

    public void setGroupItemList(List<SelectFriendItem> mItemList) {
        this.mGroupItemList = mItemList;
//        TextView viewById = groupView.findViewById(R.id.tvValue1);
//        if (mItemList.size() > 0) {
//            final StringBuilder sb = new StringBuilder();
//            sb.append(mItemList.get(0).getName());
//            for (int i = 1; i < mItemList.size(); i++) {
//                sb.append(",");
//                sb.append(mItemList.get(i).getName());
//            }
//            viewById.setText(sb);
//            viewById.setVisibility(View.VISIBLE);
//        } else {
//            viewById.setText("");
//            viewById.setVisibility(View.GONE);
//        }
    }

    public void setLableItemList(List<String> mLableNameList) {
        this.mLableNameList = mLableNameList;
//        TextView viewById = lableView.findViewById(R.id.tvValue2);
//        if (mLableNameList.size() > 0) {
//            viewById.setText(TextUtils.join(",", mLableNameList));
//            viewById.setVisibility(View.VISIBLE);
//        } else {
//            viewById.setText("");
//            viewById.setVisibility(View.GONE);
//        }
    }

    public FragmentGroupSendPresenter(FragmentGroupSendContract.View view) {
        super(new FragmentGroupSendModel(), view);
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void initRecyclerView(RecyclerView recyclerView, ContactsCatalogView contactsCatalogView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ExSelectGroupSendAdapter() {
            @Override
            protected void onCheckedItemChange(LinkedList<String> checkedIds) {
                super.onCheckedItemChange(checkedIds);
                FragmentGroupSendPresenter.this.onCheckedItemChange(checkedIds);
            }
        };
        adapter.installCatalogView(contactsCatalogView, recyclerView);
//        TextView textView = new TextView(getView().getActivity());
//        textView.setText("headerView");
//        adapter.addHeaderView(textView);
        View view = LayoutInflater.from(getView().getActivity()).inflate(R.layout.block_select_extension, null);
//        groupView = LayoutInflater.from(getView().getActivity()).inflate(R.layout.block_select_extension_group, null);
        groupView = view.findViewById(R.id.ll_group);
        groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectRoomActivity.start(selectGroupSendFragment, getView().getActivity(), 1, mGroupItemList);
            }
        });
//        lableView = LayoutInflater.from(getView().getActivity()).inflate(R.layout.block_select_extension_lable, null);
        lableView = view.findViewById(R.id.ll_label);
        lableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getView().getActivity(), SelectLabelActivity.class);
//                intent.putExtra("SELECTED_LABEL", JSON.toJSONString(mLabelIds));
                selectGroupSendFragment.startActivityForResult(intent, 2);
            }
        });
//        adapter.addHeaderView(groupView);
////        View view = LayoutInflater.from(getView().getActivity()).inflate(R.layout.block_select_extension_line, null);
//        adapter.addHeaderView(view);
//        adapter.addHeaderView(lableView);
        adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
    }

    private void onCheckedItemChange(LinkedList<String> checkedIds) {
        if (checkedIds == null) checkedIds = new LinkedList<>();
        if (tvMenuBtn == null) return;

//        tvMenuBtn.setEnabled(checkedIds.size() != 0);
        SelectGroupSendActivity activity = (SelectGroupSendActivity) getView().getActivity();
        if (checkedIds.size() != 0) {
//            tvMenuBtn.setText(String.format(Locale.getDefault(), "确定(%d)", checkedIds.size()));

            activity.setBtnNextStep(String.format(Locale.getDefault(),
                    getView().getActivity().getString(R.string.next_step_d), checkedIds.size()));
        } else {
//            tvMenuBtn.setText("确定");
            activity.setBtnNextStep(getView().getActivity().getString(R.string.next_step));
        }
    }

    @Override
    public void initMenuBtn(TextView tvMenuBtn) {
        this.tvMenuBtn = tvMenuBtn;
//        this.tvMenuBtn.setEnabled(true);
//        if (tvMenuBtn != null) {
//            tvMenuBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    createLable();
//                    selectAllFriend();
//                }
//            });
//        }
    }

    private void selectAllFriend() {
        List<MultiModel> data = adapter.getData();
        adapter.clearCheck();
        for (MultiModel multiModel : data){
            if (multiModel.type == MultiType.CONTACT){
                adapter.addCheckedIds(String.valueOf(multiModel.contact.uid));
                adapter.addCheckedNames(multiModel.contact.getNick());
            }
        }
        adapter.notifyDataSetChanged();
        onCheckedItemChange(adapter.getCheckedIds());
    }

    // ====================================================================================
    // 搜索
    // ====================================================================================

    @Override
    public void search(final String keyWord) {
        if (adapter == null) return;
        getModel().getMailList(keyWord, new BaseModel.DataProxy<List<MailListResp.Friend>>() {
            @Override
            public void onSuccess(List<MailListResp.Friend> friends) {
                adapter.updateData(friends, keyWord);
            }
        });
    }

    // ====================================================================================
    // 建群
    // ====================================================================================

    @Override
    public void createLable() {
        if (adapter == null){
            return;
        }
        List<MailListResp.Friend> friends = new ArrayList<>();
        String uidArray = StringUtil.list2String(adapter.getCheckedIds());
        if (uidArray == null) {
            return;
        }
        List<MultiModel> data = adapter.getData();
        for (String uid : uidArray.split(",")){
            for (MultiModel multiModel : data){
                if (multiModel.contact != null && multiModel.contact.uid == Integer.parseInt(uid)){
                    friends.add(multiModel.contact);
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("friends", JSONArray.toJSON(friends).toString());
        getView().getActivity().setResult(Activity.RESULT_OK, intent);
        getView().getActivity().finish();
    }

}
