package com.tiocloud.chat.yanxun.lable.create.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.tiocloud.chat.R;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.yanxun.lable.create.CreateLableSelectContactActivity;
import com.tiocloud.chat.yanxun.lable.create.fragment.mvp.FragmentCreateLableContract;
import com.tiocloud.chat.yanxun.lable.create.fragment.mvp.FragmentCreateLablePresenter;
import com.watayouxiang.androidutils.page.TioFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class CreateLableFragment extends TioFragment implements FragmentCreateLableContract.View {
    private FragmentCreateLablePresenter presenter;
    private LinkedList<String> mExistIds = new LinkedList<>();

    public static CreateLableFragment create() {
        return new CreateLableFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_create_group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getIntent() != null) {
            String ids = getActivity().getIntent().getStringExtra("exist_ids");
            if (ids != null && ids != ""){
                mExistIds = new LinkedList<>(JSON.parseArray(ids, String.class));
            }
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ContactsCatalogView contactsCatalogView = findViewById(R.id.ccv_catalogList);
        presenter = new FragmentCreateLablePresenter(this, mExistIds);
        presenter.initRecyclerView(recyclerView, contactsCatalogView);
        presenter.initMenuBtn(getTvMenuBtn());
        presenter.search(null);
    }

    private TextView getTvMenuBtn() {
        FragmentActivity activity = getActivity();
        if (activity instanceof CreateLableSelectContactActivity) {
            CreateLableSelectContactActivity createGroupActivity = (CreateLableSelectContactActivity) activity;
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
