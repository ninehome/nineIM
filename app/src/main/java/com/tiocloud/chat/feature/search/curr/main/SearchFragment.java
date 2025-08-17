package com.tiocloud.chat.feature.search.curr.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.feature.search.curr.main.mvp.SearchFragmentContract;
import com.tiocloud.chat.feature.search.curr.main.mvp.SearchFragmentPresenter;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchFragment extends TioFragment implements SearchFragmentContract.View {

    private RecyclerView indicator;
    private ViewPager pager;
    private SearchFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tio_search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        presenter = new SearchFragmentPresenter(this);
        presenter.initPager(pager, indicator);
    }

    private void findViews() {
        indicator = findViewById(R.id.rv_indicator);
        pager = findViewById(R.id.vp_pager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getChildFragmentManager();
    }

    // ====================================================================================
    // public
    // ====================================================================================

    /**
     * 搜索
     *
     * @param keyWord 关键词
     */
    public void search(String keyWord) {
        if (presenter != null) {
            presenter.search(keyWord);
        }
    }

    /**
     * 切换页面
     *
     * @param item 位置
     */
    public void setCurrentItem(int item) {
        if (pager != null) {
            pager.setCurrentItem(item);
        }
    }
}
