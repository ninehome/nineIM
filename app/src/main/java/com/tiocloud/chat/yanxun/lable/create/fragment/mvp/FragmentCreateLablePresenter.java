package com.tiocloud.chat.yanxun.lable.create.fragment.mvp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.yanxun.lable.create.fragment.adapter.ExCreateLableAdapter;
import com.tiocloud.chat.yanxun.lable.create.fragment.adapter.model.MultiModel;
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
public class FragmentCreateLablePresenter extends FragmentCreateLableContract.Presenter {

    private ExCreateLableAdapter adapter;
    private TextView tvMenuBtn;

    private LinkedList<String> mExistIds;

    public FragmentCreateLablePresenter(FragmentCreateLableContract.View view, LinkedList<String> mExistIds) {
        super(new FragmentCreateLableModel(), view);
        this.mExistIds = mExistIds;
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void initRecyclerView(RecyclerView recyclerView, ContactsCatalogView contactsCatalogView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new ExCreateLableAdapter() {
            @Override
            protected void onCheckedItemChange(LinkedList<String> checkedIds) {
                super.onCheckedItemChange(checkedIds);
                FragmentCreateLablePresenter.this.onCheckedItemChange(checkedIds);
            }
        };
        adapter.installCatalogView(contactsCatalogView, recyclerView);
        adapter.setCheckedIds(mExistIds);
        recyclerView.setAdapter(adapter);
    }

    private void onCheckedItemChange(LinkedList<String> checkedIds) {
        if (checkedIds == null) checkedIds = new LinkedList<>();
        if (tvMenuBtn == null) return;

        tvMenuBtn.setEnabled(checkedIds.size() != 0);
        if (checkedIds.size() != 0) {
            tvMenuBtn.setText(getView().getActivity().getString(R.string.sure)+"("+checkedIds.size()+")");
        } else {
            tvMenuBtn.setText(getView().getActivity().getString(R.string.sure));
        }
    }

    @Override
    public void initMenuBtn(TextView tvMenuBtn) {
        this.tvMenuBtn = tvMenuBtn;
        if (tvMenuBtn != null) {
            tvMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createLable();
                }
            });
        }
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
