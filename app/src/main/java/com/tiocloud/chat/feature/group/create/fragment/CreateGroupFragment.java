package com.tiocloud.chat.feature.group.create.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.widget.ContactsCatalogView;
import com.tiocloud.chat.feature.group.create.CreateGroupActivity;
import com.tiocloud.chat.feature.group.create.fragment.mvp.FragmentCreateGroupContract;
import com.tiocloud.chat.feature.group.create.fragment.mvp.FragmentCreateGroupPresenter;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class CreateGroupFragment extends TioFragment implements FragmentCreateGroupContract.View {
    private FragmentCreateGroupPresenter presenter;

    public static CreateGroupFragment create() {
        return new CreateGroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_create_group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ContactsCatalogView contactsCatalogView = findViewById(R.id.ccv_catalogList);
        presenter = new FragmentCreateGroupPresenter(this);
        presenter.initRecyclerView(recyclerView, contactsCatalogView);
        presenter.initMenuBtn(getTvMenuBtn());
        presenter.search(null);
    }

    private TextView getTvMenuBtn() {
        FragmentActivity activity = getActivity();
        if (activity instanceof CreateGroupActivity) {
            CreateGroupActivity createGroupActivity = (CreateGroupActivity) activity;
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
            presenter.createGroup();
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
