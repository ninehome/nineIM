package com.tiocloud.chat.feature.group.card.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.group.card.fragment.mvp.FragmentSelectGroupContract;
import com.tiocloud.chat.feature.group.card.fragment.mvp.FragmentSelectGroupPresenter;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/2/25
 * desc :
 */
public class SelectGroupFragment extends TioFragment implements FragmentSelectGroupContract.View {
    private FragmentSelectGroupPresenter presenter;

    public static SelectGroupFragment create() {
        return new SelectGroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_select_group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        presenter = new FragmentSelectGroupPresenter(this);
        presenter.initRecyclerView(recyclerView);
        presenter.search(null);
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

    @Override
    public void finishPage() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
