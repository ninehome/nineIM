package com.tiocloud.chat.feature.search.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.feature.search.user.fragment.mvp.SearchUserFragmentContract;
import com.tiocloud.chat.feature.search.user.fragment.mvp.SearchUserFragmentPresenter;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SearchUserFragment extends TioFragment implements SearchUserFragmentContract.View {

    private SearchUserFragmentPresenter presenter;

    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tio_search_user_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView rv_list = rootView.findViewById(R.id.rv_list);
        presenter = new SearchUserFragmentPresenter(this);
        presenter.initListView(rv_list, rootView.findViewById(R.id.empty_view));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    public void showEmpty(){
        if (presenter != null) {
            presenter.showEmpty();
        }
    }

    public void hideEmpty(){
        if (presenter != null) {
            presenter.hideEmpty();
        }
    }

    public void search(String keyWord) {
        if (presenter != null) {
            presenter.search(keyWord);
        }
    }
}
